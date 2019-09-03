package gspd.ispd.model.workload;

import gspd.ispd.model.Task;

public class ParallelExpansion extends Expansion {

    private int getNumberOfParallel() {
        return 0;
    }

    @Override
    public WorkloadModel expandModel() {
        return new WorkloadModel() {

            private final int parallels = getNumberOfParallel();
            private int j = 0;
            private Task task;

            @Override
            public Task getNextTask() {
                try {
                    if (j == 0) {
                        task = inside().getNextTask();
                    } else {
                        j = (j + 1) % parallels;
                    }
                    return task;
                } catch (NullPointerException e) {

                    return null;
                }
            }

            @Override
            public void rewind() {
                inside().rewind();
                j = 0;
            }
        };
    }
}
