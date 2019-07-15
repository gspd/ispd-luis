package gspd.ispd.architecture;

/**
 * Description of a processor and some operations with it.
 * 
 * @author luisbaldissera
 */
public class Processor {

    /**
     * The number of processor cores
     */
    private int numberOfCores;
    /**
     * The average frequency of the processor, in Hertz
     */
    private long frequency;
    /**
     * The number of floating point operations per instruction cycle
     * of the processor
     */
    private int flopPerCycle;

    /**
     * Constructs a processor
     * 
     * @param ncores the number of processor cores
     * @param frequency the average frequency of the processor, in Hertz
     * @param flopPerCycle the number of floating point operations per 
     * instruction cycle of the processor
     */
    public Processor(int ncores, long frequency, int flopPerCycle) {
        this.numberOfCores = ncores;
        this.frequency = frequency;
        this.flopPerCycle = flopPerCycle;
    }

    /**
     * @return the frequency of the processor
     */
    public long getFrequency() {
        return frequency;
    }
    
    /**
     * @return the number of CPU cores of the processor
     */
    public int getNumberOfCores() {
        return numberOfCores;
    }

    /**
     * @return the number of floating point operations per instruction cycle of the processor
     */
    public int getFlopPerCycle() {
        return flopPerCycle;
    }

    /**
     * @return the core power, in <measure_unit>
     */
    public long getCorePower() {
        return frequency * flopPerCycle;
    }
}