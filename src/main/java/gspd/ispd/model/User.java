package gspd.ispd.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.List;

public class User {

    private StringProperty name = new SimpleStringProperty();
    private IntegerProperty id = new SimpleIntegerProperty();
    private List<Machine> machines;
    private List<VM> vms;
    private List<Job> jobs;

    public User(String name) {
        this.name.set(name);
    }

    public List<Machine> getMachines() {
        return machines;
    }

    public List<VM> getVMs() {
        return vms;
    }
}
