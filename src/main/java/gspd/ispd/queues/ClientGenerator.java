package gspd.ispd.queues;

import java.util.List;

public abstract class ClientGenerator {

    private List<Client> generated;

    public ClientGenerator() {
    }

    private void produce() {
        Client client = generateClient();
        generated.add(client);
    }

    protected abstract Client generateClient();

}
