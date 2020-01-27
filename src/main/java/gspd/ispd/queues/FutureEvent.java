package gspd.ispd.queues;

import java.util.EventObject;

/**
 * A future event specifies a source that made the event and involves
 * a client.
 * @param <C>
 */
public class FutureEvent<C extends Client> extends EventObject {
    /**
     * The service centre that made the event
     */
    private ServiceCentre<C> source;
    /**
     * the client that is subject of the event
     */
    private C client;
    /**
     * the event type
     */
    private EventType eventType;
    /**
     * the time (in simulation) the event was creates
     */
    private double time;

    public FutureEvent(ServiceCentre<C> source, C client, EventType eventType) {
        super(source);
        this.source = source;
        this.eventType = eventType;
        this.client = client;
    }

    public EventType getEventType() {
        return eventType;
    }

    public C getClient() {
        return client;
    }

    @Override
    public ServiceCentre<C> getSource() {
        return this.source;
    }
}
