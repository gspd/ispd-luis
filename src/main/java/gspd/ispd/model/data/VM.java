package gspd.ispd.model.data;

import javafx.beans.property.*;

public class VM {

    private static int ID = 1;

    private IntegerProperty id;
    private ObjectProperty<User> owner;
    private StringProperty hypervisor;
    private IntegerProperty cores;
    private DoubleProperty memory;
    private DoubleProperty storage;
    private StringProperty os;

    private VM(int id, User owner, String hypervisor, int cores, double memory, double storage, String os) {
        // set the VM parameters
        this.id = new SimpleIntegerProperty(this, "id", id);
        this.owner = new SimpleObjectProperty<>(this, "owner", owner);
        this.hypervisor = new SimpleStringProperty(this, "hypervisor", hypervisor);
        this.cores = new SimpleIntegerProperty(this, "cores", cores);
        this.memory = new SimpleDoubleProperty(this, "memory", memory);
        this.storage = new SimpleDoubleProperty(this, "storage", storage);
        this.os = new SimpleStringProperty(this, "os", os);
        // increment the ID
        // IMPORTANT! don't do this anywhere else
        ID += 1;
    }

    public VM() {
        this(ID, null, "", 0, 0.0, 0.0, "");
    }

    public VM(VM vm) {
        this(ID, vm.getOwner(), vm.getHypervisor(), vm.getCores(), vm.getMemory(), vm.getStorage(), vm.getOs());
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

    public User getOwner() {
        return owner.get();
    }

    public Property<User> ownerProperty() {
        return owner;
    }

    public void setOwner(User owner) {
       this.owner.set(owner);
    }

    public String getHypervisor() {
        return hypervisor.get();
    }

    public StringProperty hypervisorProperty() {
        return hypervisor;
    }

    public void setHypervisor(String hypervisor) {
        this.hypervisor.set(hypervisor);
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

    public String getOs() {
        return os.get();
    }

    public StringProperty osProperty() {
        return os;
    }

    public void setOs(String os) {
        this.os.set(os);
    }

    public VM copy() {
        return new VM(this);
    }
}
