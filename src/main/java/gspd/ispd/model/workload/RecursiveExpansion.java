package gspd.ispd.model.workload;

import gspd.ispd.model.Task;

public class RecursiveExpansion extends Expansion {

    private int getRecursiveDepth() {
        return 0;
    }

    @Override
    public WorkloadModel expandModel() {
        return new WorkloadModel() {
            @Override
            public Task getNextTask() {
                return null;
            }

            @Override
            public void rewind() {

            }
        };
    }
}
