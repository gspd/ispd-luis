package gspd.ispd.queues.timing;

import gspd.ispd.util.Handler;
import gspd.ispd.queues.Simulation;
import javafx.util.Pair;

import java.util.*;

/**
 * <p>
 * Timer defines the clock of the discrete simulation. It does not backward. It is possible
 * to schedule to be notified only in the future
 * <p>
 * There is only one timer for each simulation, and just one simulation can be
 * associated with a timer.
 * <p>
 * All timer states are ready only, being set only in the construction.
 * <p>
 * Timer can be listened for time changes with implementations of {@code Handle<Timer>}
 * <p>
 * The timer construction is private, being necessary to create each timer associated
 * with the simulation through {@code getInstance}.
 *
 * @author Luís Baldissera
 *
 * @see Simulation
 * @see Handler
 */
public class Timer {
    /**
     * The timers map is to allow a like 'multi-singleton' that permits just one
     * timer for simulation
     */
    private final static Map<Simulation, Timer> timers = new HashMap<>();
    /**
     * The current time of the timer
     */
    private double time;
    /**
     * The simulation this timer is associated with
     */
    private Simulation simulation;
    /**
     * <p>
     * The scheduled instants that can be called by a handler/listener to be
     * notified when the instant happens.
     * <p>
     * The {@code Pair<Double, Handler<Timer>>} indicates:
     * <ul>
     * <li>{@code Double}: the time scheduled</li>
     * <li>{@code Handler<Timer>}: the handler that asked to be notified</li>
     * </ul>
     */
    private PriorityQueue<Pair<Double, Handler<Timer>>> scheduledInstant;
    /**
     * The scheduled priority queue comparator
     */
    private final static Comparator<? super Pair<Double, Handler<Timer>>> scheduledComparator = Comparator.comparingDouble(Pair::getKey);

    /**
     * The timer constructor is made private in order to set up singleton pattern
     */
    private Timer(Simulation simulation) {
        this.simulation = simulation;
        this.scheduledInstant = new PriorityQueue<>(0, scheduledComparator);
        time = 0;
    }
    /**
     * Get the timer instance for a given simulation
     *
     * @param simulation the simulation that is associated with the timer
     * @return the timer associated with the given simulation
     */
    public static Timer getInstance(Simulation simulation) {
        if (timers.get(simulation) == null) {
            synchronized (Timer.class) {
                if (timers.get(simulation) == null) {
                    timers.put(simulation, new Timer(simulation));
                }
            }
        }
        return timers.get(simulation);
    }
    /**
     * Get the current time of this timer
     *
     * @return the current time
     */
    public double getTime() {
        return time;
    }
    /**
     * Gets the simulation associated with this timer
     *
     * @return the simulation associated with this timer
     */
    public Simulation getSimulation() {
        return simulation;
    }
    /**
     * <p>
     * Schedule this timer to notify a given handler after some time has passed.
     * It's similar to the 'snooze' option from alarm clocks.
     * <p>
     * <b>NOTE:</b> the given time is how much time <i>later</i> the handler will
     * be notified. E.g. if application calls {@code snooze(handler,0)}, the handler
     * would be notified right away
     *
     * @param handler the handler to be notified later
     * @param time the time to snooze
     */
    public void snooze(Handler<Timer> handler, double time) {
        scheduledInstant.add(new Pair<>(this.time + time, handler));
    }
    /**
     * Notifies the next scheduled time that time has arrived
     */
    private void handleNext() {
        Pair<Double, Handler<Timer>> pair = scheduledInstant.remove();
        // update current time of the timer
        this.time = pair.getKey();
        pair.getValue().handle(this);
    }
    /**
     * Starts the clock of the simulation, notifying all the initial handlers, and supposing
     */
    public void start() {
        while (!scheduledInstant.isEmpty()) {
            handleNext();
        }
    }
}
