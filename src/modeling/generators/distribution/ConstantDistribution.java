package modeling.generators.distribution;


public class ConstantDistribution extends Distribution {

    public ConstantDistribution(double expectedValue) {
        super(expectedValue);
    }

    @Override
    public double getNextValue() {
        return expectedValue;
    }

    @Override
    public String getDistributionName() {
        return this.getClass().getSimpleName();
    }
}
