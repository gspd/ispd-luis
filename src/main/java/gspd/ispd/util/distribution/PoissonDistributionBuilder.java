package gspd.ispd.util.distribution;

import java.util.Random;

public class PoissonDistributionBuilder extends DistributionBuilder {
    private DistributionBuilder lambdaBuilder;

    public PoissonDistributionBuilder(DistributionBuilder lambdaBuilder) {
        this.lambdaBuilder = lambdaBuilder;
    }

    public PoissonDistributionBuilder setLambda(DistributionBuilder lambda) {
        this.lambdaBuilder = lambda;
        return this;
    }

    public PoissonDistributionBuilder setLambda(double lambda) {
        return setLambda(constant(lambda));
    }

    public DistributionBuilder getLambdaBuilder() {
        return lambdaBuilder;
    }

    @Override
    public Distribution build() {
        Distribution lambda = lambdaBuilder.build();
        Random lcg = new Random();
        return () -> {
            double el, p, k, l;
            l = lambda.random();
            el = Math.exp(-l);
            p = 1;
            k = 0;
            do {
                p *= lcg.nextDouble();
                k++;
            } while (p > el);
            return k-1;
        };
    }
}
