package gspd.ispd.model;


import gspd.ispd.queues.Client;
import gspd.ispd.queues.ServiceCentre;
import gspd.ispd.queues.events.FutureEventType;
import javafx.beans.property.*;
import javafx.collections.ObservableList;

public class Machine extends ServiceCentre {

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

    // machine event types
    public final static FutureEventType TASK_ARRIVED = FutureEventType.get("TASK_ARRIVED");
    public final static FutureEventType TASK_ATTENDANCE = FutureEventType.get("TASK_ATTENDANCE");
    public final static FutureEventType TASK_EXITED = FutureEventType.get("TASK_EXITED");
    public final static FutureEventType VM_REQUESTED = FutureEventType.get("VM_REQUESTED");
    public final static FutureEventType VM_ALLOCATED = FutureEventType.get("VM_ALLOCATED");
    public final static FutureEventType VM_CANCELED = FutureEventType.get("VM_CANCELED");

    public Machine() {
        setHandler(TASK_ARRIVED, this::onTaskArrived);
        setHandler(TASK_ATTENDANCE, this::attendTask);
    }

    private void onTaskArrived(Client client) {

    }

    private void attendTask(Client client) {

    }
}

