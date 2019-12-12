package gspd.ispd.commons.distribution;

public class UniformDistributionBuilder extends AbstractDistributionBuilder {
    private AbstractDistributionBuilder minBuilder;
    private AbstractDistributionBuilder maxBuilder;

    public UniformDistributionBuilder(AbstractDistributionBuilder minBuilder, AbstractDistributionBuilder maxBuilder) {
        this.minBuilder = minBuilder;
        this.maxBuilder = maxBuilder;
    }

    public UniformDistributionBuilder setMin(AbstractDistributionBuilder min) {
        this.minBuilder = min;
        return this;
    }

    public UniformDistributionBuilder setMin(double min) {
        return setMin(DistributionBuilder.constant(min));
    }

    public AbstractDistributionBuilder getMinBuilder() {
        return minBuilder;
    }

    public UniformDistributionBuilder setMax(AbstractDistributionBuilder max) {
        this.maxBuilder = max;
        return this;
    }

    public UniformDistributionBuilder setMax(double max) {
        return setMax(DistributionBuilder.constant(max));
    }

    public AbstractDistributionBuilder getMaxBuilder() {
        return maxBuilder;
    }

    @Override
    public Distribution build() {
        Distribution min = minBuilder.build();
        Distribution max = maxBuilder.build();
        return new Distribution() {
            @Override
            public double random() {
                double a, b, r, swap;
                r = nextDouble();
                a = min.random();
                b = max.random();
                if (a > b) {
                    swap = a;
                    a = b;
                    b = swap;
                }
                return (b - a) * r  + a;
            }
        };
    }
}
