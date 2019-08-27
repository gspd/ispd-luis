package gspd.ispd.model;

import java.util.List;

public class User {

    private String name;
    private List<Machine> machines;
    private List<VM> vms;

    public User(String name) {
        this.name = name;
    }

    public List<Machine> getMachines() {
        return machines;
    }

    public List<VM> getVMs() {
        return vms;
    }
}
