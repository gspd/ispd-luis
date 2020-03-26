package gspd.ispd.motor.workload;

import gspd.ispd.fxgui.workload.dag.DAG;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class DAGContainer {

    private List<DAG> dags;
    private DAGContainer() {
        dags = new ArrayList<>();
    }

    private static DAGContainer singleton;
    public static DAGContainer getInstance() {
        if (singleton == null) {
            singleton = new DAGContainer();
        }
        return singleton;
    }

    public boolean add(DAG dag) {
        return dags.add(dag);
    }

    public boolean remove(DAG dag) {
        return dags.remove(dag);
    }

    public DAG get(String dagName) {
        List<DAG> select = dags
                .stream()
                .filter(dag -> dag.getName().equals(dagName))
                .collect(Collectors.toList());
        if (select.size() >= 1) {
            return select.get(0);
        }
        return null;
    }

    public List<DAG> getDags() {
        return Collections.unmodifiableList(dags);
    }
}
