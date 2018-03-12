package modeling.generators.distribution;


public class ConstantDistribution extends Distribution {

    private double currentValue = 0;

    public ConstantDistribution(double expectedValue) {
        super(expectedValue);
    }

    @Override
    public double getNextValue() {
        currentValue += expectedValue;
        return currentValue;
    }

    @Override
    public String getDistributionName() {
        return this.getClass().getSimpleName();
    }
}
