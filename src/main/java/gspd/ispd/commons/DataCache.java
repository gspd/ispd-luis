package gspd.ispd.commons;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataCache<K, V> {

    private int maximumSize;
    private List<Object> usages;
    private Map<K, V> map;

    public DataCache(int maximumSize) {
        this.maximumSize = maximumSize;
        this.usages = new ArrayList<>();
        this.map = new HashMap<>();
    }

    public void clear() {
        map.clear();
        usages.clear();
    }

    public int size() {
        return map.size();
    }

    public boolean hit(K key) {
        return map.containsKey(key);
    }

    public void setMaximumSize(int maximumSize) {
        this.maximumSize = maximumSize;
        while (size() > maximumSize) {
            removeOneKey();
        }
    }

    public int getMaximumSize() {
        return maximumSize;
    }

    public V put(K key, V value) {
        if (map.size() >= maximumSize) {
            removeOneKey();
        }
        usages.add(key);
        return map.put(key, value);
    }

    public V get(Object key) {
        V value = map.get(key);
        if (value != null) {
            usages.remove(key);
            usages.add(key);
        }
        return value;
    }

    private void removeOneKey() {
        Object key = usages.remove(0);
        map.remove(key);
    }
}
