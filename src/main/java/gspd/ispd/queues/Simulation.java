package gspd.ispd.queues;

import gspd.ispd.queues.events.FutureEvent;
import gspd.ispd.queues.timing.Timer;

import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public abstract class Simulation {
    /**
     * The timer of this simulation
     */
    private Timer timer;
    /**
     * Simulation index. When multiple simulations needs to be run for the same model, each one have
     * a different index.
     */
    private int index;
    /**
     * The results processed in simulation
     */
    private SimulationResults results;
    /**
     * The queue of futures events
     */
    private PriorityQueue<FutureEvent> events;
    /**
     * The comparator of the futures event in order to maintain the priority queue sorted by the time of the event
     */
    private final static Comparator<FutureEvent> futureEventComparator = Comparator.comparingDouble(FutureEvent::getTime);
    /**
     * Flag to denote if simulation have finished
     */
    private boolean finished;

    /**
     * @param index the unique index of the simulation
     */
    protected Simulation(int index) {
        this.index = index;
        this.events = new PriorityQueue<>(0, futureEventComparator);
        this.timer = Timer.getInstance(this);
        this.results = SimulationResults.getInstance(this);
        this.finished = false;
    }

    public Timer getTimer() {
        return timer;
    }

    public int getIndex() {
        return index;
    }

    public SimulationResults getResults() {
        return results;
    }

    public void setResults(SimulationResults results) {
        this.results = results;
    }

    public boolean isFinished() {
        return finished;
    }

    protected void setFinished(boolean value) {
        this.finished = value;
    }

    public void submitEvent(FutureEvent event) {
        events.add(event);
    }

    public abstract void simulate();
}
