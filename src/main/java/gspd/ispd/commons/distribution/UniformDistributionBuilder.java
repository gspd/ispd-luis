package gspd.ispd.commons.distribution;

import java.util.Random;

public class UniformDistributionBuilder extends DistributionBuilder {
    private DistributionBuilder minBuilder;
    private DistributionBuilder maxBuilder;

    public UniformDistributionBuilder(DistributionBuilder minBuilder, DistributionBuilder maxBuilder) {
        this.minBuilder = minBuilder;
        this.maxBuilder = maxBuilder;
    }

    public UniformDistributionBuilder setMin(DistributionBuilder min) {
        this.minBuilder = min;
        return this;
    }

    public UniformDistributionBuilder setMin(double min) {
        return setMin(constant(min));
    }

    public DistributionBuilder getMinBuilder() {
        return minBuilder;
    }

    public UniformDistributionBuilder setMax(DistributionBuilder max) {
        this.maxBuilder = max;
        return this;
    }

    public UniformDistributionBuilder setMax(double max) {
        return setMax(constant(max));
    }

    public DistributionBuilder getMaxBuilder() {
        return maxBuilder;
    }

    @Override
    public Distribution build() {
        Distribution min = minBuilder.build();
        Distribution max = maxBuilder.build();
        Random lcg = new Random();
        return () -> {
            double a, b, r, swap;
            r = lcg.nextDouble();
            a = min.random();
            b = max.random();
            if (a > b) {
                swap = a;
                a = b;
                b = swap;
            }
            return (b - a) * r  + a;
        };
    }
}
