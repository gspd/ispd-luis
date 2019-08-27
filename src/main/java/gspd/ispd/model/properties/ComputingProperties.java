package gspd.ispd.model.properties;

/**
 * ComputingResource
 * 
 * Encapsulates the properties to be considered for computing resources in the simulator
 * All properties are leave in public attributes, to reduce memory in the simulation motor,
 * once this data can have a lot of instances in simulation motor.
 * 
 * @author luisbaldissera
 */
public class ComputingProperties {

    private int computingPower;
    private int memory;
    private int storage;


}