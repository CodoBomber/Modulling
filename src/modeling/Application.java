package modeling;

import modeling.cluster.ClusterQueuingSystem;
import modeling.generators.distribution.Distribution;
import modeling.generators.distribution.ExponentialDistribution;

public class Application {

    public static void main(String[] args) {
        int arrivalExpectedValue = 10;
        int executionExpectedValue = 1;

        Distribution arrivalDistribution = new ExponentialDistribution(arrivalExpectedValue);
        Distribution executionDistribution = new ExponentialDistribution(executionExpectedValue);

        //2 < maxPassTime < 20
        new ClusterQueuingSystem(
                512,
                256,
                arrivalDistribution,
                executionDistribution,
                10d,
                3
        ).simulate();
    }

}
