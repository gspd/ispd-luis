package gspd.ispd.schedulers;

import java.util.Map;
import java.util.Queue;
import java.util.Set;

/**
 * Scheduler provides methods necessary to have a correct scheduler
 * that maps the activity A (task, vm, ...) to the resource R (cpu, host, ...)
 */
interface Placement<A, R> {
    /**
     * Adds a resource that activity can be scheduled to
     * @param resource
     */
    void addResource(R resource);
    /**
     * Submit an activity to be scheduled to  a resource
     * @param activity
     */
    void submitActivity(A activity);
    /**
     * @return the set of resources
     */
    Set<R> getResources();
    /**
     * @return the queue of activities
     */
    Queue<A> getActivities();
    /**
     * @return the activities mapped to the resources based on the scheduler
     */
    Map<A, R> getPlacement();
}