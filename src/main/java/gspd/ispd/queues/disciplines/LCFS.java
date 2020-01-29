package gspd.ispd.queues.disciplines;

public class LCFS<E> extends QueueDiscipline<E> {
    /**
     * Specifies the discipline a just arrived element needs to follow.
     * Consider using {@link QueueDiscipline#putAhead(Object)},
     * {@link QueueDiscipline#putBehind(Object)}, and
     * {@link QueueDiscipline#setPosition(Object, int)} in its implementation
     *
     * @param element the element that just arrived in queue
     */
    @Override
    public void discipline(E element) {
        putAhead(element);
    }
}
