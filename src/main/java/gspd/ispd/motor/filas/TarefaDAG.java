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
 * This class defines a task that can be modelled in a DAG
 *
 * @author Luis Baldissera
 */
public class TarefaDAG extends Tarefa {

    /**
     * The list os suffixes task of this task
     * <p>
     * Suffixes are those that can only occur after this task already STARTED
     */
    private List<TarefaDAG> suffixes;
    /**
     * The number of predecessors of this task
     * <p>
     * Those this task is a successor of
     */
    private int predecessors;
    /**
     * The list of successors tasks of this task
     * <p>
     * Successors are those that can only occur after this task already FINISHED
     */
    private List<TarefaDAG> successors;
    /**
     * The number of prefixes of this task
     * <p>
     * Those this task is a suffix of
     */
    private int prefixes;
    /**
     * The catches are tasks those the flow is redirected when a fail occur in this task
     */
    private List<TarefaDAG> catches;
    /**
     * The number os throwers task
     * <p>
     * Those this task is a catch of
     */
    private int throwers;

    public TarefaDAG(int id, String proprietario, String aplicacao, CentroServico origem, double arquivoEnvio, double tamProcessamento, double tempoCriacao) {
        this(id, proprietario, aplicacao, origem, arquivoEnvio, 0, tamProcessamento, tempoCriacao);
    }

    public TarefaDAG(int id, String proprietario, String aplicacao, CentroServico origem, double arquivoEnvio, double arquivoRecebimento, double tamProcessamento, double tempoCriacao) {
        super(id, proprietario, aplicacao, origem, arquivoEnvio, arquivoRecebimento, tamProcessamento, tempoCriacao);
        suffixes = new ArrayList<>();
        successors = new ArrayList<>();
        catches = new ArrayList<>();
        predecessors = 0;
        prefixes = 0;
        throwers = 0;
    }

    public void addSuffix(TarefaDAG tarefa) {
        suffixes.add(tarefa);
        tarefa.prefixes += 1;
    }

    public void addSuccessor(TarefaDAG tarefa) {
        successors.add(tarefa);
        tarefa.predecessors += 1;
    }

    public void addCatch(TarefaDAG tarefa) {
        catches.add(tarefa);
        tarefa.throwers += 1;
    }

    public void notifySuffixes() {
        suffixes.forEach(task -> task.prefixes -= 1);
    }

    public void notifySuccessors() {
        successors.forEach(task -> task.predecessors -= 1);
    }

    public void notifyCatches() {
        catches.forEach(task -> task.throwers += 1);
    }

    public List<TarefaDAG> getSuccessors() {
        return successors;
    }

    public List<TarefaDAG> getSuffixes() {
        return suffixes;
    }

    @Override
    public String toString() {
        return "TaskDAG#" + getIdentificador() + "{proprietario=" + getProprietario() + "}";
    }
}