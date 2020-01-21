package gspd.ispd.queues;

public interface ServiceCentre {

    void onClientRequest(EventHandler<ClientEvent> handler);

    void onClientEntered(EventHandler<ClientEvent> handler);

    void onClientAttendance(EventHandler<ClientEvent> handler);

    void onClientExited(EventHandler<ClientEvent> handler);

}
