package gspd.ispd.queues;

import gspd.ispd.queues.events.EventObserver;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class Timer {

    /**
     * The observers that receives a notification when time changes
     */
    private List<EventObserver<TimeEvent>> observers;
    /**
     * The current time of the timer
     */
    private double time;
    /**
     * This semaphore makes sure that no two threads try to set
     * the time of the timer at same time.
     */
    private Semaphore semaphore;

    public Timer() {
        observers = new ArrayList<>();
        reset();
    }

    public void reset() {
        // use setTime instead of 'time = 0' here.
        // setTime notify all the observers
        time = 0;
        semaphore = new Semaphore(1, true);
    }

    public double getTime() {
        return time;
    }

    private void setTime(double time) {
        synchronized (Timer.class) {
            this.time = time;
        }
        // notify all observers that time has been set
        notifyObservers();
    }

    public final void incrementTime(double time) {
        setTime(this.time + time);
    }

    private void notifyObservers() {
        TimeEvent event = new TimeEvent(this, time);
        observers.parallelStream().forEach(observer -> observer.update(event));
    }

    public void addObserver(EventObserver<TimeEvent> observer) {
        if (!observers.contains(observer)) {
            observers.add(observer);
        }
    }

    public void removeObserver(EventObserver<TimeEvent> observer) {
        observers.remove(observer);
    }

    public void clearObservers() {
        observers.clear();
    }
}
