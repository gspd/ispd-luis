package gspd.ispd.util.workload;

import gspd.ispd.fxgui.workload.dag.DAG;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DAGContainer {

    private Map<String, DAG> dags;
    private DAGContainer() {
        dags = new ConcurrentHashMap<>();
    }

    private static DAGContainer singleton = new DAGContainer();
    private static final Semaphore semaphore = new Semaphore(1);
    public static DAGContainer getInstance() {
        if (singleton == null) {
            try {
                semaphore.acquire();
                if (singleton == null) {
                    singleton = new DAGContainer();
                }
                semaphore.release();
            } catch (InterruptedException e) {
                Logger.getGlobal().log(Level.SEVERE, "DAG Container was interrupted during creation: " + e);
            }
        }
        return singleton;
    }

    public void put(DAG dag) {
        dags.put(dag.getName(), dag);
    }

    public DAG get(String name) {
        return dags.get(name);
    }

    public void clear() {
        dags.clear();
    }

    public void putAll(Collection<DAG> dags) {
        dags.forEach(this::put);
    }

    public void putAll(DAG... dags) {
        putAll(List.of(dags));
    }

    public void setAll(Collection<DAG> dags) {
        clear();
        putAll(dags);
    }

    public void setAll(DAG... dags) {
        setAll(List.of(dags));
    }

    public Collection<DAG> getDags() {
        return dags.values();
    }
}
