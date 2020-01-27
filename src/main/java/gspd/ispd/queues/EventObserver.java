package gspd.ispd.queues;

import java.util.EventListener;
import java.util.EventObject;

public interface EventObserver<E extends EventObject> extends EventListener {
    void update(E event);
}
