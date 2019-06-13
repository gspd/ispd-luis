package gspd.ispd.core;

/**
 * TaskResource
 * 
 * Encapsulates the properties to be considered for task resources in the simulator.
 * All properties are leave in public attributes, to reduce memory in the simulation motor,
 * once this data can have a lot of instances in simulation motor.
 * 
 * @author luisbaldissera
 */
public class TaskResource {

    /**
     * The amount of primary memory required for the task, <mesure unit>
     */
    public int memory;
    /**
     * The memory size of input task file, <mesure unit>
     */
    public int inSize;
    /**
     * The memory size of output task file, <mesure unit>
     */
    public int outSize;
    /**
     * The number of floating points operations
     */
    public int flop;
}