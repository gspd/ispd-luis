package gspd.ispd.model.workload;

import gspd.ispd.model.Job;

public class ParallelExpansion extends Expansion {

    private int getNumberOfParallel() {
        return 0;
    }

    @Override
    public WorkloadModel expandModel() {
        return new WorkloadModel() {

            private final int parallels = getNumberOfParallel();
            private int j = 0;
            private Job job;

            @Override
            public Job getNextTask() {
                try {
                    if (j == 0) {
                        job = inside().getNextTask();
                    } else {
                        j = (j + 1) % parallels;
                    }
                    return job;
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
