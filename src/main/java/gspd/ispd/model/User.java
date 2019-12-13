package gspd.ispd.model;

import java.util.ArrayList;
import java.util.List;

public class User {

    private static int ID = 1;

    private String name;
    private int id;
    private List<Machine> machines;
    private List<VM> vms;
    private List<Job> jobs;

    public User(String name) {
        this.id = ID;
        ID += 1;
        this.name = name;
        this.machines = new ArrayList<>();
        this.vms = new ArrayList<>();
        this.jobs = new ArrayList<>();
    }

    public List<Machine> getMachines() {
        return machines;
    }

    public List<VM> getVMs() {
        return vms;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }
}
