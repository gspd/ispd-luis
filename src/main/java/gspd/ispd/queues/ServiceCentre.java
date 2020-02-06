package gspd.ispd.queues;

import gspd.ispd.util.ID;
import gspd.ispd.util.Multiplicity;

public interface ServiceCentre extends ID, Multiplicity {
    void request(Client client);
    void enter(Client client);
    void attend(Client client);
    void exit(Client client);
}
