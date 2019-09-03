package gspd.ispd.model.workload;

public abstract class Expansion {
    private WorkloadModel insideModel;

    public Expansion(WorkloadModel wrappedModel) {
        this.insideModel = wrappedModel;
    }

    public Expansion() {
        this(null);
    }

    public WorkloadModel inside() {
        return insideModel;
    }

    public void wrapModel(WorkloadModel model) {
        insideModel = model;
    }

    public abstract WorkloadModel expandModel();
}
