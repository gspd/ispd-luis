package gspd.ispd.model.workload;

import gspd.ispd.model.data.Job;

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
            public Job getNextTask() {
                try {
                    Job job = inside().getNextTask();
                    if (job == null && i < iterations) {
                        i++;
                        inside().rewind();
                        job = inside().getNextTask();
                    }
                    return job;
                } catch (NullPointerException e) {
                    // TODO: error, NULL POINTER EXCEPTION OCCURRED
                    return null;
                }
            }
        };
    }
}
