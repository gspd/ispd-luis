package gspd.ispd.queues.events;

import gspd.ispd.queues.Client;
import gspd.ispd.queues.ServiceCentre;

import java.util.EventObject;

/**
 * A future event specifies a source that made the event and involves
 * a client.
 */
public class FutureEvent extends EventObject implements Comparable<FutureEvent> {
    /**
     * The service centre that made the event
     */
    private ServiceCentre source;
    /**
     * the client that is subject of the event
     */
    private Client client;
    /**
     * the event type
     */
    private EventType eventType;
    /**
     * the time (in simulation) the event was creates
     */
    private double time;

    public FutureEvent(ServiceCentre source, Client client, EventType eventType) {
        super(source);
        this.source = source;
        this.eventType = eventType;
        this.client = client;
    }

    public EventType getEventType() {
        return eventType;
    }

    public Client getClient() {
        return client;
    }

    @Override
    public ServiceCentre getSource() {
        return this.source;
    }

    @Override
    public int compareTo(FutureEvent futureEvent) {
        return (int) (this.time - futureEvent.time);
    }
}
