package gspd.ispd.commons.distribution;

public abstract class AbstractLogDistributionBuilder extends AbstractDistributionBuilder {
    private AbstractDistributionBuilder distributionBuilder;

    public AbstractLogDistributionBuilder(AbstractDistributionBuilder distributionBuilder) {
        this.distributionBuilder = distributionBuilder;
    }

    public AbstractDistributionBuilder getDistribution() {
        return distributionBuilder;
    }

    public void setDistribution(AbstractDistributionBuilder distributionBuilder) {
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
