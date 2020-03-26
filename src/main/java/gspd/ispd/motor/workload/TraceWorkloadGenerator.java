package gspd.ispd.motor.workload;

import gspd.ispd.commons.ISPDType;

import java.io.File;

public abstract class TraceWorkloadGenerator extends WorkloadGenerator {

    public static final ISPDType TRACE_WORKLOAD = ISPDType.type(WorkloadGenerator.WORKLOAD_TYPE, "TRACE_WORKLOAD");

    //////////////////////////////////
    ///////// CONSTRUCTOR ////////////
    //////////////////////////////////

    public TraceWorkloadGenerator() {
        setType(TRACE_WORKLOAD);
    }

    //////////////////////////////////
    ////////// PROPERTIES ////////////
    //////////////////////////////////

    private File traceFile;
    public File getTraceFile() {
        return traceFile;
    }
    public void setTraceFile(File traceFile) {
        this.traceFile = traceFile;
    }
}
