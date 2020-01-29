package gspd.ispd.queues.disciplines;

public class SimplePriorityDiscipline<P extends Priority> extends PriorityDiscipline<P> {

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
    @Override
    protected int calculatePriority(P element) {
        return element.getPriority();
    }
}
