package gspd.ispd.motor.workload;

import gspd.ispd.commons.ISPDType;
import gspd.ispd.motor.filas.Tarefa;

import java.util.List;

public abstract class WorkloadGenerator {

    public static final ISPDType WORKLOAD_TYPE = ISPDType.type(null, "WORKLOAD_TYPE");

    //////////////////////////////////////
    ///////// CONSTRUCTOR ////////////////
    //////////////////////////////////////

    public WorkloadGenerator() {
        setType(WORKLOAD_TYPE);
    }


    //////////////////////////////////////
    ///////////// ABSTRACTS //////////////
    //////////////////////////////////////

    /**
     * Generate the list of tasks
     * @return the list of tasks
     */
    public abstract List<Tarefa> generateTaskList();

    //////////////////////////////////////
    ///////////// METHODS ////////////////
    //////////////////////////////////////

    protected <G extends WorkloadGenerator> void setUpClone(G generator) {
        generator.setType(getType());
    }

    //////////////////////////////////////
    ///////////// PROPERTIES /////////////
    //////////////////////////////////////

    /**
     * The type of the workload
     */
    public static final String TYPE_PROPERTY = "type";
    private ISPDType type;
    public ISPDType getType() {
        return type;
    }
    public void setType(ISPDType type) {
        this.type = type;
    }

    /**
     * The string that represents this workload
     */
    public static final String STRING_PROPERTY = "string";
    private String string;
    public String getString() {
        return string;
    }
    public void setString(String string) {
        this.string = string;
    }
}
