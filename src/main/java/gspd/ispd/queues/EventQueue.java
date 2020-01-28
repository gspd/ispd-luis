package gspd.ispd.queues;

import gspd.ispd.queues.disciplines.FIFO;
import gspd.ispd.queues.disciplines.QueueDiscipline;

import java.util.Iterator;
import java.util.List;

public class EventQueue<C extends Client> implements Iterable<C> {

    private QueueDiscipline<C> discipline;
    private List<C> queue;

    public void add(C client) {
        queue.add(client);
        discipline.discipline(client);
    }

    public void setDiscipline(QueueDiscipline<C> discipline) {
        this.discipline = discipline;
        this.discipline.setQueue(queue);
    }

    public void setQueue(List<C> queue) {
        this.queue = queue;
    }

    @Override
    public Iterator<C> iterator() {
        return new EventQueueIterator();
    }

    class EventQueueIterator implements Iterator<C> {

        @Override
        public boolean hasNext() {
            return queue.iterator().hasNext();
        }

        @Override
        public C next() {
            return queue.iterator().next();
        }
    }
}
