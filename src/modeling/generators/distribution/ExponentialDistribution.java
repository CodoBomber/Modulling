package modeling.generators.distribution;

/**
 * 指数関数的タイプ
 */
public class ExponentialDistribution extends Distribution {

    public ExponentialDistribution(double expectedValue) {
        super(expectedValue);
    }

    @Override
    public double getNextValue() {
        return -1 * expectedValue * Math.log(Math.random());
    }

    @Override
    public String getDistributionName() {
        return getClass().getSimpleName();
    }
}
