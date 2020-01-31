package gspd.ispd.queues.events;

import gspd.ispd.queues.Client;

import java.util.Iterator;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public abstract class ClientGenerator implements Iterable<Client>{

    private BlockingQueue<Client> generatedBuffer;

    public ClientGenerator() {
        this.generatedBuffer = new ArrayBlockingQueue<>(10);
    }

    private void produce() throws InterruptedException {
        Client client = generateClient();
        generatedBuffer.put(client);
    }

    protected abstract Client generateClient();

    @Override
    public Iterator<Client> iterator() {
        return null;
    }
}
