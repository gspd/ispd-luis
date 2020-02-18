/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gspd.ispd.motor.filas.servidores.implementacao;

import gspd.ispd.motor.EventoFuturo;
import gspd.ispd.motor.Mensagens;
import gspd.ispd.motor.Simulation;
import gspd.ispd.motor.filas.Mensagem;
import gspd.ispd.motor.filas.Tarefa;
import gspd.ispd.motor.filas.dag.TarefaDAG;
import gspd.ispd.motor.filas.servidores.CS_Comunicacao;
import gspd.ispd.motor.filas.servidores.CS_Processamento;
import gspd.ispd.motor.filas.servidores.CentroServico;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author denison_usuario
 */
// Só GRID
public class CS_Maquina extends CS_Processamento implements Mensagens, Vertice {

    private List<CS_Comunicacao> conexoesEntrada;
    private List<CS_Comunicacao> conexoesSaida;
    private List<Tarefa> filaTarefas;
    private List<Tarefa> filaBloqueio = new ArrayList<Tarefa>();
    private List<Mensagem> filaMsgDAG = new ArrayList<Mensagem>();
    private List<CS_Processamento> mestres;
    private List<List> caminhoMestre;
    private int processadoresDisponiveis;
    //Dados dinamicos
    private List<Tarefa> tarefaEmExecucao;
    //Adição de falhas
    private List<Double> falhas = new ArrayList<Double>();
    private List<Double> recuperacao = new ArrayList<Double>();
    private boolean erroRecuperavel;
    private boolean falha = false;
    private List<Tarefa> historicoProcessamento;
    //TO DO: INCLUIR INFORMAÇÕES DE MEMÓRIA E DISCO
    
    /**
     * 
     * @param id
     * @param proprietario
     * @param PoderComputacional
     * @param numeroProcessadores
     * @param Ocupacao 
     */

    public CS_Maquina(String id, String proprietario, double PoderComputacional, int numeroProcessadores, double Ocupacao) {
        super(id, proprietario, PoderComputacional, numeroProcessadores, Ocupacao, 0);
        this.conexoesEntrada = new ArrayList<CS_Comunicacao>();
        this.conexoesSaida = new ArrayList<CS_Comunicacao>();
        this.filaTarefas = new ArrayList<Tarefa>();
        this.mestres = new ArrayList<CS_Processamento>();
        this.processadoresDisponiveis = numeroProcessadores;
        this.tarefaEmExecucao = new ArrayList<Tarefa>(numeroProcessadores);
        this.historicoProcessamento = new ArrayList<Tarefa>();
    }

    public CS_Maquina(String id, String proprietario, double PoderComputacional, int numeroProcessadores, double Ocupacao, int numeroMaquina) {
        super(id, proprietario, PoderComputacional, numeroProcessadores, Ocupacao, numeroMaquina);
        this.conexoesEntrada = new ArrayList<CS_Comunicacao>();
        this.conexoesSaida = new ArrayList<CS_Comunicacao>();
        this.filaTarefas = new ArrayList<Tarefa>();
        this.mestres = new ArrayList<CS_Processamento>();
        this.processadoresDisponiveis = numeroProcessadores;
        this.tarefaEmExecucao = new ArrayList<Tarefa>(numeroProcessadores);
        this.historicoProcessamento = new ArrayList<Tarefa>();
    }

    @Override
    public void addConexoesEntrada(CS_Link conexao) {
        this.conexoesEntrada.add(conexao);
    }

    @Override
    public void addConexoesSaida(CS_Link conexao) {
        this.conexoesSaida.add(conexao);
    }

    public void addConexoesEntrada(CS_Switch conexao) {
        this.conexoesEntrada.add(conexao);
    }

    public void addConexoesSaida(CS_Switch conexao) {
        this.conexoesSaida.add(conexao);
    }

    public void addMestre(CS_Processamento mestre) {
        this.mestres.add(mestre);
    }

    @Override
    public List<CS_Comunicacao> getConexoesSaida() {
        return this.conexoesSaida;
    }

    @Override
    public void chegadaDeCliente(Simulation simulacao, Tarefa cliente) {
        if (cliente.getEstado() != Tarefa.CANCELADO) {
            cliente.iniciarEsperaProcessamento(simulacao.getTime(this));
            if (processadoresDisponiveis != 0) {
                // indica que recurso está ocupado
                processadoresDisponiveis--;
                //cria evento para iniciar o atendimento imediatamente
                EventoFuturo novoEvt = new EventoFuturo(
                        simulacao.getTime(this),
                        EventoFuturo.ATENDIMENTO,
                        this,
                        cliente);
                simulacao.addEventoFuturo(novoEvt);
            } else {
                filaTarefas.add(cliente);
            }
            historicoProcessamento.add(cliente);
        }
    }

    @Override
    public void atendimento(Simulation simulacao, Tarefa cliente) {
        if (cliente instanceof TarefaDAG) {
            // TODO: Quem implementa isso aqui???
            // Ela mesma, refazer o git diff com ispd-dag
            atenderProgramaDAG(simulacao, (TarefaDAG) cliente);
        } else {
            cliente.finalizarEsperaProcessamento(simulacao.getTime(this));
            cliente.iniciarAtendimentoProcessamento(simulacao.getTime(this));
            tarefaEmExecucao.add(cliente);
            Double next = simulacao.getTime(this) + tempoProcessar(cliente.getTamProcessamento() - cliente.getMflopsProcessado());
            if (!falhas.isEmpty() && next > falhas.get(0)) {
                Double tFalha = falhas.remove(0);
                if (tFalha < simulacao.getTime(this)) {
                    tFalha = simulacao.getTime(this);
                }
                Mensagem msg = new Mensagem(this, Mensagens.FALHAR, cliente);
                EventoFuturo evt = new EventoFuturo(
                        tFalha,
                        EventoFuturo.MENSAGEM,
                        this,
                        msg);
                simulacao.addEventoFuturo(evt);
            } else {
                falha = false;
                //Gera evento para atender proximo cliente da lista
                EventoFuturo evtFut = new EventoFuturo(
                        next,
                        EventoFuturo.SAIDA,
                        this, cliente);
                //Event adicionado a lista de evntos futuros
                simulacao.addEventoFuturo(evtFut);
            }            
        }

    }

    @Override
    public void saidaDeCliente(Simulation simulacao, Tarefa cliente) {
        //Incrementa o número de Mbits transmitido por este link
        this.getMetrica().incMflopsProcessados(cliente.getTamProcessamento() - cliente.getMflopsProcessado());
        //Incrementa o tempo de processamento
        double tempoProc = this.tempoProcessar(cliente.getTamProcessamento() - cliente.getMflopsProcessado());
        this.getMetrica().incSegundosDeProcessamento(tempoProc);
        //Incrementa o tempo de transmissão no pacote
        cliente.finalizarAtendimentoProcessamento(simulacao.getTime(this));
        tarefaEmExecucao.remove(cliente);
        //eficiencia calculada apenas nas classes CS_Maquina
        cliente.calcEficiencia(this.getPoderComputacional());
        //Devolve tarefa para o mestre
        if (mestres.contains(cliente.getOrigem())) {
            int index = mestres.indexOf(cliente.getOrigem());
            List<CentroServico> caminho = new ArrayList<CentroServico>((List<CentroServico>) caminhoMestre.get(index));
            cliente.setCaminho(caminho);
            //Gera evento para chegada da tarefa no proximo servidor
            EventoFuturo evtFut = new EventoFuturo(
                    simulacao.getTime(this),
                    EventoFuturo.CHEGADA,
                    cliente.getCaminho().remove(0),
                    cliente);
            //Event adicionado a lista de evntos futuros
            simulacao.addEventoFuturo(evtFut);
        } else {
            //buscar menor caminho!!!
            CS_Processamento novoMestre = (CS_Processamento) cliente.getOrigem();
            List<CentroServico> caminho = new ArrayList<CentroServico>(
                    CS_Maquina.getMenorCaminhoIndireto(this, novoMestre));
            this.addMestre(novoMestre);
            this.caminhoMestre.add(caminho);
            cliente.setCaminho(new ArrayList<CentroServico>(caminho));
            //Gera evento para chegada da tarefa no proximo servidor
            EventoFuturo evtFut = new EventoFuturo(
                    simulacao.getTime(this),
                    EventoFuturo.CHEGADA,
                    cliente.getCaminho().remove(0),
                    cliente);
            //Event adicionado a lista de evntos futuros
            simulacao.addEventoFuturo(evtFut);
        }
        if (filaTarefas.isEmpty()) {
            //Indica que está livre
            this.processadoresDisponiveis++;
        } else {
            //Gera evento para atender proximo cliente da lista
            Tarefa proxCliente = filaTarefas.remove(0);
            EventoFuturo evtFut = new EventoFuturo(
                    simulacao.getTime(this),
                    EventoFuturo.ATENDIMENTO,
                    this, proxCliente);
            //Event adicionado a lista de evntos futuros
            simulacao.addEventoFuturo(evtFut);
        }
        nextTarefa(simulacao);
    }

    @Override
    public void requisicao(Simulation simulacao, Mensagem mensagem, int tipo) {
        if (mensagem != null) {
            if (mensagem.getTipo() == Mensagens.ATUALIZAR) {
                atenderAtualizacao(simulacao, mensagem);
            } else if (mensagem.getTarefa() != null && mensagem.getTarefa().getLocalProcessamento().equals(this)) {
                switch (mensagem.getTipo()) {
                    case Mensagens.PARAR:
                        atenderParada(simulacao, mensagem);
                        break;
                    case Mensagens.CANCELAR:
                        atenderCancelamento(simulacao, mensagem);
                        break;
                    case Mensagens.DEVOLVER:
                        atenderDevolucao(simulacao, mensagem);
                        break;
                    case Mensagens.DEVOLVER_COM_PREEMPCAO:
                        atenderDevolucaoPreemptiva(simulacao, mensagem);
                        break;
                    case Mensagens.FALHAR:
                        atenderFalha(simulacao, mensagem);
                        break;
                    case Mensagens.DAG_PROGRAM:
                        atenderDAG(simulacao, mensagem);
                        break;
                    case Mensagens.DAG_ACK:
                        atenderDAG(simulacao, mensagem);
                        break;
                }
            }
        }
    }

    @Override
    public void determinarCaminhos() throws LinkageError {
        //Instancia objetos
        caminhoMestre = new ArrayList<List>(mestres.size());
        //Busca pelos caminhos
        for (int i = 0; i < mestres.size(); i++) {
            caminhoMestre.add(i, CS_Maquina.getMenorCaminho(this, mestres.get(i)));
        }
        //verifica se todos os mestres são alcansaveis
        for (int i = 0; i < mestres.size(); i++) {
            if (caminhoMestre.get(i).isEmpty()) {
                throw new LinkageError();
            }
        }
    }

    @Override
    public void atenderCancelamento(Simulation simulacao, Mensagem mensagem) {
        if (mensagem.getTarefa().getEstado() == Tarefa.PROCESSANDO) {
            //remover evento de saida do cliente do servidor
            simulacao.removeEventoFuturo(EventoFuturo.SAIDA, this, mensagem.getTarefa());
            tarefaEmExecucao.remove(mensagem.getTarefa());
            //gerar evento para atender proximo cliente
            if (filaTarefas.isEmpty()) {
                //Indica que está livre
                this.processadoresDisponiveis++;
            } else {
                //Gera evento para atender proximo cliente da lista
                Tarefa proxCliente = filaTarefas.remove(0);
                EventoFuturo evtFut = new EventoFuturo(
                        simulacao.getTime(this),
                        EventoFuturo.ATENDIMENTO,
                        this, proxCliente);
                //Event adicionado a lista de evntos futuros
                simulacao.addEventoFuturo(evtFut);
            }
            // Merged from DAG
            nextTarefa(simulacao);
        }
        double inicioAtendimento = mensagem.getTarefa().cancelar(simulacao.getTime(this));
        double tempoProc = simulacao.getTime(this) - inicioAtendimento;
        double mflopsProcessados = this.getMflopsProcessados(tempoProc);
        //Incrementa o número de Mflops processados por este recurso
        this.getMetrica().incMflopsProcessados(mflopsProcessados);
        //Incrementa o tempo de processamento
        this.getMetrica().incSegundosDeProcessamento(tempoProc);
        //Incrementa porcentagem da tarefa processada
        mensagem.getTarefa().setMflopsProcessado(mflopsProcessados);
        // Incrementa mflops desperdiçados
        mensagem.getTarefa().incMflopsDesperdicados(mflopsProcessados);
    }

    @Override
    public void atenderParada(Simulation simulacao, Mensagem mensagem) {
        if (mensagem.getTarefa().getEstado() == Tarefa.PROCESSANDO) {
            //remover evento de saida do cliente do servidor
            boolean remover = simulacao.removeEventoFuturo(
                    EventoFuturo.SAIDA,
                    this,
                    mensagem.getTarefa());
            //gerar evento para atender proximo cliente
            if (filaTarefas.isEmpty()) {
                //Indica que está livre
                this.processadoresDisponiveis++;
            } else {
                //Gera evento para atender proximo cliente da lista
                Tarefa proxCliente = filaTarefas.remove(0);
                EventoFuturo evtFut = new EventoFuturo(
                        simulacao.getTime(this),
                        EventoFuturo.ATENDIMENTO,
                        this, proxCliente);
                //Event adicionado a lista de evntos futuros
                simulacao.addEventoFuturo(evtFut);
            }
            // Merged DAG
            nextTarefa(simulacao);
            double inicioAtendimento = mensagem.getTarefa().parar(simulacao.getTime(this));
            double tempoProc = simulacao.getTime(this) - inicioAtendimento;
            double mflopsProcessados = this.getMflopsProcessados(tempoProc);
            //Incrementa o número de Mflops processados por este recurso
            this.getMetrica().incMflopsProcessados(mflopsProcessados);
            //Incrementa o tempo de processamento
            this.getMetrica().incSegundosDeProcessamento(tempoProc);
            //Incrementa procentagem da tarefa processada
            mensagem.getTarefa().setMflopsProcessado(mflopsProcessados);
            // Merged from DAG
            mensagem.getTarefa().incMflopsDesperdicados(mflopsProcessados);
            tarefaEmExecucao.remove(mensagem.getTarefa());
            filaTarefas.add(mensagem.getTarefa());
        }
    }

    @Override
    public void atenderDevolucao(Simulation simulacao, Mensagem mensagem) {
        boolean remover = filaTarefas.remove(mensagem.getTarefa());
        if (remover) {
            EventoFuturo evtFut = new EventoFuturo(
                    simulacao.getTime(this),
                    EventoFuturo.CHEGADA,
                    mensagem.getTarefa().getOrigem(),
                    mensagem.getTarefa());
            //Event adicionado a lista de evntos futuros
            simulacao.addEventoFuturo(evtFut);
        }
    }

    @Override
    public void atenderDevolucaoPreemptiva(Simulation simulacao, Mensagem mensagem) {
        boolean remover = false;
        if (mensagem.getTarefa().getEstado() == Tarefa.PARADO) {
            remover = filaTarefas.remove(mensagem.getTarefa());
        } else if (mensagem.getTarefa().getEstado() == Tarefa.PROCESSANDO) {
            remover = simulacao.removeEventoFuturo(
                    EventoFuturo.SAIDA,
                    this,
                    mensagem.getTarefa());
            //gerar evento para atender proximo cliente
            if (filaTarefas.isEmpty()) {
                //Indica que está livre
                this.processadoresDisponiveis++;
            } else {
                //Gera evento para atender proximo cliente da lista
                Tarefa proxCliente = filaTarefas.remove(0);
                EventoFuturo evtFut = new EventoFuturo(
                        simulacao.getTime(this),
                        EventoFuturo.ATENDIMENTO,
                        this, proxCliente);
                //Event adicionado a lista de evntos futuros
                simulacao.addEventoFuturo(evtFut);
            }
            // Merged DAG
            nextTarefa(simulacao);
            double inicioAtendimento = mensagem.getTarefa().parar(simulacao.getTime(this));
            double tempoProc = simulacao.getTime(this) - inicioAtendimento;
            double mflopsProcessados = this.getMflopsProcessados(tempoProc);
            //Incrementa o número de Mflops processados por este recurso
            this.getMetrica().incMflopsProcessados(mflopsProcessados);
            //Incrementa o tempo de processamento
            this.getMetrica().incSegundosDeProcessamento(tempoProc);
            //Incrementa procentagem da tarefa processada
            double numCP = ((int) ((mflopsProcessados / mensagem.getTarefa().getCheckPoint()) * mensagem.getTarefa().getCheckPoint())) * mensagem.getTarefa().getCheckPoint();
            mensagem.getTarefa().setMflopsProcessado(numCP);
            tarefaEmExecucao.remove(mensagem.getTarefa());
            // Incrementa deperdício
            mensagem.getTarefa().incMflopsDesperdicados(mflopsProcessados - numCP);
            tarefaEmExecucao.remove(mensagem.getTarefa());
        }
        if (remover) {
            // Evento para devolver tarefa a sua origem
            EventoFuturo evtFut = new EventoFuturo(
                    simulacao.getTime(this),
                    EventoFuturo.CHEGADA,
                    mensagem.getTarefa().getOrigem(),
                    mensagem.getTarefa());
            //Event adicionado a lista de evntos futuros
            simulacao.addEventoFuturo(evtFut);
        }
    }

    @Override
    public void atenderAtualizacao(Simulation simulacao, Mensagem mensagem) {
        //enviar resultados
        int index = mestres.indexOf(mensagem.getOrigem());
        List<CentroServico> caminho = new ArrayList<CentroServico>((List<CentroServico>) caminhoMestre.get(index));
        Mensagem novaMensagem = new Mensagem(this, mensagem.getTamComunicacao(), Mensagens.RESULTADO_ATUALIZAR);
        //Obtem informações dinâmicas
        novaMensagem.setProcessadorEscravo(new ArrayList<Tarefa>(tarefaEmExecucao));
        novaMensagem.setFilaEscravo(new ArrayList<Tarefa>(filaTarefas));
        novaMensagem.setCaminho(caminho);
        EventoFuturo evtFut = new EventoFuturo(
                simulacao.getTime(this),
                EventoFuturo.MENSAGEM,
                novaMensagem.getCaminho().remove(0),
                novaMensagem);
        //Event adicionado a lista de evntos futuros
        simulacao.addEventoFuturo(evtFut);
    }

    @Override
    public void atenderRetornoAtualizacao(Simulation simulacao, Mensagem mensagem) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void atenderFalha(Simulation simulacao, Mensagem mensagem) {
        double tempoRec = recuperacao.remove(0);
        for (Tarefa tar : tarefaEmExecucao) {
            if (tar.getEstado() == Tarefa.PROCESSANDO) {
                falha = true;
                double inicioAtendimento = tar.parar(simulacao.getTime(this));
                double tempoProc = simulacao.getTime(this) - inicioAtendimento;
                double mflopsProcessados = this.getMflopsProcessados(tempoProc);
                //Incrementa o número de Mflops processados por este recurso
                this.getMetrica().incMflopsProcessados(mflopsProcessados);
                //Incrementa o tempo de processamento
                this.getMetrica().incSegundosDeProcessamento(tempoProc);
                //Incrementa procentagem da tarefa processada
                double numCP = ((int) (mflopsProcessados / tar.getCheckPoint())) * tar.getCheckPoint();
                tar.setMflopsProcessado(numCP);
                tar.incMflopsDesperdicados(mflopsProcessados - numCP);
                if (erroRecuperavel) {
                    //Reiniciar atendimento da tarefa
                    tar.iniciarEsperaProcessamento(simulacao.getTime(this));
                    //cria evento para iniciar o atendimento imediatamente
                    EventoFuturo novoEvt = new EventoFuturo(
                            simulacao.getTime(this) + tempoRec,
                            EventoFuturo.ATENDIMENTO,
                            this,
                            tar);
                    simulacao.addEventoFuturo(novoEvt);
                } else {
                    tar.setEstado(Tarefa.FALHA);
                }
            }
        }
        if (!erroRecuperavel) {
            processadoresDisponiveis += tarefaEmExecucao.size();
            filaTarefas.clear();
        }
        tarefaEmExecucao.clear();
    }

    @Override
    public Integer getCargaTarefas() {
        if (falha) {
            return -100;
        } else {
            return (filaTarefas.size() + tarefaEmExecucao.size());
        }
    }

    /**
     * @return the historicoProcessamento
     */
    public List<Tarefa> getHistorico() {
        return historicoProcessamento;
    }

    public void addFalha(Double tFalha, double tRec, boolean recuperavel) {
        falhas.add(tFalha);
        recuperacao.add(tRec);
        erroRecuperavel = recuperavel;
    }

    @Override
    public void atenderAckAlocacao(Simulation simulacao, Mensagem mensagem) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void atenderDesligamento(Simulation simulacao, Mensagem mensagem) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void atenderProgramaDAG(Simulation simulacao, TarefaDAG tarefa) {
        if (tarefa.getEstado() != Tarefa.PROCESSANDO) {
            System.out.println(this.getId() + "-" + this.getnumeroMaquina() + " Iniciar " + tarefa.toString() + "! " + simulacao.getTime(this));
            tarefa.finalizarEsperaProcessamento(simulacao.getTime(this));
            tarefa.iniciarAtendimentoProcessamento(simulacao.getTime(this));
            tarefaEmExecucao.add(tarefa);
        }

        Object instrucao = tarefa.getThread().getBlock();

        //Encerra tarefa
        if (instrucao == null) {
            System.out.println(this.getId() + "-" + this.getnumeroMaquina() + " Finalizar " + tarefa.getIdentificador() + "! " + simulacao.getTime(this));
            //Gera evento para atender proximo cliente da lista
            EventoFuturo novoEvt = new EventoFuturo(
                    simulacao.getTime(this),
                    EventoFuturo.SAIDA,
                    this, tarefa);
            //Event adicionado a lista de evntos futuros
            simulacao.addEventoFuturo(novoEvt);

            //Atendimeto de Send
        } else if (instrucao instanceof gspd.ispd.motor.filas.dag.Send) {
            gspd.ispd.motor.filas.dag.Send send = (gspd.ispd.motor.filas.dag.Send) instrucao;
            TarefaDAG dest = send.getDestino();
            System.out.println(this.getId() + "-" + this.getnumeroMaquina() + " Enviando <" + tarefa + "--" + dest + "> para " + dest.getLocalProcessamento().getId() + "-" + ((CS_Processamento) dest.getLocalProcessamento()).getnumeroMaquina());
            Mensagem msg = new Mensagem(this, send.getTamanho(), dest, tarefa, Mensagens.DAG_PROGRAM);
            send(simulacao, msg, (CS_Processamento) dest.getLocalProcessamento());
            tarefa.getThread().setNextBlock();
            blockDAGTask(simulacao, tarefa);

            //Atende Receive
        } else if (instrucao instanceof gspd.ispd.motor.filas.dag.Receive) {
            gspd.ispd.motor.filas.dag.Receive receive = (gspd.ispd.motor.filas.dag.Receive) instrucao;
            System.out.println(this.getId() + "-" + this.getnumeroMaquina() + " Aguardar uma comunicação!");
            Mensagem temp = null;
            for (Mensagem msgTemp : filaMsgDAG) {
                if (msgTemp.getTarefa().equals(tarefa)) {
                    temp = msgTemp;
                    break;
                }
            }
            if (temp == null) {
                System.out.println("Bloquear tarefa até chegar mensagem");
                blockDAGTask(simulacao, tarefa);
                //nextTarefa(simulacao);
            } else {
                System.out.println("Mensagem já tinha chegado indicar que chegou e continuando execução");
                filaMsgDAG.remove(temp);
                receive.setOrigem(temp.getTarefaOrigem());
                Mensagem msg = new Mensagem(this, 0.011444091796875, temp.getTarefaOrigem(), tarefa, Mensagens.DAG_ACK);
                send(simulacao, msg, (CS_Processamento) temp.getTarefaOrigem().getLocalProcessamento());
                EventoFuturo novoEvt = new EventoFuturo(
                        simulacao.getTime(this),
                        EventoFuturo.ATENDIMENTO,
                        this,
                        tarefa);
                simulacao.addEventoFuturo(novoEvt);
                tarefa.getThread().setNextBlock();
            }

            //Realiza precessamento
        } else if (instrucao instanceof gspd.ispd.motor.filas.dag.Process) {
            gspd.ispd.motor.filas.dag.Process process = (gspd.ispd.motor.filas.dag.Process) instrucao;
            Double next = simulacao.getTime(this) + tempoProcessar(process.getTamanho());
            System.out.println("Atender processamento " + tarefa + "! " + process.getTamanho());
            EventoFuturo novoEvt = new EventoFuturo(next, EventoFuturo.ATENDIMENTO, this, tarefa);
            simulacao.addEventoFuturo(novoEvt);
            tarefa.getThread().setNextBlock();

        }
    }

    private void blockDAGTask(Simulation simulacao, TarefaDAG tarefa) {
        if (tarefa.getEstado() == Tarefa.PROCESSANDO) {
            double inicioAtendimento = tarefa.parar(simulacao.getTime(this) + 0.1);
            double tempoProc = simulacao.getTime(this) - inicioAtendimento;
            double mflopsProcessados = this.getMflopsProcessados(tempoProc);
            //Incrementa o número de Mflops processados por este recurso
            this.getMetrica().incMflopsProcessados(mflopsProcessados);
            //Incrementa o tempo de processamento
            this.getMetrica().incSegundosDeProcessamento(tempoProc);
            tarefaEmExecucao.remove(tarefa);
            filaBloqueio.add(tarefa);
            nextTarefa(simulacao);
        }
    }

    public void atenderDAG(Simulation simulacao, Mensagem mensagem) {
        System.out.println("Bloqueados " + getId() + "-" + this.getnumeroMaquina() + " " + filaBloqueio);
        System.out.println("Tarefa: " + mensagem.getTarefa());
        if (filaBloqueio.contains(mensagem.getTarefa())) {
            System.out.println("tirar tarefa " + simulacao.getTime(this));
            filaBloqueio.remove(mensagem.getTarefa());
            if (processadoresDisponiveis != 0) {
                processadoresDisponiveis--;
                System.out.println("Processador livre " + tarefaEmExecucao);
                //máquina livre tarefa será atendida imediatamente
                EventoFuturo evtFut = new EventoFuturo(
                        simulacao.getTime(this),
                        EventoFuturo.ATENDIMENTO,
                        this, mensagem.getTarefa());
                simulacao.addEventoFuturo(evtFut);
            } else {
                filaTarefas.add(mensagem.getTarefa());
            }
        }
        if (mensagem.getTipo() == Mensagens.DAG_PROGRAM) {
            System.out.println("armazenar mensagem " + simulacao.getTime(this));
            filaMsgDAG.add(mensagem);
        }
    }

    /**
     * Verifica se exite alguma tarefa aguardando ser atendida, caso encontre
     * cria o evento para atender a tarefa, caso não encontre indica que um
     * processador está livre
     *
     * @param simulacao
     */
    private void nextTarefa(Simulation simulacao) {
        //gerar evento para atender proximo cliente
        if (filaTarefas.isEmpty()) {
            //Indica que está livre
            this.processadoresDisponiveis++;
        } else {
            //Gera evento para atender proximo cliente da lista
            Tarefa proxCliente = filaTarefas.remove(0);
            EventoFuturo evtFut = new EventoFuturo(
                    simulacao.getTime(this),
                    EventoFuturo.ATENDIMENTO,
                    this, proxCliente);
            //Event adicionado a lista de evntos futuros
            simulacao.addEventoFuturo(evtFut);
        }
    }

    private void send(Simulation simulacao, Mensagem msg, CS_Processamento dest) {
        List<CentroServico> caminho;
        if (dest.equals(this)) {
            //Medida provisória :P utilizada quando o destino da mensagem é a própria máquina
            caminho = new ArrayList<CentroServico>();
            caminho.add(this);
        } else if (mestres.contains(dest)) {
            System.out.println("Conheço rota");
            int index = mestres.indexOf(dest);
            caminho = new ArrayList<CentroServico>((List<CentroServico>) caminhoMestre.get(index));
            System.out.println("Olha lá -> " + caminho.size());
        } else {
            System.out.println("Calcular rota");
            //buscar menor caminho!!!;
            caminho = new ArrayList<CentroServico>(CS_Maquina.getMenorCaminhoIndireto(this, dest));
            this.addMestre(dest);
            this.caminhoMestre.add(new ArrayList<CentroServico>(caminho));
        }
        msg.setCaminho(caminho);
        EventoFuturo evtFut = new EventoFuturo(
                simulacao.getTime(this),
                EventoFuturo.MENSAGEM,
                msg.getCaminho().remove(0),
                msg);
        //Event adicionado a lista de evntos futuros
        simulacao.addEventoFuturo(evtFut);
    }   
}