package gspd.ispd.queues;

public class ClientEvent extends Event {

    Client client;
    Simulation simulation;

    public ClientEvent(Client client, EventType eventType, Simulation simulation) {
        super(client, eventType);
        this.simulation = simulation;
    }

    public <C extends Client> C getClient() {
        return (C) client;
    }

    public Simulation getSimulation() {
        return simulation;
    }
}
