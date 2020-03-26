package gspd.ispd.motor.workload;

import gspd.ispd.commons.ISPDType;
import gspd.ispd.fxgui.workload.dag.DAG;
import gspd.ispd.motor.filas.Tarefa;

import java.util.ArrayList;
import java.util.List;

public class DAGWorkloadGenerator extends SingleSchedulerWorkloadGenerator {

    public static final ISPDType DAG_WORKLOAD_TYPE = ISPDType.type(SingleSchedulerWorkloadGenerator.SINGLE_SCHEDULER_TYPE, "DAG_WORKLOAD");

    ////////////////////////////////////////
    //////////// CONSTRUCTOR ///////////////
    ////////////////////////////////////////

    public DAGWorkloadGenerator() {
        setType(DAG_WORKLOAD_TYPE);
    }

    ////////////////////////////////////////
    ///////////// OVERRIDES ////////////////
    ////////////////////////////////////////

    @Override
    public List<Tarefa> generateTaskList() {
        List<Tarefa> taskList = new ArrayList<>();
        // build the task chain and return the initial tasks
        return taskList;
    }

    @Override
    public SingleSchedulerWorkloadGenerator clone() {
        return null;
    }

    /////////////////////////////////////////
    ///////////// PROPERTIES ////////////////
    /////////////////////////////////////////

    private DAG dag;
    public DAG getDag() {
        return dag;
    }
    public void setDag(DAG dag) {
        this.dag = dag;
    }
}
