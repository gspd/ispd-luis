package gspd.ispd.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class User {

    private static int ID = 1;

    private String name;
    private int id;
    private ObservableList<Machine> machines;
    private ObservableList<VM> vms;
    private ObservableList<Job> jobs;

    public User(String name) {
        this.id = ID;
        ID += 1;
        this.name = name;
        this.machines = FXCollections.observableArrayList();
        this.vms = FXCollections.observableArrayList();
        this.jobs = FXCollections.observableArrayList();
    }

    public User() {
        this(null);
    }

    public ObservableList<Machine> getMachines() {
        return machines;
    }

    public ObservableList<VM> getVMs() {
        return vms;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }
}
