package gspd.ispd.queues.disciplines;

import java.util.List;

public class FIFO<E> extends QueueDiscipline<E> {

    /**
     * Specifies the discipline a just arrived element needs to follow.
     * Consider using {@link QueueDiscipline#setAhead(Object)},
     * {@link QueueDiscipline#setBehind(Object)}, and
     * {@link QueueDiscipline#setPosition(Object, int)} in its implementation
     *
     * @param element the element that just arrived in queue
     */
    @Override
    public void discipline(E element) {
        setBehind(element);
    }
}
