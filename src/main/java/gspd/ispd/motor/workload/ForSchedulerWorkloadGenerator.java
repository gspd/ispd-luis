package gspd.ispd.motor.workload;

import gspd.ispd.commons.IDSystem;
import gspd.ispd.commons.ISPDType;
import gspd.ispd.motor.filas.Tarefa;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ForSchedulerWorkloadGenerator extends WorkloadGenerator {

    public static final ISPDType FOR_SCHEDULER_TYPE = ISPDType.type(WorkloadGenerator.WORKLOAD_TYPE, "FOR_SCHEDULER_TYPE");

    ///////////////////////////////////////////////
    /////////////// CONSTRUCTOR ///////////////////
    ///////////////////////////////////////////////

    public ForSchedulerWorkloadGenerator() {
        setType(FOR_SCHEDULER_TYPE);
    }

    ///////////////////////////////////////////////
    //////////////// OVERRIDES ////////////////////
    ///////////////////////////////////////////////

    @Override
    public List<Tarefa> generateTaskList() {
        List<Tarefa> taskList = new ArrayList<>();
        setIdSystem(new IDSystem());
        for (WorkloadGenerator generator : getGenerators()) {
            generator.setQueueNetwork(getQueueNetwork());
            generator.setIdSystem(getIdSystem());
            taskList.addAll(generator.generateTaskList());
        }
        return taskList;
    }

    //////////////////////////////////////////////////
    //////////////////// PROPERTIES //////////////////
    //////////////////////////////////////////////////

    private List<WorkloadGenerator> generators = new ArrayList<>();

    /**
     * Return a read-only list of all workload generators
     * <p>
     * <b>NOTE:</b> adding or removing elements in this list does <b>not</b>
     * change the real list. Use {@code addGenerator()} and
     * {@code removeGenerator()} instead
     *
     * @see #addGenerator(SingleSchedulerWorkloadGenerator)
     * @see #removeGenerator(SingleSchedulerWorkloadGenerator)
     *
     * @return a list with the generators
     */
    public List<WorkloadGenerator> getGenerators() {
        return Collections.unmodifiableList(List.copyOf(generators));
    }
    public boolean addGenerator(SingleSchedulerWorkloadGenerator generator) {
        return generators.add(generator);
    }
    public boolean removeGenerator(SingleSchedulerWorkloadGenerator generator) {
        return generators.remove(generator);
    }
}
