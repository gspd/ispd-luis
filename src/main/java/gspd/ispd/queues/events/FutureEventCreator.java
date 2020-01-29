package gspd.ispd.queues.events;

import gspd.ispd.queues.Client;

public abstract class FutureEventCreator {

    public abstract FutureEvent createEvent(Client client);
}
