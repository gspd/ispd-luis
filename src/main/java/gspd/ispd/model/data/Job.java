package gspd.ispd.model.data;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

import java.util.List;

public class Job {

    // Process states (bib: Tanembaum, Operating Systems)
    public static final int BLOCKED = 0;
    public static final int READY = 1;
    public static final int RUNNING = 2;
    public static final int DONE = 3;

    private IntegerProperty id = new SimpleIntegerProperty();
    private IntegerProperty state = new SimpleIntegerProperty();
    private IntegerProperty dependencies = new SimpleIntegerProperty();
    private List<Job> triggers;

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public int getState() {
        return state.get();
    }

    public IntegerProperty stateProperty() {
        return state;
    }

    public void setState(int state) {
        this.state.set(state);
    }

    public int getDependencies() {
        return dependencies.get();
    }

    public IntegerProperty dependenciesProperty() {
        return dependencies;
    }

    public void setDependencies(int dependencies) {
        this.dependencies.set(dependencies);
    }
}
