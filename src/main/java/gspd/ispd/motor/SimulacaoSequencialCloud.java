/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gspd.ispd.motor;

import gspd.ispd.alocacaoVM.VMM;
import gspd.ispd.escalonadorCloud.MestreCloud;
import gspd.ispd.motor.filas.Cliente;
import gspd.ispd.motor.filas.Mensagem;
import gspd.ispd.motor.filas.RedeDeFilasCloud;
import gspd.ispd.motor.filas.Tarefa;
import gspd.ispd.motor.filas.servidores.CS_Processamento;
import gspd.ispd.motor.filas.servidores.CentroServico;
import gspd.ispd.motor.filas.servidores.implementacao.CS_MaquinaCloud;
import gspd.ispd.motor.filas.servidores.implementacao.CS_Mestre;
import gspd.ispd.motor.filas.servidores.implementacao.CS_VMM;
import java.awt.Color;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

/**
 *
 * @author denison_usuario
 */
public class SimulacaoSequencialCloud extends Simulation {

    private double time = 0;
    /**
     * List of futures event sorted by priority, where this priority
     * is the time instant the event was created. See {@link EventoFuturo#compareTo(EventoFuturo)}
     */
    private PriorityQueue<EventoFuturo> eventos;
    
    public SimulacaoSequencialCloud(SimulationProgress janela, RedeDeFilasCloud redeDeFilas, List<Tarefa> tarefas) throws IllegalArgumentException {
        super(janela, redeDeFilas,tarefas);
        this.time = 0;
        this.eventos = new PriorityQueue<EventoFuturo>();

        // verifica possíveis erros no modelo
        if (redeDeFilas == null) {
            throw new IllegalArgumentException("The model has no icons.");
        } else if (redeDeFilas.getMestres() == null || redeDeFilas.getMestres().isEmpty()) {
            throw new IllegalArgumentException("The model has no Masters.");
        } else if (redeDeFilas.getLinks() == null || redeDeFilas.getLinks().isEmpty()) {
            janela.println("The model has no Networks.", Color.orange);
        }else if (redeDeFilas.getVMs() == null || redeDeFilas.getVMs().isEmpty())
            janela.println("The model has no virtual machines configured.", Color.orange);
        if (tarefas == null || tarefas.isEmpty()) {
            throw new IllegalArgumentException("One or more workloads have not been configured.");
        }
        
        janela.println("Creating routing [master -> slaves]", Color.cyan);
        /*
         * Trecho de código que implementa o roteamento entre os mestres e os seus respectivos escravos
         */
        for (CS_Processamento mst : redeDeFilas.getMestres()) {
            VMM temp = (VMM) mst;
            MestreCloud aux = (MestreCloud) mst;
            //Cede acesso ao mestre a fila de eventos futuros
            aux.setSimulacao(this);
            temp.setSimulacaoAlloc(this);
            //Encontra menor caminho entre o mestre e seus escravos
            janela.println(":: Master " + mst.getId() + " determining paths to slaves");
            mst.determinarCaminhos(); //mestre encontra caminho para seus escravos
        }
        
        janela.incProgresso(5);
        janela.println("OK [master -> slaves]", Color.green);
        
        if (redeDeFilas.getMaquinasCloud() == null || redeDeFilas.getMaquinasCloud().isEmpty()) {
            janela.println("The model has no phisical machines.", Color.orange);
        } else {
            janela.println("Creating rounting [slaves -> master]", Color.cyan);
            for (CS_MaquinaCloud maq : redeDeFilas.getMaquinasCloud()) {
                //Encontra menor caminho entre o escravo e seu mestre
                janela.println(":: Machine " + maq.getId() + " determining path to master");
                maq.setStatus(CS_MaquinaCloud.LIGADO);
                maq.determinarCaminhos();//escravo encontra caminhos para seu mestre
            }
        }
        janela.println("OK [slaves -> master]", Color.green);
        //fim roteamento
        janela.println("OK routing", Color.green);
        janela.incProgresso(5);
        janela.println("Simulation constructed", Color.pink);
    }

    @Override
    public void simular() {
        iniciarEscalonadoresCloud();
        iniciarAlocadoresCloud();
        addEventos(this.getTarefas());
        if (atualizarEscalonadores()) {
            getJanela().println("Scheduling update: ON");
            realizarSimulacaoAtualizaTime();
        } else {
            getJanela().println("Scheduling update: OFF");
            realizarSimulacao();
        }
        desligarMaquinas(this, this.getRedeDeFilasCloud());
        getJanela().incProgresso(30);
        getJanela().println("Simulation completed.", Color.pink);
        //Centralizando métricas de usuários
        //for (CS_Processamento mestre : redeDeFilas.getMestres()) {
            //CS_Mestre mst = (CS_Mestre) mestre;
            //janela.println(mst.getId());
            //janela.println(mst.getEscalonador().getMetricaUsuarios().toString());
            //redeDeFilas.getMetricasUsuarios().addMetricasUsuarios(mst.getEscalonador().getMetricaUsuarios());
        //}
        //janela.println(redeDeFilas.getMetricasUsuarios().toString());
    }

    public void addEventos(List<Tarefa> tarefas) {
        /*for (CS_VirtualMac vm : VMs){
            EventoFuturo evt = new EventoFuturo(0.0, EventoFuturo.CHEGADA, vm.getVmmResponsavel(), vm);
            eventos.add(evt);
        }*/
        getJanela().println("Adding tasks as future events", Color.blue);
        for (Tarefa tarefa : tarefas) {
            EventoFuturo evt = new EventoFuturo(tarefa.getTimeCriacao(), EventoFuturo.CHEGADA, tarefa.getOrigem(), tarefa);
            getJanela().println(":: " + evt);
            eventos.add(evt);
        }
        getJanela().println("OK (future events)", Color.green);
    }

    private static PrintStream testEventStream;
    @Override
    public void addEventoFuturo(EventoFuturo ev) {
        eventos.offer(ev);
        if (testEventStream == null) {
            try {
                testEventStream = new PrintStream("trackSimu.csv");
                testEventStream.println("Tempo,Cliente,CentroServico,Tipo");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        testEventStream.println(ev.getTempoOcorrencia() + "," + ev.getCliente() + "," + ev.getServidor() + "," + ev.getTipo());
    }

    @Override
    public boolean removeEventoFuturo(int tipoEv, CentroServico servidorEv, Cliente clienteEv) {
        //remover evento de saida do cliente do servidor
        java.util.Iterator<EventoFuturo> interator = this.eventos.iterator();
        while (interator.hasNext()) {
            EventoFuturo ev = interator.next();
            if (ev.getTipo() == tipoEv
                    && ev.getServidor().equals(servidorEv)
                    && ev.getCliente().equals(clienteEv)) {
                this.eventos.remove(ev);
                return true;
            }
        }
        return false;
    }

    @Override
    public double getTime(Object origem) {
        return time;
    }

    private boolean atualizarEscalonadores() {
        for (CS_Processamento mst : getRedeDeFilasCloud().getMestres()) {
            CS_VMM mestre = (CS_VMM) mst;
            if (mestre.getEscalonador().getTempoAtualizar() != null) {
                return true;
            }
        }
        return false;
    }

    
    private void realizarSimulacao() {
        getJanela().println("Simulation started", Color.blue);
        while (!eventos.isEmpty()) {
        //recupera o próximo evento e o executa.
            //executa estes eventos de acordo com sua ordem de chegada
            //de forma a evitar a execução de um evento antes de outro
            //que seria criado anteriormente
            EventoFuturo eventoAtual = eventos.poll();
            time = eventoAtual.getTempoOcorrencia();
            getJanela().println(":: time: " + time + " | event " + eventoAtual);
            switch (eventoAtual.getTipo()) {
                case EventoFuturo.CHEGADA:
                    getJanela().println(":::: type CHEGADA");
                    eventoAtual.getServidor().chegadaDeCliente(this, (Tarefa) eventoAtual.getCliente());
                    break;
                case EventoFuturo.ATENDIMENTO:
                    getJanela().println(":::: type ATENDIMENTO");
                    eventoAtual.getServidor().atendimento(this, (Tarefa) eventoAtual.getCliente());
                    break;
                case EventoFuturo.SAIDA:
                    getJanela().println(":::: type SAIDA");
                    eventoAtual.getServidor().saidaDeCliente(this, (Tarefa) eventoAtual.getCliente());
                    break;
                case EventoFuturo.ESCALONAR:
                    getJanela().println(":::: type ESCALONAR");
                    eventoAtual.getServidor().requisicao(this, null, EventoFuturo.ESCALONAR);
                    break;
                case EventoFuturo.ALOCAR_VMS:
                    getJanela().println(":::: type ALOCAR_VMS");
                    eventoAtual.getServidor().requisicao(this, null, EventoFuturo.ALOCAR_VMS);
                    break;
                default:
                    getJanela().println(":::: type MENSAGEM (default)");
                    eventoAtual.getServidor().requisicao(this, (Mensagem) eventoAtual.getCliente(), eventoAtual.getTipo());
                    break;
            }
        }
    }

    /**
     * Executa o laço de repetição responsavel por atender todos eventos da
     * simulação, e adiciona o evento para atualizar os escalonadores.
     */
    private void realizarSimulacaoAtualizaTime() {
        List<Object[]> Arrayatualizar = new ArrayList<Object[]>();
        for (CS_Processamento mst : getRedeDeFilas().getMestres()) {
            CS_Mestre mestre = (CS_Mestre) mst;
            if (mestre.getEscalonador().getTempoAtualizar() != null) {
                Object[] item = new Object[3];
                item[0] = mestre;
                item[1] = mestre.getEscalonador().getTempoAtualizar();
                item[2] = mestre.getEscalonador().getTempoAtualizar();
                Arrayatualizar.add(item);
            }
        }
        while (!eventos.isEmpty()) {
            //recupera o próximo evento e o executa.
            //executa estes eventos de acordo com sua ordem de chegada
            //de forma a evitar a execução de um evento antes de outro
            //que seria criado anteriormente
            for (Object[] ob : Arrayatualizar) {
                if ((Double) ob[2] < eventos.peek().getTempoOcorrencia()) {
                    CS_Mestre mestre = (CS_Mestre) ob[0];
                    for (CS_Processamento maq : mestre.getEscalonador().getEscravos()) {
                        mestre.atualizar(maq, (Double) ob[2]);
                    }
                    ob[2] = (Double) ob[2] + (Double) ob[1];
                }
            }
            EventoFuturo eventoAtual = eventos.poll();
            time = eventoAtual.getTempoOcorrencia();
            switch (eventoAtual.getTipo()) {
                case EventoFuturo.CHEGADA:
                    eventoAtual.getServidor().chegadaDeCliente(this, (Tarefa) eventoAtual.getCliente());
                    break;
                case EventoFuturo.ATENDIMENTO:
                    eventoAtual.getServidor().atendimento(this, (Tarefa) eventoAtual.getCliente());
                    break;
                case EventoFuturo.SAIDA:
                    eventoAtual.getServidor().saidaDeCliente(this, (Tarefa) eventoAtual.getCliente());
                    break;
                case EventoFuturo.ESCALONAR:
                    eventoAtual.getServidor().requisicao(this, null, EventoFuturo.ESCALONAR);
                    break;
                    case EventoFuturo.ALOCAR_VMS:
                    eventoAtual.getServidor().requisicao(this, null, EventoFuturo.ALOCAR_VMS);
                    break;
                default:
                    eventoAtual.getServidor().requisicao(this, (Mensagem) eventoAtual.getCliente(), eventoAtual.getTipo());
                    break;
            }
        }
        
        
    }

    private void desligarMaquinas(Simulation simulacao, RedeDeFilasCloud rdfCloud) {
        getJanela().println("Turning machines off", Color.blue);
        for(CS_MaquinaCloud aux : rdfCloud.getMaquinasCloud()){
            getJanela().println(":: Machine " + aux.getId() + " turning off");
            aux.desligar(simulacao);
        }
        getJanela().println("OK (machines off)", Color.green);
    }
}