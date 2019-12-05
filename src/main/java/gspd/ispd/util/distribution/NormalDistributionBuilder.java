package gspd.ispd.util.distribution;

public class NormalDistributionBuilder extends AbstractDistributionBuilder {

    private AbstractDistributionBuilder averageBuilder;
    private AbstractDistributionBuilder deviationBuilder;

    public NormalDistributionBuilder(AbstractDistributionBuilder averageBuilder, AbstractDistributionBuilder deviationBuilder) {
        this.averageBuilder = averageBuilder;
        this.deviationBuilder = deviationBuilder;
    }

    public NormalDistributionBuilder setAverage(AbstractDistributionBuilder average) {
        this.averageBuilder = average;
        return this;
    }

    public NormalDistributionBuilder setAverage(double average) {
        return setAverage(DistributionBuilder.constant(average));
    }

    public AbstractDistributionBuilder getAverageBuilder() {
        return averageBuilder;
    }

    public NormalDistributionBuilder setDeviation(AbstractDistributionBuilder deviation) {
        this.deviationBuilder = deviation;
        return this;
    }

    public NormalDistributionBuilder setDeviation(double deviation) {
        return setDeviation(DistributionBuilder.constant(deviation));
    }

    public AbstractDistributionBuilder getDeviationBuilder() {
        return deviationBuilder;
    }

    @Override
    public Distribution build() {
        Distribution average = averageBuilder.build();
        Distribution deviation = deviationBuilder.build();
        return new Distribution() {
            @Override
            public double random() {
                int i;
                double sum = 0.0, a, d;
                a = average.random();
                d = deviation.random();
                for (i = 0; i < 12; i++) {
                    sum += nextDouble();
                }
                return (a + (d * (sum - 6.0)));
            }
        };
    }
}
