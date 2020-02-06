package gspd.ispd.util.distribution;

import java.util.Random;

public class WeibullDistributionBuilder extends DistributionBuilder {

    private DistributionBuilder scaleBuilder;
    private DistributionBuilder shapeBuilder;

    public WeibullDistributionBuilder(DistributionBuilder scaleBuilder, DistributionBuilder shapeBuilder) {
        this.scaleBuilder = scaleBuilder;
        this.shapeBuilder = shapeBuilder;
    }

    public WeibullDistributionBuilder setScale(DistributionBuilder scale) {
        this.scaleBuilder = scale;
        return this;
    }

    public WeibullDistributionBuilder setScale(double scale) {
        return setScale(constant(scale));
    }

    public DistributionBuilder getScaleBuilder() {
        return scaleBuilder;
    }

    public  WeibullDistributionBuilder setShape(DistributionBuilder shape) {
        this.shapeBuilder = shape;
        return this;
    }

    public WeibullDistributionBuilder setShape(double shape) {
        return setShape(constant(shape));
    }

    public DistributionBuilder getShapeBuilder() {
        return shapeBuilder;
    }

    @Override
    public Distribution build() {
        Distribution scale = scaleBuilder.build();
        Distribution shape = shapeBuilder.build();
        Random lcg = new Random();
        return () -> {
            double r, sc, sh;
            r = lcg.nextDouble();
            sc = scale.random();
            sh = shape.random();
            return sc * Math.pow(-Math.log(1 - r), 1 / sh);
        };
    }
}
