package gspd.ispd.queues;

import java.util.EventObject;

public class Event extends EventObject {

    private EventType eventType;

    public Event(Object source, EventType eventType) {
        super(source);
        this.eventType = eventType;
    }

    public EventType getEventType() {
        return eventType;
    }

}
