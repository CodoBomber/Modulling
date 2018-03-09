package modeling.generators.distribution;

import java.util.concurrent.ThreadLocalRandom;

public class UniformDistribution extends Distribution {

    private ThreadLocalRandom random = ThreadLocalRandom.current();
    private double leftBound;
    private double rightBound;

    public UniformDistribution(double expectedValue) {
        super(expectedValue);
        generateBounds();
    }

    /**
     * この式で範囲を作り出します。
     * -r + 2 * M = x
     * r - ランダム下の範囲
     * M - 期待値
     * x - 上の範囲
     */
    private void generateBounds() {
        leftBound = Math.random();
        rightBound = -1 * leftBound + 2 * expectedValue;
    }

    @Override
    public double getNextValue() {
        return random.nextDouble(leftBound, rightBound);
    }
}
