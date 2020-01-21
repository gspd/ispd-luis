package gspd.ispd.queues;

import java.util.EventListener;
import java.util.EventObject;

public interface EventHandler<T extends EventObject> extends EventListener {
    void handle(T event);
}
