package gspd.ispd.queues;

import gspd.ispd.queues.events.FutureEventType;

public abstract class Client {

    /**
     * The simulation that own this client
     */
    private Simulation simulation;

    private ServiceCentre currentServiceCentre;

    // client future events types
    public final static FutureEventType SERVER_ENTER = FutureEventType.get("SERVER_ENTER");
    public final static FutureEventType SERVER_ATTENDANCE = FutureEventType.get("SERVER_ATTENDANCE");
    public final static FutureEventType SERVER_EXIT = FutureEventType.get("SERVER_EXIT");

    public Client(Simulation simulation) {
        this.simulation = simulation;
    }

    public Simulation getSimulation() {
        return simulation;
    }

    /**
     * Tell this client that it needs no go to another service centre
     *
     * @param serviceCentre the new service centre
     */
    public void driveTo(ServiceCentre serviceCentre) {

    }

    public void retry() {
        this.driveTo(currentServiceCentre);
    }
}
