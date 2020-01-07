package gspd.ispd.commons.distribution;

// TODO: review this class
public class MultiStageDistributionBuilder extends AbstractDistributionBuilder {

    private AbstractDistributionBuilder dealerDistribution;
    private AbstractDistributionBuilder[] stagesDistributions;
    private double[] stagesLimits;

    public MultiStageDistributionBuilder(AbstractDistributionBuilder dealer, AbstractDistributionBuilder[] stagesDistributions, double[] stagesLimits) {
        this.stagesDistributions = stagesDistributions;
        this.stagesLimits = stagesLimits;
        this.dealerDistribution = dealer;
    }

    public void setLimits(double... limits) {
        this.stagesLimits = limits;
    }

    public void setStages(AbstractDistributionBuilder... stages) {
        try {
            this.stagesDistributions = stages;
            stagesLimits = new double[stages.length - 1];
        } catch (Exception e) {
            System.out.println("Use 2 or more distributions");
            e.printStackTrace();
        }
    }

    public void setDealer(AbstractDistributionBuilder dealer) {
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

    public void setStage(int index, AbstractDistributionBuilder distribution) {
        try {
            stagesDistributions[index] = distribution;
        } catch (IndexOutOfBoundsException e) {
            System.out.println("The stages goes from stage 0 to stage " + stagesDistributions.length + ". But tried to set stage " + index + ".");
            e.printStackTrace();
        }
    }

    public AbstractDistributionBuilder[] getStages() {
        return stagesDistributions;
    }

    public double[] getStagesLimits() {
        return stagesLimits;
    }

    public AbstractDistributionBuilder getDealer() {
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
        return new Distribution() {
            @Override
            public double random() {
                try {
                    double p;
                    int i;
                    p = dealer.random();
                    i = 0;
                    while (i < stagesLimits.length && stagesLimits[i] > p) {
                        i++;
                    }
                    return distributions[i].random();
                } catch (Exception e) {
                    System.out.println("An error occurred probably in the distribution builder specification. Are you sure the stages are specified correctly?");
                    e.printStackTrace();
                    return -1.0;
                }
            }
        };
    }

    private void generateStageDistribution(Distribution[] distributions, int index) {
        distributions[index] = stagesDistributions[index].build();
    }
}
