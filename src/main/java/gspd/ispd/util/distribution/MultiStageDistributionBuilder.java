package gspd.ispd.util.distribution;

// TODO: review this class
public class MultiStageDistributionBuilder extends DistributionBuilder {

    private DistributionBuilder dealerDistribution;
    private DistributionBuilder[] stagesDistributions;
    private double[] stagesLimits;

    public MultiStageDistributionBuilder(DistributionBuilder dealer, DistributionBuilder[] stagesDistributions, double[] stagesLimits) {
        this.stagesDistributions = stagesDistributions;
        this.stagesLimits = stagesLimits;
        this.dealerDistribution = dealer;
    }

    public void setLimits(double... limits) {
        this.stagesLimits = limits;
    }

    public void setStages(DistributionBuilder... stages) {
        try {
            this.stagesDistributions = stages;
            stagesLimits = new double[stages.length - 1];
        } catch (Exception e) {
            System.out.println("Use 2 or more distributions");
            e.printStackTrace();
        }
    }

    public void setDealer(DistributionBuilder dealer) {
        this.dealerDistribution = dealer;
    }

    public void setStageLimit(int index, double value) {
        try {
            stagesLimits[index] = value;
        } catch (IndexOutOfBoundsException e){
            System.out.println("It's only possible to set limits from 0 to " + stagesLimits.length + ", but " + index + " was given.");
            e.printStackTrace();
        }
    }

    public void setStage(int index, DistributionBuilder distribution) {
        try {
            stagesDistributions[index] = distribution;
        } catch (IndexOutOfBoundsException e) {
            System.out.println("The stages goes from stage 0 to stage " + stagesDistributions.length + ". But tried to set stage " + index + ".");
            e.printStackTrace();
        }
    }

    public DistributionBuilder[] getStages() {
        return stagesDistributions;
    }

    public double[] getStagesLimits() {
        return stagesLimits;
    }

    public DistributionBuilder getDealer() {
        return dealerDistribution;
    }

    @Override
    public Distribution build() {
        int i;
        Distribution[] distributions = new Distribution[stagesDistributions.length];
        Distribution dealer = dealerDistribution.build();
        // try parallel attribution. If an error occur, do the sequential attribution
        try {
            Thread[] threads = new Thread[stagesDistributions.length];
            for (i = 0; i < stagesDistributions.length; i++) {
                int k = i; // just because java forces a final variable to put as argument in thread method
                threads[i] = new Thread(() -> generateStageDistribution(distributions, k));
                threads[i].start();
            }
            for (i = 0; i < stagesDistributions.length; i++) {
                threads[i].join();
            }
        } catch (Exception e) {
            System.out.println("Parallel attribution did not go well. Doing in the old way");
            for (i = 0; i < stagesDistributions.length; i++) {
                distributions[i] = stagesDistributions[i].build();
            }
        }
        return () -> {
            try {
                double p;
                int j;
                p = dealer.random();
                j = 0;
                while (j < stagesLimits.length && stagesLimits[j] > p) {
                    j++;
                }
                return distributions[j].random();
            } catch (Exception e) {
                System.out.println("An error occurred probably in the distribution builder specification. Are you sure the stages are specified correctly?");
                e.printStackTrace();
                return -1.0;
            }
        };
    }

    private void generateStageDistribution(Distribution[] distributions, int index) {
        distributions[index] = stagesDistributions[index].build();
    }
}
