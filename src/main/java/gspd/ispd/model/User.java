package gspd.ispd.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class User {

    private static int ID = 1;

    private StringProperty name;
    private IntegerProperty id;
    private ObservableList<Machine> machines;
    private ObservableList<VM> vms;
    private ObservableList<Job> jobs;

    public User() {
        this.id = new SimpleIntegerProperty(this, "id", ID);
        this.name = new SimpleStringProperty(this, "name", "");
        this.machines = FXCollections.observableArrayList();
        this.vms = FXCollections.observableArrayList();
        this.jobs = FXCollections.observableArrayList();
        ID += 1;
    }

    public ObservableList<Machine> getMachines() {
        return machines;
    }

    public ObservableList<VM> getVms() {
        return vms;
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
