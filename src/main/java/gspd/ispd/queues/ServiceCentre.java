package gspd.ispd.queues;

import gspd.ispd.queues.events.FutureEvent;
import gspd.ispd.queues.events.FutureEventType;

import java.util.*;

public abstract class ServiceCentre {

    /**
     * Associates each event type with a client handler
     */
    private Map<FutureEventType, Handler<Client>> handlers;

    private List<ServiceCentre> output;
    private List<Client> input;
    private Client currentClient;
    private boolean on;

    // service centre events type
    public final static FutureEventType SERVER_ON = FutureEventType.get("SERVER_ON");
    public final static FutureEventType SERVER_OFF = FutureEventType.get("SERVER_OFF");
    public final static FutureEventType CLIENT_ARRIVED = FutureEventType.get("CLIENT_ARRIVED");
    public final static FutureEventType CLIENT_ENTERED = FutureEventType.get("CLIENT_ENTERED");
    public final static FutureEventType CLIENT_STARTED = FutureEventType.get("CLIENT_STARTED");
    public final static FutureEventType CLIENT_FINISHED = FutureEventType.get("CLIENT_FINISHED");
    public final static FutureEventType CLIENT_EXITED = FutureEventType.get("CLIENT_EXITED");

    public ServiceCentre() {
        handlers = new HashMap<>();
    }

    protected final void drop(Client client) {
        if (currentClient == client)
            currentClient = null;
        else
            throw new ConcurrentModificationException("'currentClient' was changed without expecting, or already dropped");
    }

    protected final void send(Client client, int index) {
        output.get(index).submit(client);
    }

    protected final void accept(Client client) {

    }

    protected final void reject(Client client) {
        if (currentClient == client) {
            drop(client);
        } else  {
            input.remove(client);
        }
    }

    protected final void retry(Client client) {
        reject(client);
        submit(client);
    }

    protected List<ServiceCentre> getOutput() {
        return new ArrayList<>(output);
    }

    /**
     * Submit a client to the input queue of the service centre
     *
     * @param client the client to submit
     */
    public final void submit(Client client) {
        input.add(client);
    }

    /**
     * <p>
     * Set how this service centre handles a given event type
     * <p>
     * It's not possible to set more than one handler to a given
     * event. If application tries to do so, the most recent call
     * sets the handler
     * <p>
     * To unset a {@code handler} for any given event type, put
     * {@code null} as the handler
     *
     * @param type the event type
     * @param handler the handler
     *
     * @see Handler
     * @see FutureEventType
     */
    public void setHandler(FutureEventType type, Handler<Client> handler) {
        if (handler == null)
            handlers.remove(type);
        else
            handlers.put(type, handler);
    }

    /**
     * <p>
     * Handles the event type that can occur with a client in this service
     * centre.
     * <p>
     * In order to the event be corrected handler, a handler should've be
     * set with {@link ServiceCentre#setHandler(FutureEventType, Handler)}. If no
     * handler was set, nothing happens
     *
     * @param type the event type
     * @param client the subject client
     *
     * @see Handler
     * @see FutureEventType
     */
    public void handle(FutureEventType type, Client client) {
        Handler<Client> handler = handlers.get(type);
        if (handler != null) {
            handler.handle(client);
        }
    }
}
