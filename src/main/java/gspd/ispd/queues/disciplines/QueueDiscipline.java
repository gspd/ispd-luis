package gspd.ispd.queues.disciplines;

import java.util.List;
import java.util.NoSuchElementException;

public abstract class QueueDiscipline<E> {

    /**
     * The queue (in form of list) that the queue discipline refers to.
     */
    private List<E> queue;

    public void setQueue(List<E> queue) {
        this.queue = queue;
    }

    protected void setAhead(E element) {
        setPosition(element, 0);
    }

    protected void setAhead(int index) {
        setPosition(index, 0);
    }

    protected void setBehind(E element) {
        int position = queue.size() - 1;
        setPosition(element, position);
    }

    protected void setBehind(int index) {
        int position = queue.size() - 1;
        setPosition(index, position);
    }

    protected void setPosition(E element, int position) {
        int index = queue.indexOf(element);
        if (index != -1) {
            setPosition(index, position);
        } else {
            throw new NoSuchElementException();
        }
    }

    protected void setPosition(int index, int position) {
        E element = queue.remove(index);
        queue.add(position, element);
    }

    /**
     * Specifies the discipline a just arrived element needs to follow.
     * Consider using {@link QueueDiscipline#setAhead(Object)},
     * {@link QueueDiscipline#setBehind(Object)}, and
     * {@link QueueDiscipline#setPosition(Object, int)} in its implementation
     * 
     * @param element the element that just arrived in queue
     */
    public abstract void discipline(E element);
}
