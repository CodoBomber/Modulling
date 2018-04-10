package modeling;

import modeling.cluster.ClusterQueuingSystem;
import modeling.generators.distribution.Distribution;
import modeling.generators.distribution.ExponentialDistribution;

public class Application {

    public static void main(String[] args) {
        double arrivalExpectedValue = 1;
        double executionExpectedValue = 1;

        Distribution arrivalDistribution = new ExponentialDistribution(arrivalExpectedValue);
        Distribution executionDistribution = new ExponentialDistribution(executionExpectedValue);

        //2 < maxPassTime < 20
        for (int i = 64; i <= 512; i += 64) {
            for (int j = 32; j <= 256; j += 32) {
                System.out.print(i + "," + j + '\t');
                new ClusterQueuingSystem(
                        i,
                        j,
                        arrivalDistribution,
                        executionDistribution,
                        10,
                        10_000
                ).simulate();
            }
        }

        /*for (int i = 0; i <= 20; i += 2)
            new ClusterQueuingSystem(
                    512,
                    256,
                    arrivalDistribution,
                    executionDistribution,
                    i,
                    10_000
            ).simulate();*/
    }
}
