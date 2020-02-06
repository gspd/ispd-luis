package gspd.ispd.util.distribution;

public class LogNormalDistributionBuilder extends LogDistributionBuilder {
    private NormalDistributionBuilder normalBuilder;

    public LogNormalDistributionBuilder(NormalDistributionBuilder normalBuilder) {
        super(normalBuilder);
        this.normalBuilder = normalBuilder;
    }

    public LogNormalDistributionBuilder setAverage(DistributionBuilder average) {
        normalBuilder.setAverage(average);
        return this;
    }

    public LogNormalDistributionBuilder setAverage(double average) {
        normalBuilder.setAverage(average);
        return this;
    }

    public LogNormalDistributionBuilder setDeviation(DistributionBuilder deviation) {
        normalBuilder.setDeviation(deviation);
        return this;
    }

    public LogNormalDistributionBuilder setDeviation(double deviation) {
        normalBuilder.setDeviation(deviation);
        return this;
    }

    public DistributionBuilder getAverageBuilder() {
        return normalBuilder.getAverageBuilder();
    }

    public DistributionBuilder getDeviationBuilder() {
        return normalBuilder.getDeviationBuilder();
    }
}
