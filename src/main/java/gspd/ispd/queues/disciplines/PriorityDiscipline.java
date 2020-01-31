package gspd.ispd.queues.disciplines;

import com.jcabi.aspects.Cacheable;
import gspd.ispd.commons.DataCache;

public abstract class PriorityDiscipline<E> extends QueueDiscipline<E> {

    /**
     * Cache some priority values associated with the elements
     */
    private DataCache<E, Integer> priorityCache;
    private static int MAX_CACHE_SIZE = 16;

    public PriorityDiscipline() {
        priorityCache = new DataCache<>(MAX_CACHE_SIZE);
    }

    @Override
    public final void discipline(E element) {
        int priority = this.getPriority(element);
        int i = 0;
        while (getPriority(getElement(i)) > priority) {
            i++;
        }
        setPosition(element, i-1);
    }

    /**
     * Gets the priority if a given element.
     *
     * @param element the element to get the priority
     * @return the calculated priority
     */
    @Cacheable
    public final int getPriority(E element) {
        int priority;
        if (priorityCache.hit(element)) {
            priority = priorityCache.get(element);
        } else {
            priority = calculatePriority(element);
            priorityCache.put(element, priority);
        }
        return priority;
    }

    /**
     * Specifies the algorithm, in subclasses, that is used to calculate
     * the priority of the element. The higher the priority, the closer
     * to the beginning of the queue the element is.
     * <p>
     * Note that its not used to retrieve the priority itself, to do so,
     * use {@link PriorityDiscipline#getPriority(Object)} instead.
     *
     * @param element one given element
     * @return the priority of the element
     */
    protected abstract int calculatePriority(E element);

}
