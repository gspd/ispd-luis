package gspd.ispd.queues;

public interface ClientInput<C extends Client> {
    void receiveClient(C client);
}
