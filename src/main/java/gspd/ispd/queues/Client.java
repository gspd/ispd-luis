package gspd.ispd.queues;

import java.util.List;

public class Client {
    private List<ServiceCentre<Client>> path;

    public Client() {
    }

    public List<ServiceCentre<Client>> getPath() {
        return path;
    }


}
