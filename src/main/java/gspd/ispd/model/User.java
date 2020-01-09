package gspd.ispd.model;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class User {

    private static int ID = 1;

    private StringProperty name;
    private IntegerProperty id;
    private ObjectProperty<ObservableList<Machine>> machines;
    private ObjectProperty<ObservableList<VM>> vms;
    private ObjectProperty<ObservableList<Job>> jobs;

    public User() {
        this.id = new SimpleIntegerProperty(this, "id", ID);
        this.name = new SimpleStringProperty(this, "name", "");
        this.machines = new SimpleObjectProperty<>(this, "machines", FXCollections.observableArrayList());
        this.vms = new SimpleObjectProperty<>(this, "vms", FXCollections.observableArrayList());
        this.jobs = new SimpleObjectProperty<>(this, "jobs", FXCollections.observableArrayList());
        ID += 1;
    }

    public ObservableList<Machine> getMachines() {
        return machines.get();
    }

    public ObjectProperty<ObservableList<Machine>> machinesProperty() {
        return machines;
    }

    public void setMachines(ObservableList<Machine> machines) {
        this.machines.set(machines);
    }

    public ObservableList<VM> getVms() {
        return vms.get();
    }

    public ObjectProperty<ObservableList<VM>> vmsProperty() {
        return vms;
    }

    public void setVms(ObservableList<VM> vms) {
        this.vms.set(vms);
    }

    public ObservableList<Job> getJobs() {
        return jobs.get();
    }

    public ObjectProperty<ObservableList<Job>> jobsProperty() {
        return jobs;
    }

    public void setJobs(ObservableList<Job> jobs) {
        this.jobs.set(jobs);
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

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

}
