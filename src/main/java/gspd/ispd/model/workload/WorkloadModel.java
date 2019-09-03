package gspd.ispd.model.workload;

import gspd.ispd.model.Task;

import java.util.List;

public abstract class WorkloadModel {

    public List<RecursivePoint> getRecursivePoints() { return null; }

    public abstract Task getNextTask();
    public abstract void rewind();
}
