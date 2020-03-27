package gspd.ispd.commons;

import java.util.HashMap;
import java.util.Map;

public class IDSystem {

    private int id;
    private Map<Object, Integer> idMap;

    private IDSystem() {
        id = 0;
        idMap = new HashMap<>();
    }

    private static IDSystem singleton;
    public static IDSystem getInstance() {
        if (singleton == null) {
            synchronized (IDSystem.class) {
                if (singleton == null) {
                    singleton = new IDSystem();
                }
            }
        }
        return singleton;
    }

    public boolean add(Object obj) {
        if (obj != null) {
            synchronized (IDSystem.class) {
                if (idMap.putIfAbsent(obj, id) == null) {
                    id++;
                    return true;
                }
            }
        }
        return false;
    }

    public boolean remove(Object obj) {
        return idMap.remove(obj) != null;
    }

    public int getId(Object obj) {
        Integer id = idMap.get(obj);
        if (id == null) {
            return -1;
        }
        return id;
    }
}
