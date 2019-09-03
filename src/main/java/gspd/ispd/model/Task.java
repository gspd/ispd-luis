package gspd.ispd.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;

import java.util.List;

public class Task {

    // Process states (bib: Tanembaum, Operating Systems)
    public static final int BLOCKED = 0;
    public static final int READY = 1;
    public static final int RUNNING = 2;
    public static final int DONE = 3;

    private IntegerProperty id = new SimpleIntegerProperty();
    private IntegerProperty state = new SimpleIntegerProperty();

    private IntegerProperty dependencies = new SimpleIntegerProperty();
    private List<Task> triggers;
}
