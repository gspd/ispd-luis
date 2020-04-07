package gspd.ispd.util.structures;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * SureHashMap is a HashMap that presumes the key will eventually
 * be present, and can block the thread requesting a key until it
 * becomes present in the map
 *
 * @param <K> the key class
 * @param <V> the value class
 */
public class SureHashMap<K, V> extends HashMap<K, V> {

    private final Object lock = new Object();

    /**
     * Retrieves the value V associated with the K. If key is
     * not present yet, block the thread until become present
     *
     * @param key the key
     * @return the value mapped with the key
     */
    @Override
    public V get(Object key) {
        V result = null;
        synchronized (lock) {
            try {
                while (!super.containsKey(key)) {
                    lock.wait();
                }
                result = super.get(key);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    @Override
    public V put(K key, V value) {
        V result;
        synchronized (lock) {
            result = super.put(key, value);
            lock.notifyAll();
        }
        return result;
    }
}
