package gspd.ispd.core;

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
     * The computing power, in <mesure unit>
     */
    public int computingPower;
    /**
     * The primary memory
     */
    public int memory;
    /**
     * The storage memory
     */
    public int storage;
}