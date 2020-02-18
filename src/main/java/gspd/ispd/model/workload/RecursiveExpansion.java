package gspd.ispd.model.workload;

import gspd.ispd.model.data.Job;

public class RecursiveExpansion extends Expansion {

    private int getRecursiveDepth() {
        return 0;
    }

    @Override
    public WorkloadModel expandModel() {
        return new WorkloadModel() {
            @Override
            public Job getNextTask() {
                return null;
            }

            @Override
            public void rewind() {

            }
        };
    }
}
