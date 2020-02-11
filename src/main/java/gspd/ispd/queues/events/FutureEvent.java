package gspd.ispd.queues.events;

import gspd.ispd.queues.Client;
import gspd.ispd.queues.ServiceCentre;
import gspd.ispd.queues.Simulation;

/**
 * A future event specifies a source that made the event and involves
 * a client. A {@code FutureEvent} is anything that can change the state
 * os a queue simulation. It basically stores the context about some event
 * in the queue system:
 * <ul>
 *   <li>
 *     In which <b>simulation</b> experiment the event happened
 *   </li>
 *   <li>
 *     With which <b>client</b> it happened
 *   </li>
 *   <li>
 *     Which <b>service centre</b> reported the event
 *   </li>
 *   <li>
 *     What <b>type of event</b> happened
 *   </li>
 *   <li>
 *     The <b>time</b> it happens
 *   </li>
 * </ul>
 * <p>
 * In other words: what happened with the client in the the service centre, in that time
 * during that simulation
 */
public class FutureEvent {
    // The future events type
    public final static FutureEventType CLIENT_REQUEST = FutureEventType.get("CLIENT_REQUEST");
    public final static FutureEventType CLIENT_ENTERED = FutureEventType.get("CLIENT_ENTERED");
    public final static FutureEventType CLIENT_ATTENDANCE = FutureEventType.get("CLIENT_ATTENDANCE");
    public final static FutureEventType CLIENT_EXITED = FutureEventType.get("CLIENT_EXITED");
    public final static FutureEventType CLIENT_CANCELED = FutureEventType.get("CLIENT_CANCELED");
    public final static FutureEventType CLIENT_REJECTED = FutureEventType.get("CLIENT_REJECTED");
    public final static FutureEventType SERVER_OVERLOADED = FutureEventType.get("SERVER_OVERLOADED");
    /**
     * The service centre that made the event
     */
    private ServiceCentre serviceCentre;
    /**
     * The client that is subject of the event
     */
    private Client client;
    /**
     * The event type
     */
    private FutureEventType eventType;
    /**
     * The time (in simulation) the event is supposed to happen
     */
    private double time;
    /**
     * The simulation this event is associated with
     */
    private Simulation simulation;

    /**
     * Future Event constructor.
     *
     * @param simulation the simulation associated with this event
     * @param serviceCentre the service centre that 'created' the event
     * @param client the client who is subject of the event
     * @param eventType the event type
     */
    public FutureEvent(Simulation simulation, ServiceCentre serviceCentre, Client client, FutureEventType eventType) {
        this.simulation = simulation;
        this.serviceCentre = serviceCentre;
        this.eventType = eventType;
        this.client = client;
        this.time = simulation.getTimer().getTime();
    }

    public FutureEventType getEventType() {
        return eventType;
    }

    public Client getClient() {
        return client;
    }

    public Simulation getSimulation() {
        return simulation;
    }

    public ServiceCentre getServiceCentre() {
        return this.serviceCentre;
    }

    public double getTime() {
        return time;
    }
}
