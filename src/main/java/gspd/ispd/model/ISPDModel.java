package gspd.ispd.model;

import javafx.beans.property.*;
import javafx.collections.ObservableList;

import java.util.List;

public class ISPDModel {

    public static final int GRID = 0;
    public static final int IAAS = 1;

    // the model file name
    private StringProperty name = new SimpleStringProperty();
    // the edited file flag
    private BooleanProperty edited = new SimpleBooleanProperty();

    private Hardware hardware;
    private Workload workload;
    private ListProperty<Hypervisor> hypervisors = new SimpleListProperty<>();
    private ListProperty<User> users = new SimpleListProperty<>();

    private static int modelType;

    public StringProperty nameProperty() {
        return name;
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public boolean isEdited() {
        return edited.get();
    }

    public BooleanProperty editedProperty() {
        return edited;
    }

    public Hardware getHardware() {
        return hardware;
    }

    public Workload getWorkload() {
        return workload;
    }

    public ListProperty<User> usersProperty() {
        return users;
    }

    public static int getModelType() {
        return modelType;
    }
}
