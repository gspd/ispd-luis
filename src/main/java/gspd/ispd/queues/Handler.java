package gspd.ispd.queues;

import java.util.EventListener;
import java.util.EventObject;

public interface Handler<E> extends EventListener {
    void handle(E event);
}
