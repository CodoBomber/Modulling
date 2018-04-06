package modeling;

import modeling.cluster.ClusterQueuingSystem;
import modeling.generators.distribution.Distribution;
import modeling.generators.distribution.ExponentialDistribution;

public class Application {

    public static void main(String[] args) {
        double arrivalExpectedValue = 1;
        double executionExpectedValue = 0.1;

        Distribution arrivalDistribution = new ExponentialDistribution(arrivalExpectedValue);
        Distribution executionDistribution = new ExponentialDistribution(executionExpectedValue);

        //2 < maxPassTime < 20
        new ClusterQueuingSystem(
                64,
                32,
                arrivalDistribution,
                executionDistribution,
                0d,
                100
        ).simulate();
    }
}
