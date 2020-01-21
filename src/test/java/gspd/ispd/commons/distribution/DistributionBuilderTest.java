package gspd.ispd.commons.distribution;

import org.junit.Test;

public class DistributionBuilderTest {

    private Distribution distribution;

    @Test
    public void testDistributions() {
        System.out.println("Testing distributions");
        distribution = DistributionBuilder
            .weibull()
            .setScale(2.0)
            .setShape(
                DistributionBuilder
                .normal()
                .setAverage(2.0)
                .setDeviation(
                    DistributionBuilder
                    .exponential()
                    .setLambda(0.9)
                )
            ).build();
    }
}
