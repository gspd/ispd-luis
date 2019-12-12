package gspd.ispd.model.properties;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * Encapsulates the properties to be considered for computing resources in the simulator
 * All properties are leave in public attributes, to reduce memory in the simulation motor,
 * once this data can have a lot of instances in simulation motor.
 * 
 * @author luisbaldissera
 */
public class ComputingProperty {

    private IntegerProperty computingPower = new SimpleIntegerProperty();
    private IntegerProperty memory = new SimpleIntegerProperty();
    private IntegerProperty storage = new SimpleIntegerProperty();

    public int getComputingPower() {
        return computingPower.get();
    }

    public int getMemory() {
        return memory.get();
    }

    public int getStorage() {
        return storage.get();
    }

    public void setComputingPower(int computingPower) {
        this.computingPower.set(computingPower);
    }

    public void setMemory(int memory) {
        this.memory.set(memory);
    }

    public void setStorage(int storage) {
        this.storage.set(storage);
    }
}