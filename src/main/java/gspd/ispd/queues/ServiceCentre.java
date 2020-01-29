package gspd.ispd.queues;

import gspd.ispd.commons.ID;
import gspd.ispd.commons.Multiplicity;

public interface ServiceCentre extends ID, Multiplicity {
    void request(Client client);
    void enter(Client client);
    void attend(Client client);
    void exit(Client client);
}
