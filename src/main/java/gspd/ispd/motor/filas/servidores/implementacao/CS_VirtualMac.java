/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gspd.ispd.motor.filas.servidores.implementacao;

import gspd.ispd.motor.EventoFuturo;
import gspd.ispd.motor.Mensagens;
import gspd.ispd.motor.Simulation;
import gspd.ispd.motor.filas.*;
import gspd.ispd.motor.filas.servidores.CS_Processamento;
import gspd.ispd.motor.filas.servidores.CentroServico;
import gspd.ispd.motor.metricas.MetricasCusto;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Diogo Tavares
 */
public class CS_VirtualMac extends CS_Processamento implements Cliente, Mensagens {
    
    public static final int LIVRE = 1;
    public static final int ALOCADA = 2;
    public static final int REJEITADA = 3;
    public static final int DESTRUIDA = 4;
    
    //Lista de atributos
    private CS_VMM vmmResponsavel;
    private int processadoresDisponiveis;
    private double poderProcessamento;
    private double memoriaDisponivel;
    private double discoDisponivel;
    private double instanteAloc;
    private double tempoDeExec;
    private String OS;
    private CS_MaquinaCloud maquinaHospedeira;
    private List<CentroServico> caminho;
    private List<CentroServico> caminhoVMM;
    private List<List> caminhoIntermediarios;
    private int status;
    private List<Tarefa> filaTarefas;
    private List<Tarefa> tarefaEmExecucao;
    private List<CS_VMM> vmmsIntermediarios;
    private MetricasCusto metricaCusto;
    private List<Double> falhas = new ArrayList<Double>();
    private List<Double> recuperacao = new ArrayList<Double>();
    private List<Tarefa> historicoProcessamento = new ArrayList<>();
    private boolean erroRecuperavel;
    private boolean falha = false;


    /**
     *
     * @param id
     * @param proprietario
     * @param numeroProcessadores
     * @param memoria
     * @param disco
     * @param OS
     */
    public CS_VirtualMac(String id, String proprietario, int numeroProcessadores, double memoria, double disco, String OS) {
        super(id, proprietario, 0, numeroProcessadores, 0, 0);
        this.processadoresDisponiveis = numeroProcessadores;
        this.memoriaDisponivel = memoria;
        this.discoDisponivel = disco;
        this.OS = OS;
        this.metricaCusto = new MetricasCusto(id);
        this.maquinaHospedeira = null;
        this.caminhoVMM = null;
        this.vmmsIntermediarios = new ArrayList<CS_VMM>();
        this.caminhoIntermediarios = new ArrayList<List>();
        this.tempoDeExec = 0;
        this.status = LIVRE;
        this.tarefaEmExecucao = new ArrayList<Tarefa>(numeroProcessadores);
        this.filaTarefas = new ArrayList<Tarefa>();
    }

    
    @Override
    public void chegadaDeCliente(Simulation simulacao, Tarefa cliente) {
        if (cliente.getEstado() != Tarefa.CANCELADO) { //se a tarefa estiver parada ou executando
            if (simulacao.isVerbose()) {
                simulacao.getJanela().println("[Enter VM] Client: " + cliente, Color.blue);
            }
            cliente.iniciarEsperaProcessamento(simulacao.getTime(this));
            cliente.setEstado(Tarefa.PARADO);
            if (processadoresDisponiveis != 0) {
                //indica que recurso está ocupado
                processadoresDisponiveis--;
                //cria evento para iniciar o atendimento imediatamente
                EventoFuturo novoEvt = new EventoFuturo(
                        simulacao.getTime(this),
                        EventoFuturo.ATENDIMENTO,
                        this,
                        cliente);
                simulacao.addEventoFuturo(novoEvt);
                // Precisa cololcar no histórico ?
                historicoProcessamento.add(cliente);
            } else {
                filaTarefas.add(cliente);
            }
        } else {
            if (simulacao.isVerbose()) {
                simulacao.getJanela().println("[Enter VM] task is cancelled", Color.red);
            }
        }
    }

    @Override
    public void atendimento(Simulation simulacao, Tarefa cliente) {
        if (simulacao.isVerbose()) {
            if(cliente == null)
                simulacao.getJanela().println("[Attendance VM] null task", Color.red);
            else
                simulacao.getJanela().println("[Attendance VM] Client: " + cliente, Color.blue);
        }
        cliente.finalizarEsperaProcessamento(simulacao.getTime(this));
        // se for tarefa DAG, deve poder criar os eventos futuros de suas tarefas SUFIXAS
        if (cliente instanceof TarefaDAG) {
            TarefaDAG tarefaDAG = (TarefaDAG) cliente;
            // notifica todos os seus SUFIXOS que essa tarefa já foi INICIADA
            tarefaDAG.notifySuffixes();
            if (simulacao.isVerbose()) {
                simulacao.getJanela().println("[Attendance VM] Notifying " + tarefaDAG.getSuffixes().size() + " suffixes tasks", Color.blue);
            }
            // para cada tarefa SUFIXA
            for (TarefaDAG tDAG : tarefaDAG.getSuffixes()) {
                // se a tarefa notificada pode ser executada, então cria um evento para ela
                if (tDAG.canExecute()) {
                    EventoFuturo eventoFuturo = new EventoFuturo(
                            simulacao.getTime(this),
                            EventoFuturo.CHEGADA,
                            tDAG.getOrigem(),
                            tDAG
                    );
                    simulacao.addEventoFuturo(eventoFuturo);
                }
            }
            // limpa as tarefas da lista de SUFIXOS
            tarefaDAG.clearSuffixes();
        }
        cliente.iniciarAtendimentoProcessamento(simulacao.getTime(this));
        tarefaEmExecucao.add(cliente);
        cliente.setEstado(Tarefa.PROCESSANDO);
        // tempo do próximo evento depois do atendimento
        double nextTime;
        // se a tarefa é uma tarefa de espera, então utiliza seu tempo de espera. Se não é necessário calcular o tempo de espera
        if (cliente instanceof EsperaDAG) {
            nextTime = simulacao.getTime(this) + ((EsperaDAG)cliente).getTime();
        } else {
            nextTime = simulacao.getTime(this) + tempoProcessar(cliente.getTamProcessamento() - cliente.getMflopsProcessado());
        }
        // se tem falhas previstas para ocorrer na simulação
        if (!falhas.isEmpty() && nextTime > falhas.get(0)) {
            falha = true;
            double tFalha = falhas.remove(0);
            if (tFalha < simulacao.getTime(this)) {
                tFalha = simulacao.getTime(this);
            }
            cliente.setEstado(Tarefa.FALHA);
            Mensagem msg = new Mensagem(this, Mensagens.FALHAR, cliente);
            if (simulacao.isVerbose()) {
                simulacao.getJanela().println("[Attendance VM] failure occurrence", Color.blue);
                simulacao.getJanela().println("[Attendance VM] :::: failure time: " + tFalha, Color.blue);
                simulacao.getJanela().println("[Attendance VM] :::: message: " + msg, Color.blue);
            }
            EventoFuturo evt = new EventoFuturo(
                    tFalha,
                    EventoFuturo.MENSAGEM,
                    this,
                    msg);
            simulacao.addEventoFuturo(evt);
        } else {
            falha = false;
            if (simulacao.isVerbose()) {
                simulacao.getJanela().println("[Attendance VM] " + cliente + " finished", Color.blue);
            }
            cliente.setEstado(Tarefa.CONCLUIDO);
            //Gera evento para atender proximo cliente da lista
            EventoFuturo evtFut = new EventoFuturo(
                    nextTime,
                    EventoFuturo.SAIDA,
                    this, cliente);
            //Event adicionado a lista de evntos futuros
            simulacao.addEventoFuturo(evtFut);
        }
    }

    @Override
    public void saidaDeCliente(Simulation simulacao, Tarefa cliente) {
        /////////////// DICA JOÃO: para toda tarefa dentro da lista de "sub"tarefas (dentro da tarefa cliente), cria evento futuro
        if (simulacao.isVerbose()) {
            simulacao.getJanela().println("[Exit VM] Client: " + cliente, Color.blue);
        }
        //Incrementa o número de Mbits transmitido por este link
        this.getMetrica().incMflopsProcessados(cliente.getTamProcessamento() - cliente.getMflopsProcessado());
        //Incrementa o tempo de processamento
        double tempoProc = this.tempoProcessar(cliente.getTamProcessamento() - cliente.getMflopsProcessado());
        this.getMetrica().incSegundosDeProcessamento(tempoProc);

        // se for tarefa DAG, deve poder criar os eventos futuros de suas tarefas SUCESSORAS
        if (cliente instanceof TarefaDAG) {
            TarefaDAG tarefaDAG = (TarefaDAG) cliente;
            // notifica todos as suas SUCESSORAS que essa tarefa já foi CONCLUÍDA
            tarefaDAG.notifySuccessors();
            if (simulacao.isVerbose()) {
                simulacao.getJanela().println("[Exit VM] Notifying " + tarefaDAG.getSuffixes().size() + " successors tasks", Color.blue);
            }
            // para cada tarefa SUCESSORA
            for (TarefaDAG tDAG : tarefaDAG.getSuccessors()) {
                // se a tarefa notificada pode ser executada, então cria um evento para ela
                if (tDAG.canExecute()) {
                    EventoFuturo eventoFuturo = new EventoFuturo(
                            simulacao.getTime(this),
                            EventoFuturo.CHEGADA,
                            tDAG.getOrigem(),
                            tDAG
                    );
                    simulacao.addEventoFuturo(eventoFuturo);
                }
            }
            // limpa as tarefas da lista de SUCESSORAS
            tarefaDAG.clearSuccessors();
        }

        //Incrementa o tempo de transmissão no pacote
        cliente.finalizarAtendimentoProcessamento(simulacao.getTime(this));
        tarefaEmExecucao.remove(cliente);
        //eficiencia calculada apenas nas classes CS_Maquina
        cliente.calcEficiencia(this.getPoderComputacional());
        //Devolve tarefa para o mestre
        
        CentroServico origem = cliente.getOrigem();
        ArrayList<CentroServico> caminho;
        if(origem.equals(this.vmmResponsavel)){
            if (simulacao.isVerbose()) {
                simulacao.getJanela().println("[Exit VM] path to VMM ok", Color.blue);
            }
            caminho =  new ArrayList<CentroServico>(caminhoVMM);
        }else{
            if (simulacao.isVerbose()) {
                simulacao.getJanela().println("[Exit VM] recalculating VMM through intermediaries VMMs", Color.blue);
            }
            int index = vmmsIntermediarios.indexOf((CS_VMM) origem);
            if(index == -1){
                CS_MaquinaCloud auxMaq = this.getMaquinaHospedeira();
                ArrayList<CentroServico> caminhoInter = new ArrayList<CentroServico>(getMenorCaminhoIndiretoCloud(auxMaq, (CS_Processamento) origem));
                caminho = new ArrayList<CentroServico>(caminhoInter);
                vmmsIntermediarios.add((CS_VMM) origem);
                int idx = vmmsIntermediarios.indexOf((CS_VMM) origem);
                caminhoIntermediarios.add(idx, caminhoInter);
                
            }else{
                caminho = new ArrayList<CentroServico>(caminhoIntermediarios.get(index));
            }
        }
        cliente.setCaminho(caminho);
        if (simulacao.isVerbose()) {
            simulacao.getJanela().println("[Exit VM] path to vmm size: " + caminho.size(), Color.blue);
        }

        //Gera evento para chegada da tarefa no proximo servidor
        EventoFuturo evtFut = new EventoFuturo(
                simulacao.getTime(this),
                EventoFuturo.CHEGADA,
                cliente.getCaminho().remove(0),
                cliente);
        //Event adicionado a lista de evntos futuros
        simulacao.addEventoFuturo(evtFut);

        if (filaTarefas.isEmpty()) {
            //Indica que está livre
            this.processadoresDisponiveis++; // it is really here ?:?
        } else {
            //Gera evento para atender proximo cliente da lista
            Tarefa proxCliente = filaTarefas.remove(0);
            EventoFuturo NovoEvt = new EventoFuturo(
                    simulacao.getTime(this),
                    EventoFuturo.ATENDIMENTO,
                    this, proxCliente);
            //Event adicionado a lista de evntos futuros
            simulacao.addEventoFuturo(NovoEvt);
        }
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
                }
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
            double inicioAtendimento = mensagem.getTarefa().parar(simulacao.getTime(this));
            double tempoProc = simulacao.getTime(this) - inicioAtendimento;
            double mflopsProcessados = this.getMflopsProcessados(tempoProc);
            //Incrementa o número de Mflops processados por este recurso
            this.getMetrica().incMflopsProcessados(mflopsProcessados);
            //Incrementa o tempo de processamento
            this.getMetrica().incSegundosDeProcessamento(tempoProc);
            //Incrementa procentagem da tarefa processada
            mensagem.getTarefa().setMflopsProcessado(mflopsProcessados);
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
            double inicioAtendimento = mensagem.getTarefa().parar(simulacao.getTime(this));
            double tempoProc = simulacao.getTime(this) - inicioAtendimento;
            double mflopsProcessados = this.getMflopsProcessados(tempoProc);
            //Incrementa o número de Mflops processados por este recurso
            this.getMetrica().incMflopsProcessados(mflopsProcessados);
            //Incrementa o tempo de processamento
            this.getMetrica().incSegundosDeProcessamento(tempoProc);
            //Incrementa procentagem da tarefa processada
            int numCP = (int) (mflopsProcessados / mensagem.getTarefa().getCheckPoint());
            mensagem.getTarefa().setMflopsProcessado(numCP * mensagem.getTarefa().getCheckPoint());
            tarefaEmExecucao.remove(mensagem.getTarefa());
        }
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
    public void atenderAtualizacao(Simulation simulacao, Mensagem mensagem) {
        //enviar resultados
        
        List<CentroServico> caminho = new ArrayList<CentroServico>((List<CentroServico>) caminhoVMM);
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void atenderFalha(Simulation simulacao, Mensagem mensagem) {

        Tarefa tarefa = mensagem.getTarefa();
        // se for tarefa DAG, deve poder criar os eventos futuros de suas tarefas TRATAMENTOS_FALHAS (CATCHES)
        if (tarefa instanceof TarefaDAG) {
            TarefaDAG tarefaDAG = (TarefaDAG) tarefa;
            // notifica todos as suas TRATAMENTOS_FALHAS (CATCHES) que essa tarefa FALHOU
            tarefaDAG.notifyCatches();
            if (simulacao.isVerbose()) {
                simulacao.getJanela().println("[Attendance FAIL VM] Notifying " + tarefaDAG.getSuffixes().size() + " catch tasks", Color.blue);
            }
            // para cada tarefa TRATAMENTOS_FALHAS (CATCHES)
            for (TarefaDAG tDAG : tarefaDAG.getCatches()) {
                // se a tarefa notificada pode ser executada, então cria um evento para ela
                if (tDAG.canExecute()) {
                    EventoFuturo eventoFuturo = new EventoFuturo(
                            simulacao.getTime(this),
                            EventoFuturo.CHEGADA,
                            tDAG.getOrigem(),
                            tDAG
                    );
                    simulacao.addEventoFuturo(eventoFuturo);
                }
            }
            // limpa as tarefas da lista de TRATAMENTOS_FALHAS (CATCHES)
            tarefaDAG.clearCatches();
        }

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
                int numCP = (int) (mflopsProcessados / tar.getCheckPoint());
                tar.setMflopsProcessado(numCP * tar.getCheckPoint());
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

    public CS_VMM getVmmResponsavel() {
        return vmmResponsavel;
    }
    
    public List<CS_VMM> getVmmsIntermediarios(){
        return this.vmmsIntermediarios;
    }

    public List<List> getCaminhoIntermediarios() {
        return caminhoIntermediarios;
    }
    
    public void addIntermediario(CS_VMM aux){
       System.out.println(aux.getId());
              
       this.vmmsIntermediarios.add(aux);
    }
    
    public void addCaminhoIntermediario(int i, List<CentroServico> caminho){
        this.caminhoIntermediarios.add(i, caminho);
    }

       public int getProcessadoresDisponiveis() {
        return processadoresDisponiveis;
    }

    public void setProcessadoresDisponiveis(int processadoresDisponiveis) {
        this.processadoresDisponiveis = processadoresDisponiveis;
    }

    public double getPoderProcessamento() {
        return poderProcessamento;
    }

    public void setPoderProcessamentoPorNucleo(double poderProcessamento) {
        super.setPoderComputacionalDisponivelPorProcessador(poderProcessamento);
        super.setPoderComputacional(poderProcessamento);
    }

    public double getMemoriaDisponivel() {
        return memoriaDisponivel;
    }

    public void setMemoriaDisponivel(double memoriaDisponivel) {
        this.memoriaDisponivel = memoriaDisponivel;
    }

    public double getDiscoDisponivel() {
        return discoDisponivel;
    }

    public void setDiscoDisponivel(double discoDisponivel) {
        this.discoDisponivel = discoDisponivel;
    }

    public CS_MaquinaCloud getMaquinaHospedeira() {
        return maquinaHospedeira;
    }

    public void setMaquinaHospedeira(CS_MaquinaCloud maquinaHospedeira) {
        this.maquinaHospedeira = maquinaHospedeira;
    }

    public List<CentroServico> getCaminhoVMM() {
        return caminhoVMM;
    }

    public void setCaminhoVMM(List<CentroServico> caminhoMestre) {
        this.caminhoVMM = caminhoMestre;        
    }

     public void addVMM(CS_VMM vmmResponsavel) {
        this.vmmResponsavel = vmmResponsavel;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public MetricasCusto getMetricaCusto() {
        return metricaCusto;
    }
    
        
    @Override
    public void determinarCaminhos() throws LinkageError {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


    @Override
    public Object getConexoesSaida() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Integer getCargaTarefas() {
         if (falha) {
             // QUESTION: pq '-100' ?
            return -100;
        } else {
            return (filaTarefas.size() + tarefaEmExecucao.size());
        }
    }

    @Override
    public double getTimeCriacao() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public CentroServico getOrigem() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<CentroServico> getCaminho() {
        return this.caminho;
    }

    @Override
    public void setCaminho(List<CentroServico> caminho) {
        this.caminho = caminho;
    }

    @Override
    public double getTamComunicacao() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public double getTamProcessamento() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void atenderAckAlocacao(Simulation simulacao, Mensagem mensagem) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void atenderDesligamento(Simulation simulacao, Mensagem mensagem) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public double getInstanteAloc() {
        return instanteAloc;
    }

    public void setInstanteAloc(double instanteAloc) {
        this.instanteAloc = instanteAloc;
    }

    public double getTempoDeExec() {
        return tempoDeExec;
    }

    public void setTempoDeExec(double tempoDestruir) {
        this.tempoDeExec = tempoDestruir - getInstanteAloc();
    }

    @Override
    public String toString() {
        return "VM#" + this.getId();
    }
}
