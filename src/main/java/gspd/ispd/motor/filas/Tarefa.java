/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gspd.ispd.motor.filas;

import gspd.ispd.motor.filas.servidores.CS_Processamento;
import gspd.ispd.motor.filas.servidores.CentroServico;
import gspd.ispd.motor.metricas.MetricasTarefa;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe que representa o cliente do modelo de filas, ele será atendo pelos
 * centros de serviços Os clientes podem ser: Tarefas
 *
 * @author denison_usuario
 */
public class Tarefa implements Cliente {
    //Estados que a tarefa pode estar

    public static final int PARADO = 1;
    public static final int PROCESSANDO = 2;
    public static final int CANCELADO = 3;
    public static final int CONCLUIDO = 4;
    public static final int FALHA = 5;
    
    private String proprietario;
    private String aplicacao;
    private int identificador;
    private boolean copia;
    private List<CS_Processamento> historicoProcessamento = new ArrayList<CS_Processamento>();

    /**
     * The list of tasks that depends on this task
     */
    private List<Tarefa> dependencyTriggerTasks;
    /**
     * The number of tasks this task depends on
     */
    private int dependenciesCounter;

    /**
     * Indica a quantidade de mflops já processados no momento de um bloqueio
     */
    private double mflopsProcessado;
    /**
     * Indica a quantidade de mflops desperdiçados por uma preempção ou cancelamento
     */
    private double mflopsDesperdicados = 0;
    /**
     * Tamanho do arquivo em Mbits que será enviado para o escravo
     */
    private double arquivoEnvio;
    /**
     * Tamanho do arquivo em Mbits que será devolvido para o mestre
     */
    private double arquivoRecebimento;
    /**
     * Tamanho em Mflops para processar
     */
    private double tamProcessamento;
    /**
     * Local de origem da mensagem/tarefa
     */
    private CentroServico origem;
    /**
     * Local de destino da mensagem/tarefa
     */
    private CentroServico localProcessamento;
    /**
     * Caminho que o pacote deve percorrer até o destino O destino é o ultimo
     * item desta lista
     */
    private List<CentroServico> caminho;
    private double inicioEspera;
    private MetricasTarefa metricas;
    private double tempoCriacao;
    //Criando o tempo em que a tarefa acabou.
    private List<Double> tempoFinal;
    //Criando o tempo em que a tarefa começou a ser executada.
    private List<Double> tempoInicial;
    private int estado;
    private double tamComunicacao;

    public Tarefa(int id, String proprietario, String aplicacao, CentroServico origem, double arquivoEnvio, double tamProcessamento, double tempoCriacao) {
        this.proprietario = proprietario;
        this.aplicacao = aplicacao;
        this.identificador = id;
        this.copia = false;
        this.origem = origem;
        this.tamComunicacao = arquivoEnvio;
        this.arquivoEnvio = arquivoEnvio;
        this.arquivoRecebimento = 0;
        this.tamProcessamento = tamProcessamento;
        this.metricas = new MetricasTarefa();
        this.tempoCriacao = tempoCriacao;
        this.estado = PARADO;
        this.mflopsProcessado = 0;
        this.tempoInicial = new ArrayList<Double>();
        this.tempoFinal = new ArrayList<Double>();
        this.dependenciesCounter = 0;
        this.dependencyTriggerTasks = new ArrayList<>();
    }

    public Tarefa(int id, String proprietario, String aplicacao, CentroServico origem, double arquivoEnvio, double arquivoRecebimento, double tamProcessamento, double tempoCriacao) {
        this.identificador = id;
        this.proprietario = proprietario;
        this.aplicacao = aplicacao;
        this.copia = false;
        this.origem = origem;
        this.tamComunicacao = arquivoEnvio;
        this.arquivoEnvio = arquivoEnvio;
        this.arquivoRecebimento = arquivoRecebimento;
        this.tamProcessamento = tamProcessamento;
        this.metricas = new MetricasTarefa();
        this.tempoCriacao = tempoCriacao;
        this.estado = PARADO;
        this.mflopsProcessado = 0;
        this.tempoInicial = new ArrayList<Double>();
        this.tempoFinal = new ArrayList<Double>();
    }

    public Tarefa(Tarefa tarefa) {
        this.proprietario = tarefa.proprietario;
        this.aplicacao = tarefa.getAplicacao();
        this.identificador = tarefa.identificador;
        this.copia = true;
        this.origem = tarefa.getOrigem();
        this.tamComunicacao = tarefa.arquivoEnvio;
        this.arquivoEnvio = tarefa.arquivoEnvio;
        this.arquivoRecebimento = tarefa.arquivoRecebimento;
        this.tamProcessamento = tarefa.getTamProcessamento();
        this.metricas = new MetricasTarefa();
        this.tempoCriacao = tarefa.getTimeCriacao();
        this.estado = PARADO;
        this.mflopsProcessado = 0;
        this.tempoInicial = new ArrayList<Double>();
        this.tempoFinal = new ArrayList<Double>();
    }

    public double getTamComunicacao() {
        return tamComunicacao;
    }

    public double getTamProcessamento() {
        return tamProcessamento;
    }

    public String getProprietario() {
        return proprietario;
    }

    public CentroServico getOrigem() {
        return origem;
    }

    public CentroServico getLocalProcessamento() {
        return localProcessamento;
    }

    public CS_Processamento getCSLProcessamento() {
        return (CS_Processamento) localProcessamento;
    }

    public List<CentroServico> getCaminho() {
        return caminho;
    }

    public void setLocalProcessamento(CentroServico localProcessamento) {
        this.localProcessamento = localProcessamento;
    }

    public void setCaminho(List<CentroServico> caminho) {
        this.caminho = caminho;
    }

    public void iniciarEsperaComunicacao(double tempo) {
        this.inicioEspera = tempo;
    }

    public void finalizarEsperaComunicacao(double tempo) {
        this.metricas.incTempoEsperaComu(tempo - inicioEspera);
    }

    public void iniciarAtendimentoComunicacao(double tempo) {
        this.inicioEspera = tempo;
    }

    public void finalizarAtendimentoComunicacao(double tempo) {
        this.metricas.incTempoComunicacao(tempo - inicioEspera);
    }

    public void iniciarEsperaProcessamento(double tempo) {
        this.inicioEspera = tempo;
    }

    public void finalizarEsperaProcessamento(double tempo) {
        this.metricas.incTempoEsperaProc(tempo - inicioEspera);
    }

    public void iniciarAtendimentoProcessamento(double tempo) {
        this.estado = PROCESSANDO;
        this.inicioEspera = tempo;
        this.tempoInicial.add(tempo);
        this.historicoProcessamento.add((CS_Processamento) localProcessamento);
    }

    /**
     * Notify all tasks those depends on this one, that they are free
     */
    private void triggerDependentsTasks() {
        for (Tarefa task : dependencyTriggerTasks) {
            task.dependenciesCounter -= 1;
        }
    }

    public void finalizarAtendimentoProcessamento(double tempo) {
        this.estado = CONCLUIDO;
        triggerDependentsTasks();
        this.metricas.incTempoProcessamento(tempo - inicioEspera);
        if (this.tempoFinal.size() < this.tempoInicial.size()) {
            this.tempoFinal.add(tempo);
        }
        this.tamComunicacao = arquivoRecebimento;
    }

    public List<CS_Processamento> getHistoricoProcessamento(){
        return this.historicoProcessamento;
    }

    public double cancelar(double tempo) {
        if (estado == PARADO || estado == PROCESSANDO) {
            this.estado = CANCELADO;
            this.metricas.incTempoProcessamento(tempo - inicioEspera);
            if (this.tempoFinal.size() < this.tempoInicial.size()) {
                this.tempoFinal.add(tempo);
            }
            return inicioEspera;
        } else {
            this.estado = CANCELADO;
            return tempo;
        }
    }

    public double parar(double tempo) {
        if (estado == PROCESSANDO) {
            this.estado = PARADO;
            this.metricas.incTempoProcessamento(tempo - inicioEspera);
            if (this.tempoFinal.size() < this.tempoInicial.size()) {
                this.tempoFinal.add(tempo);
            }
            return inicioEspera;
        } else {
            return tempo;
        }
    }

    public void calcEficiencia(double capacidadeRecebida) {
        this.metricas.calcEficiencia(capacidadeRecebida, tamProcessamento);
    }

    public double getTimeCriacao() {
        return tempoCriacao;
    }

    public List<Double> getTempoInicial() {
        return tempoInicial;
    }

    public List<Double> getTempoFinal() {
        return tempoFinal;
    }

    public MetricasTarefa getMetricas() {
        return metricas;
    }

    public int getEstado() {
        return this.estado;
    }
    
    public void setEstado(int estado) {
        this.estado = estado;
    }

    public int getIdentificador() {
        return this.identificador;
    }

    public String getAplicacao() {
        return aplicacao;
    }

    public boolean isCopy() {
        return copia;
    }

    public boolean isCopyOf(Tarefa tarefa) {
        if (this.identificador == tarefa.identificador && !this.equals(tarefa)) {
            return true;
        } else {
            return false;
        }
    }

    public double getMflopsProcessado() {
        return mflopsProcessado;
    }

    /**
     * @return the mflopsDesperdicados
     */
    public double getMflopsDesperdicados() {
        return mflopsDesperdicados;
    }

    public void incMflopsDesperdicados(double mflopsDesperdicados) {
        this.mflopsDesperdicados += mflopsDesperdicados;
    }
        

    public void setMflopsProcessado(double mflopsProcessado) {
        this.mflopsProcessado = mflopsProcessado;
    }

    public double getCheckPoint() {
        //return 1.0;//Fazer Chekcpoint a cada 1 megaflop
        //double tempo = mflopsProcessado/((CS_Processamento) localProcessamento).getPoderComputacional();
        //double resto = tempo%600;
        //return mflopsProcessado - ((CS_Processamento) localProcessamento).getPoderComputacional()*resto;
        return 0.0;
        //throw new UnsupportedOperationException("Not yet implemented");
    }

    public double getArquivoEnvio() {
        return arquivoEnvio;
    }

    @Override
    public String toString() {
        return "Task " + this.getIdentificador();
    }
}