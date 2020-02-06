package gspd.ispd.util.distribution;

public class LogDistributionBuilder extends DistributionBuilder {
    private DistributionBuilder distributionBuilder;

    public LogDistributionBuilder(DistributionBuilder distributionBuilder) {
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
        return () -> Math.exp(distribution.random());
    }
}
