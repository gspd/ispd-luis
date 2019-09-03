package gspd.ispd.model;

import javafx.beans.property.*;

public class VM {

    private IntegerProperty id = new SimpleIntegerProperty();
    private ObjectProperty<User> owner = new SimpleObjectProperty<>();
    private StringProperty hypervisor = new SimpleStringProperty();
    private IntegerProperty cores = new SimpleIntegerProperty();
    private IntegerProperty memory = new SimpleIntegerProperty();
    private IntegerProperty storage = new SimpleIntegerProperty();
    private StringProperty os = new SimpleStringProperty();

}
