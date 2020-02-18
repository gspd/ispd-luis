package gspd.ispd.model.data;


import gspd.ispd.queues.ServiceCentre;
import gspd.ispd.queues.events.FutureEventType;
import javafx.beans.property.*;

public class Machine extends ServiceCentre {

    private IntegerProperty id = new SimpleIntegerProperty(this, "ID", 0);
    private StringProperty name = new SimpleStringProperty(this, "Name", "");
    private ObjectProperty<User> owner = new SimpleObjectProperty<>(this, "Owner");
    private DoubleProperty power = new SimpleDoubleProperty(this, "Power", 0);
    private DoubleProperty load = new SimpleDoubleProperty(this, "Load Factor", 0);
    private DoubleProperty memory = new SimpleDoubleProperty(this, "Memory", 0);
    private DoubleProperty storage = new SimpleDoubleProperty(this, "Storage", 0);
    private IntegerProperty cores = new SimpleIntegerProperty(this, "Cores", 1);
    private StringProperty scheduling = new SimpleStringProperty(this, "Scheduler", "RR");
    // private ObjectProperty<ObservableList<Hardware>> slaves;

    // machine event types
    public final static FutureEventType TASK_ARRIVED = FutureEventType.get("TASK_ARRIVED");
    public final static FutureEventType TASK_ATTENDANCE = FutureEventType.get("TASK_ATTENDANCE");
    public final static FutureEventType TASK_EXITED = FutureEventType.get("TASK_EXITED");
    public final static FutureEventType VM_REQUESTED = FutureEventType.get("VM_REQUESTED");
    public final static FutureEventType VM_ALLOCATED = FutureEventType.get("VM_ALLOCATED");
    public final static FutureEventType VM_CANCELED = FutureEventType.get("VM_CANCELED");

    public Machine() {
    }

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public double getPower() {
        return power.get();
    }

    public DoubleProperty powerProperty() {
        return power;
    }

    public void setPower(double power) {
        this.power.set(power);
    }

    public double getLoad() {
        return load.get();
    }

    public DoubleProperty loadProperty() {
        return load;
    }

    public void setLoad(double load) {
        this.load.set(load);
    }

    public double getMemory() {
        return memory.get();
    }

    public DoubleProperty memoryProperty() {
        return memory;
    }

    public void setMemory(double memory) {
        this.memory.set(memory);
    }

    public double getStorage() {
        return storage.get();
    }

    public DoubleProperty storageProperty() {
        return storage;
    }

    public void setStorage(double storage) {
        this.storage.set(storage);
    }

    public int getCores() {
        return cores.get();
    }

    public IntegerProperty coresProperty() {
        return cores;
    }

    public void setCores(int cores) {
        this.cores.set(cores);
    }

    public String getScheduling() {
        return scheduling.get();
    }

    public StringProperty schedulingProperty() {
        return scheduling;
    }

    public void setScheduling(String scheduling) {
        this.scheduling.set(scheduling);
    }

    public User getOwner() {
        return owner.get();
    }

    public ObjectProperty<User> ownerProperty() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner.set(owner);
    }
}

