package gspd.ispd.util.distribution;

public class DistributionBuilder {

    public static NormalDistributionBuilder normal() {
        return normal(0, 1);
    }
    public static NormalDistributionBuilder normal(double average) {
        return normal(average, 0);
    }
    public static NormalDistributionBuilder normal(double average, double deviation) {
        return new NormalDistributionBuilder(constant(average), constant(deviation));
    }

    public static ExponentialDistributionBuilder exponential() {
        return exponential(1);
    }
    public static ExponentialDistributionBuilder exponential(double lambda) {
        return new ExponentialDistributionBuilder(constant(lambda));
    }

    public static UniformDistributionBuilder uniform() {
        return uniform(0, 1);
    }
    public static UniformDistributionBuilder uniform(double max) {
        return uniform(0, max);
    }
    public static UniformDistributionBuilder uniform(double min, double max) {
        return new UniformDistributionBuilder(constant(min), constant(max));
    }

    public static PoissonDistributionBuilder poisson() {
        return poisson(1);
    }
    public static PoissonDistributionBuilder poisson(double lambda) {
        return new PoissonDistributionBuilder(constant(lambda));
    }

    public static WeibullDistributionBuilder weibull() {
        return weibull(0,1);
    }
    public static WeibullDistributionBuilder weibull(double shape) {
        return weibull(0, shape);
    }
    public static WeibullDistributionBuilder weibull(double scale, double shape) {
        return new WeibullDistributionBuilder(constant(scale), constant(shape));
    }

    public static ConstantDistributionBuilder constant(double value) {
        return new ConstantDistributionBuilder(value);
    }
}
