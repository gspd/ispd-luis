package gspd.ispd.model;


import javafx.beans.property.*;
import javafx.collections.ObservableList;

public class Machine {

    private IntegerProperty id;
    private StringProperty name;
    private ObjectProperty<User> owner;
    private DoubleProperty power;
    private DoubleProperty load;
    private DoubleProperty memory;
    private DoubleProperty storage;
    private IntegerProperty cores;
    private StringProperty scheduling;
    private ObjectProperty<ObservableList<Hardware>> slaves;

}
