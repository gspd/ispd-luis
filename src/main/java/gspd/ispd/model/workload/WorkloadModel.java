package gspd.ispd.model.workload;

import gspd.ispd.model.Job;

import java.util.List;

public abstract class WorkloadModel {

    public List<RecursivePoint> getRecursivePoints() { return null; }

    public abstract Job getNextTask();
    public abstract void rewind();
}
