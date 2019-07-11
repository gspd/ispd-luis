package gspd.ispd.architecture.resources;

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
    private int memory;
    /**
     * The memory size of input task file, <mesure unit>
     */
    private int inputFileSize;
    /**
     * The memory size of output task file, <mesure unit>
     */
    private int outputFileSize;
    /**
     * The number of floating points operations
     */
    private int flop;
    /**
     * The communication size used by the task. <mesure unit>
     */
    private int comunicationSize;

    /**
     * TaskResource constructor
     *
     * @param memory The amount of primary memory required for the task, <mesure unit>
     * @param inSize The memory size of input task file, <mesure unit>
     * @param outSize The memory size of output task file, <mesure unit>
     * @param flop The number of floating points operations
     * @param comunicationSize The communication size used by the task. <mesure unit>
     */
    public TaskResource(int memory, int inSize, int outSize, int flop, int comunicationSize) {
        this.memory = memory;
        this.inputFileSize = inSize;
        this.outputFileSize = outSize;
        this.flop = flop;
        this.comunicationSize = comunicationSize;
    }

    /**
     * @return the primary memory size
     */
    public int getMemory() {
        return memory;
    }

    /**
     * @return the input file size
     */
    public int getInputFileSize() {
        return inputFileSize;
    }

    /**
     * @return the output file size
     */
    public int getOutputFileSize() {
        return outputFileSize;
    }

    /**
     * @return the number of floating point operations
     */
    public int getFlop() {
        return flop;
    }

    /**
     * @return the communication size
     */
    public int getComunicationSize() {
        return comunicationSize;
    }

    /**
     * Clones the Task Resource
     * <p><strong>NOTE:</strong> the method returns an {@code Object}, it is necessary casting like above:</p>
     * <p><code>TaskResource tr2 = (TaskResource) tr1.clone() </code></p>
     * @return the cloned object
     */
    @Override
    protected Object clone() {
        return (Object) new TaskResource(getMemory(), getInputFileSize(), getOutputFileSize(), getFlop(), getComunicationSize());
    }
}