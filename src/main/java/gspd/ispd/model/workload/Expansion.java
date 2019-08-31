package gspd.ispd.model.workload;

public interface Expansion {
    public void wrapModel(WorkloadModel model);
    public WorkloadModel expandModel();
}
