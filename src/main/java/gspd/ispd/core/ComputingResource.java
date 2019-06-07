package gspd.ispd.core;

/**
 * ComputingResource
 * 
 * Encapsulates the properties to be considered for computing resources in the simulator
 * All properties are leave in public attributes, to reduce memory in the simulation motor,
 * once this data can have a lot of instances in simulation motor.
 * 
 * @author Luís Vinícius Omar Baldissera
 */
public class ComputingResource {

    /**
     * The number of processing cores
     */
    public int ncores;
    /**
     * The processing power of each core
     */
    public int corePower;
    /**
     * The primary memory
     */
    public int memory;
    /**
     * The storage memory
     */
    public int storage;
}