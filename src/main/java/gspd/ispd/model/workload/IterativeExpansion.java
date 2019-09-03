package gspd.ispd.model.workload;

import gspd.ispd.model.Task;

public class IterativeExpansion extends Expansion {

    public int getNumberOfIterations() {
        return 0;
    }

    @Override
    public WorkloadModel expandModel() {
        return new WorkloadModel() {
            private final int iterations = getNumberOfIterations();
            private int i = 1;

            @Override
            public void rewind() {
                inside().rewind();
                i = 1;
            }

            @Override
            public Task getNextTask() {
                try {
                    Task task = inside().getNextTask();
                    if (task == null && i < iterations) {
                        i++;
                        inside().rewind();
                        task = inside().getNextTask();
                    }
                    return task;
                } catch (NullPointerException e) {
                    // TODO: error, NULL POINTER EXCEPTION OCCURRED
                    return null;
                }
            }
        };
    }
}
