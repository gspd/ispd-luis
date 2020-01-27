package gspd.ispd.queues;

import gspd.ispd.queues.disciplines.FIFO;
import gspd.ispd.queues.disciplines.QueueDiscipline;

import java.util.List;

public class EventQueue<C extends Client> implements ClientInput<C>, ClientOutput<C> {

    private ClientOutput<C> input;
    private ClientInput<C> output;

    private QueueDiscipline<C> discipline;
    private List<C> queue;

    public void add(C client) {
        queue.add(client);
        discipline.discipline(client);
    }

    public boolean hasNext() {
        return queue.size() > 0;
    }

    public void setDiscipline(QueueDiscipline<C> discipline) {
        this.discipline = discipline;
        this.discipline.setQueue(queue);
    }

    public void setQueue(List<C> queue) {
        this.queue = queue;
    }

    @Override
    public void receiveClient(C client) {
        add(client);
    }

    @Override
    public void transmitClient(C client) {
        output.receiveClient(client);
    }
}
