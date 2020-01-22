package gspd.ispd.commons.distribution;

public abstract class DistributionBuilder {

    public abstract Distribution build();
    // Normal distributions
    public static NormalDistributionBuilder normal(DistributionBuilder average, DistributionBuilder deviation) {
        return new NormalDistributionBuilder(average, deviation);
    }
    public static NormalDistributionBuilder normal(double average, double deviation) {
        return normal(constant(average), constant(deviation));
    }
    public static NormalDistributionBuilder normal() {
        return normal(0, 1);
    }
    public static NormalDistributionBuilder normal(DistributionBuilder average) {
        return normal(average, constant(0));
    }
    public static NormalDistributionBuilder normal(double average) {
        return normal(average, 0);
    }
    // Gaussian distributions (identical to Normal)
    public static NormalDistributionBuilder gaussian() {
        return normal();
    }
    public static NormalDistributionBuilder gaussian(DistributionBuilder average) {
        return normal(average);
    }
    public static NormalDistributionBuilder gaussian(double average) {
        return normal(average);
    }
    public static NormalDistributionBuilder gaussian(DistributionBuilder average, DistributionBuilder deviation) {
        return normal(average, deviation);
    }
    public static NormalDistributionBuilder gaussian(double average, double deviation) {
        return normal(average, deviation);
    }
    // Log distribution -- it is NOT log normal distribution. To make it, use 'log(normal(avg,dvt))'
    public static LogDistributionBuilder log(DistributionBuilder distributionBuilder) {
        return new LogDistributionBuilder(distributionBuilder);
    }
    public static LogDistributionBuilder log(double value) {
        return log(constant(value));
    }
    public static LogDistributionBuilder log() {
        return log(1);
    }
    // Exponential distribution
    public static ExponentialDistributionBuilder exponential(DistributionBuilder lambda) {
        return new ExponentialDistributionBuilder(lambda);
    }
    public static ExponentialDistributionBuilder exponential(double lambda) {
        return exponential(constant(lambda));
    }
    public static ExponentialDistributionBuilder exponential() {
        return exponential(1);
    }
    // Uniform distribution builder
    public static UniformDistributionBuilder uniform(DistributionBuilder min, DistributionBuilder max) {
        return new UniformDistributionBuilder(min, max);
    }
    public static UniformDistributionBuilder uniform(double min, double max) {
        return uniform(constant(min), constant(max));
    }
    public static UniformDistributionBuilder uniform() {
        return uniform(0, 1);
    }
    public static UniformDistributionBuilder uniform(DistributionBuilder max) {
        return uniform(constant(0), max);
    }
    public static UniformDistributionBuilder uniform(double max) {
        return uniform(0, max);
    }
    // Poisson distribution
    public static PoissonDistributionBuilder poisson(DistributionBuilder lambda) {
        return new PoissonDistributionBuilder(lambda);
    }
    public static PoissonDistributionBuilder poisson(double lambda) {
        return poisson(constant(lambda));
    }
    public static PoissonDistributionBuilder poisson() {
        return poisson(1);
    }
    // Weibull distribution
    public static WeibullDistributionBuilder weibull(DistributionBuilder scale, DistributionBuilder shape) {
        return new WeibullDistributionBuilder(scale, shape);
    }
    public static WeibullDistributionBuilder weibull(double scale, double shape) {
        return weibull(constant(scale), constant(shape));
    }
    public static WeibullDistributionBuilder weibull() {
        return weibull(0,1);
    }
    public static WeibullDistributionBuilder weibull(DistributionBuilder shape) {
        return weibull(constant(0), shape);
    }
    public static WeibullDistributionBuilder weibull(double shape) {
        return weibull(0, shape);
    }
    // Constant distribution builder. These are the "leafs" of a complex distribution builder hierarchy
    public static ConstantDistributionBuilder constant(double value) {
        return new ConstantDistributionBuilder(value);
    }
}
