package modeling.generators.distribution;

public abstract class Distribution {

    /**
     * Мат. Ожидание
     */
    protected final double expectedValue;

    public Distribution(double expectedValue) {
        this.expectedValue = expectedValue;
    }

    public abstract double getNextValue();

}
