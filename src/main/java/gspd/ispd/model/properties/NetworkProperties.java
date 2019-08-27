package gspd.ispd.model.properties;

/**
 * NetworkResource
 * 
 * Encapsulates the properties to be considered for network resources in the simulator
 * All properties are leave in public attributes, to reduce memory in the simulation motor,
 * once this data can have a lot of instances in simulation motor.
 * 
 * @author luisbaldissera
 */
public class NetworkProperties {

    /**
     * The bandwidth, <measure unit>
     */
    private int bandwidth;
    /**
     * The buffer size, <measure unit>
     */
    private int buffer;
}