package gspd.ispd.schedulers;

import java.util.Map;
import java.util.Queue;
import java.util.Set;

/**
 * Allocator provides methods necessary to have to allocate
 * an activity A (task, vm, ...) to a resource R (cpu, host, ...)
 * managing a Map<A,R>
 */
interface Allocator<A, R> {
    /**
     * Adds a resource that activity can be allocated to
     * @param resource
     */
    void addResource(R resource);
    /**
     * Submit an activity to be allocate to  a resource
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
     * @return the activities mapped to the resources based on the Allocator
     */
    Map<A, R> getAllocation();
}