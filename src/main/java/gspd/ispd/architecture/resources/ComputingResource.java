package gspd.ispd.architecture.resources;

/**
 * ComputingResource
 * 
 * Encapsulates the properties to be considered for computing resources in the simulator
 * All properties are leave in public attributes, to reduce memory in the simulation motor,
 * once this data can have a lot of instances in simulation motor.
 * 
 * @author luisbaldissera
 */
public class ComputingResource {

    /**
     * The computing power, in <measure unit>
     */
    private int computingPower;
    /**
     * The primary memory size, in <measure unit>
     */
    private int memory;
    /**
     * The storage memory, in <measure unit>
     */
    private int storage;

    /**
     * ComputingResource constructor
     *
     * @param computingPower The computing power, in <mesure unit>
     * @param memory The primary memory
     * @param storage The storage memory
     */
    public ComputingResource(int computingPower, int memory, int storage) {
        this.computingPower = computingPower;
        this.memory = memory;
        this.storage = storage;
    }

    /**
     * @return the computing power
     */
    public int getComputingPower() {
        return computingPower;
    }

    /**
     * @return the primary memory size
     */
    public int getMemory() {
        return memory;
    }

    /**
     * @return the storage size
     */
    public int getStorage() {
        return storage;
    }

    /**
     * Clones the Computing Resource
     * <p><strong>NOTE:</strong> the method returns an {@code Object}, it is necessary casting like above:</p>
     * <p><code>TaskResource cr2 = (ComputingResource) cr1.clone() </code></p>
     * @return the cloned object
     */
    @Override
    protected Object clone() {
        return (Object) new ComputingResource(getComputingPower(), getMemory(), getStorage());
    }
}