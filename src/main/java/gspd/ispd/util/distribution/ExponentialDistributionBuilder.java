package gspd.ispd.util.distribution;

public class ExponentialDistributionBuilder extends AbstractDistributionBuilder {
    private AbstractDistributionBuilder lambdaBuilder;

    public ExponentialDistributionBuilder(AbstractDistributionBuilder lambdaBuilder) {
        this.lambdaBuilder = lambdaBuilder;
    }

    public ExponentialDistributionBuilder setLambda(AbstractDistributionBuilder lambda) {
        this.lambdaBuilder = lambda;
        return this;
    }

    public ExponentialDistributionBuilder setLambda(double lambda) {
        return setLambda(DistributionBuilder.constant(lambda));
    }

    public AbstractDistributionBuilder getLambdaBuilder() {
        return lambdaBuilder;
    }

    @Override
    public Distribution build() {
        Distribution lambda = lambdaBuilder.build();
        return new Distribution() {
            @Override
            public double random() {
                double l, r;
                r = nextDouble();
                l = lambda.random();
                return - l * Math.log(r);
            }
        };
    }
}
