package gspd.ispd.queues.time;

import java.util.EventObject;

public class TimeEvent extends EventObject {

    private Timer source;
    private double time;

    public TimeEvent(Timer source, double time) {
        super(source);
        this.source = source;
        this.time = time;
    }
}
