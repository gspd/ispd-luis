package gspd.ispd.commons.distribution;

public class WeibullDistributionBuilder extends AbstractDistributionBuilder {

    private AbstractDistributionBuilder scaleBuilder;
    private AbstractDistributionBuilder shapeBuilder;

    public WeibullDistributionBuilder(AbstractDistributionBuilder scaleBuilder, AbstractDistributionBuilder shapeBuilder) {
        this.scaleBuilder = scaleBuilder;
        this.shapeBuilder = shapeBuilder;
    }

    public WeibullDistributionBuilder setScale(AbstractDistributionBuilder scale) {
        this.scaleBuilder = scale;
        return this;
    }

    public WeibullDistributionBuilder setScale(double scale) {
        return setScale(DistributionBuilder.constant(scale));
    }

    public AbstractDistributionBuilder getScaleBuilder() {
        return scaleBuilder;
    }

    public  WeibullDistributionBuilder setShape(AbstractDistributionBuilder shape) {
        this.shapeBuilder = shape;
        return this;
    }

    public WeibullDistributionBuilder setShape(double shape) {
        return setShape(DistributionBuilder.constant(shape));
    }

    public AbstractDistributionBuilder getShapeBuilder() {
        return shapeBuilder;
    }

    @Override
    public Distribution build() {
        Distribution scale = scaleBuilder.build();
        Distribution shape = shapeBuilder.build();
        return new Distribution() {
            @Override
            public double random() {
                double r, sc, sh;
                r = nextDouble();
                sc = scale.random();
                sh = shape.random();
                return sc * Math.pow(-Math.log(1 - r), 1 / sh);
            }
        };
    }
}
