package gspd.ispd.model.workload;

public class IterativeExpansion implements Expansion {

    private WorkloadModel wrappedModel;

    public IterativeExpansion(WorkloadModel wrappedModel) {
        this.wrappedModel = wrappedModel;
    }

    public IterativeExpansion() {
        this(null);
    }

    public int getNumberOfIterations() {
        return 0;
    }

    @Override
    public void wrapModel(WorkloadModel model) {
        this.wrappedModel = model;
    }

    @Override
    public WorkloadModel expandModel() {
        return new WorkloadModel() {
            private final int iterations = getNumberOfIterations();
            private int i = 1;

            @Override
            public void rewind() {
                wrappedModel.rewind();
                i = 1;
            }

            @Override
            public Task getNextTask() {
                Task task = wrappedModel.getNextTask();
                if (task ==  null && i < iterations) {
                    i++;
                    wrappedModel.rewind();
                    task = wrappedModel.getNextTask();
                }
                return task;
            }
        };
    }
}
