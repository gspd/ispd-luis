package gspd.ispd.commons.distribution;

import java.util.Random;

public class ExponentialDistributionBuilder extends DistributionBuilder {
    private DistributionBuilder lambdaBuilder;

    public ExponentialDistributionBuilder(DistributionBuilder lambdaBuilder) {
        this.lambdaBuilder = lambdaBuilder;
    }

    public ExponentialDistributionBuilder setLambda(DistributionBuilder lambda) {
        this.lambdaBuilder = lambda;
        return this;
    }

    public ExponentialDistributionBuilder setLambda(double lambda) {
        return setLambda(constant(lambda));
    }

    public DistributionBuilder getLambdaBuilder() {
        return lambdaBuilder;
    }

    @Override
    public Distribution build() {
        Distribution lambda = lambdaBuilder.build();
        Random lcg = new Random();
        // overrides random()
        return () -> {
            double l, r;
            r = lcg.nextDouble();
            l = lambda.random();
            return - l * Math.log(r);
        };
    }
}
