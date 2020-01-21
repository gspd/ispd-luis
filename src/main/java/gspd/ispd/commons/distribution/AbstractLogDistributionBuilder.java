package gspd.ispd.commons.distribution;

public abstract class AbstractLogDistributionBuilder extends DistributionBuilder {
    private DistributionBuilder distributionBuilder;

    public AbstractLogDistributionBuilder(DistributionBuilder distributionBuilder) {
        this.distributionBuilder = distributionBuilder;
    }

    public DistributionBuilder getDistribution() {
        return distributionBuilder;
    }

    public void setDistribution(DistributionBuilder distributionBuilder) {
        this.distributionBuilder = distributionBuilder;
    }

    @Override
    public Distribution build() {
        Distribution distribution = distributionBuilder.build();
        return new Distribution() {
            @Override
            public double random() {
                return Math.exp(distribution.random());
            }
        };
    }
}
