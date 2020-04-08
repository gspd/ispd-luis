package gspd.ispd.motor.workload;

import gspd.ispd.commons.ISPDType;
import gspd.ispd.model.workload.Workload;
import gspd.ispd.motor.carga.GerarCarga;
import gspd.ispd.motor.filas.RedeDeFilas;
import gspd.ispd.motor.filas.Tarefa;

import java.util.List;

/**
 * Adapter class to transform a WorkloadGenerator into a
 * 'GerarCarga' class
 */
public class WorkloadGeneratorAdapter extends GerarCarga {

    /**
     * The wrapped generator
     */
    private WorkloadGenerator generator;
    public WorkloadGeneratorAdapter(WorkloadGenerator generator) {
        this.generator = generator;
    }

    @Override
    public List<Tarefa> toTarefaList(RedeDeFilas rdf) {
        generator.setQueueNetwork(rdf);
        return generator.generateTaskList();
    }

    @Override
    public String toString() {
        return generator.toString();
    }

    @Override
    public int getTipo() {
        int intType = 0;
        ISPDType type = generator.getType();
        if (type.isTypeOf(ForSchedulerWorkloadGenerator.FOR_SCHEDULER_TYPE)) {
            intType = GerarCarga.FORNODE;
        } else if (type == TraceWorkloadGenerator.TRACE_WORKLOAD_TYPE) {
            intType = GerarCarga.TRACE;
        }
        return intType;
    }
}
