package gspd.ispd.escalonador;

/**
 * PriorityScheduler
 */
interface PriorityScheduler<A, R, T> extends Scheduler<A, R> {
    T getPriority();
}