package gspd.ispd.commons;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

public class IDSystem {

    private int id;
    private Map<Object, Integer> idMap;

    public IDSystem() {
        id = 0;
        idMap = new HashMap<>();
        this.criticalSem = new Semaphore(1);
    }

    private static IDSystem singleton;
    private static final Semaphore singSem = new Semaphore(1);
    private Semaphore criticalSem;
    public static IDSystem getDefaultInstance() {
        if (singleton == null) {
            try {
                singSem.acquire();
                if (singleton == null) {
                    singleton = new IDSystem();
                }
                singSem.release();
            } catch (InterruptedException e) {
                Logger.getGlobal().log(Level.SEVERE, "Error creating ID System: " + e);
            }
        }
        return singleton;
    }

    public boolean add(Object obj) {
        if (obj != null) {
            try {
                criticalSem.acquire();
                if (idMap.putIfAbsent(obj, id) == null) {
                    id++;
                }
                criticalSem.release();
                return true;
            } catch (InterruptedException e) {
                Logger.getGlobal().log(Level.SEVERE, "Error while adding " + obj + " to ID System: " + e);
            }
        }
        return false;
    }

    public boolean remove(Object obj) {
        try {
            criticalSem.acquire();
            Object o = idMap.remove(obj);
            criticalSem.release();
        } catch (InterruptedException e) {
            Logger.getGlobal().log(Level.SEVERE, "Error while removing "  + obj + " from ID System: " + e);
        }
        return idMap.remove(obj) != null;
    }

    public int getId(Object obj) {
        Integer id = idMap.get(obj);
        if (id == null) {
            return -1;
        }
        return id;
    }

    public int size() {
        return idMap.size();
    }
}
