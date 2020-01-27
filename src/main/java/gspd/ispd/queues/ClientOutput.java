package gspd.ispd.queues;

public interface ClientOutput<C extends Client> {
    void transmitClient(C client);
}
