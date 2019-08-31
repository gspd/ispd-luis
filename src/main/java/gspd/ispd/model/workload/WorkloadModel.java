package gspd.ispd.model.workload;

public abstract class WorkloadModel {
    private WorkloadModel parent;

    public WorkloadModel(WorkloadModel parent) {
        this.parent = parent;
    }

    public WorkloadModel() {
        this(null);
    }

    public abstract Task getNextTask();
    public abstract void rewind();
}
