package gspd.ispd.core;

/**
 * NetworkResource
 * 
 * Encapsulates the properties to be considered for network resources in the simulator
 * All properties are leave in public attributes, to reduce memory in the simulation motor,
 * once this data can have a lot of instances in simulation motor.
 * 
 * @author luisbaldissera
 */
public class NetworkResource {

    /**
     * The bandwidth, <mesure unit>
     */
    public int bandwidth;
    /**
     * The buffer size, <mesure unit>
     */
    public int buffer;
}