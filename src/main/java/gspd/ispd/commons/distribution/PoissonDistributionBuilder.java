package gspd.ispd.commons.distribution;

public class PoissonDistributionBuilder extends AbstractDistributionBuilder {
    private AbstractDistributionBuilder lambdaBuilder;

    public PoissonDistributionBuilder(AbstractDistributionBuilder lambdaBuilder) {
        this.lambdaBuilder = lambdaBuilder;
    }

    public PoissonDistributionBuilder setLambda(AbstractDistributionBuilder lambda) {
        this.lambdaBuilder = lambda;
        return this;
    }

    public PoissonDistributionBuilder setLambda(double lambda) {
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
                double el, p, k, l;
                l = lambda.random();
                el = Math.exp(-l);
                p = 1;
                k = 0;
                do {
                    p *= nextDouble();
                    k++;
                } while (p > el);
                return k-1;
            }
        };
    }
}
