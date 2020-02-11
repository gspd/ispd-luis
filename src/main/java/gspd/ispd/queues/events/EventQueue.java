package gspd.ispd.queues.events;

import gspd.ispd.queues.disciplines.QueueDiscipline;

import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

public class EventQueue<E extends FutureEvent> extends LinkedBlockingQueue<E> {

    private QueueDiscipline<E> discipline;
    private List<E> queue;
    private int maxUsers;
    public static int INFINITY = -1;

}
