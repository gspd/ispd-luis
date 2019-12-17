package gspd.ispd.model;

public class VM {

    private static int ID = 1;

    private int id;
    private User owner;
    private String hypervisor;
    private int cores;
    private double memory;
    private double storage;
    private String os;

    public VM(User owner, String hypervisor, int cores, double memory, double storage, String os) {
        this.id = ID;
        ID += 1;
        this.owner = owner;
        this.hypervisor = hypervisor;
        this.cores = cores;
        this.memory = memory;
        this.storage = storage;
        this.os = os;
    }

    public int getId() {
        return id;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public String getHypervisor() {
        return hypervisor;
    }

    public void setHypervisor(String hypervisor) {
        this.hypervisor = hypervisor;
    }

    public int getCores() {
        return cores;
    }

    public void setCores(int cores) {
        this.cores = cores;
    }

    public double getMemory() {
        return memory;
    }

    public void setMemory(double memory) {
        this.memory = memory;
    }

    public double getStorage() {
        return storage;
    }

    public void setStorage(double storage) {
        this.storage = storage;
    }

    public String getOS() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getOwnerName() {
        return owner.getName();
    }
}
