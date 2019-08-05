package gspd.ispd.schedulers;

/**
 * PriorityScheduler
 */
interface PriorityScheduler<A, R, P> extends Scheduler<A, R> {
    P getPriority(A activity);
}