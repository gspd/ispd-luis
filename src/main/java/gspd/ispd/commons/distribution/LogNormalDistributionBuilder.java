package gspd.ispd.commons.distribution;

public class LogNormalDistributionBuilder extends AbstractLogDistributionBuilder {
    private NormalDistributionBuilder normalBuilder;

    public LogNormalDistributionBuilder(NormalDistributionBuilder normalBuilder) {
        super(normalBuilder);
    }

    public LogNormalDistributionBuilder setAverage(AbstractDistributionBuilder average) {
        normalBuilder.setAverage(average);
        return this;
    }

    public LogNormalDistributionBuilder setAverage(double average) {
        normalBuilder.setAverage(average);
        return this;
    }

    public LogNormalDistributionBuilder setDeviation(AbstractDistributionBuilder deviation) {
        normalBuilder.setDeviation(deviation);
        return this;
    }

    public LogNormalDistributionBuilder setDeviation(double deviation) {
        normalBuilder.setDeviation(deviation);
        return this;
    }

    public AbstractDistributionBuilder getAverageBuilder() {
        return normalBuilder.getAverageBuilder();
    }

    public AbstractDistributionBuilder getDeviationBuilder() {
        return normalBuilder.getDeviationBuilder();
    }
}
