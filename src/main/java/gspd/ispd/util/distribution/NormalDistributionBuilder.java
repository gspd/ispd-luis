package gspd.ispd.util.distribution;

import java.util.Random;

public class NormalDistributionBuilder extends DistributionBuilder {

    private DistributionBuilder averageBuilder;
    private DistributionBuilder deviationBuilder;

    public NormalDistributionBuilder(DistributionBuilder averageBuilder, DistributionBuilder deviationBuilder) {
        this.averageBuilder = averageBuilder;
        this.deviationBuilder = deviationBuilder;
    }

    public NormalDistributionBuilder setAverage(DistributionBuilder average) {
        this.averageBuilder = average;
        return this;
    }

    public NormalDistributionBuilder setAverage(double average) {
        return setAverage(constant(average));
    }

    public DistributionBuilder getAverageBuilder() {
        return averageBuilder;
    }

    public NormalDistributionBuilder setDeviation(DistributionBuilder deviation) {
        this.deviationBuilder = deviation;
        return this;
    }

    public NormalDistributionBuilder setDeviation(double deviation) {
        return setDeviation(constant(deviation));
    }

    public DistributionBuilder getDeviationBuilder() {
        return deviationBuilder;
    }

    @Override
    public Distribution build() {
        Distribution average = averageBuilder.build();
        Distribution deviation = deviationBuilder.build();
        Random lcg = new Random();
        return () -> {
            int i;
            double sum = 0.0, a, d;
            a = average.random();
            d = deviation.random();
            for (i = 0; i < 12; i++) {
                sum += lcg.nextDouble();
            }
            return (a + (d * (sum - 6.0)));
        };
    }
}
