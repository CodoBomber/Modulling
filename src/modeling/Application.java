package modeling;

import modeling.generators.distribution.Distribution;
import modeling.generators.distribution.ExponentialDistribution;

public class Application {

    public static void main(String[] args) {
        int arrivalExpectedValue = 10;
        int queuingExpectedValue = 1;

        Distribution arrivalDistribution = new ExponentialDistribution(arrivalExpectedValue);
        Distribution queuingDistribution = new ExponentialDistribution(queuingExpectedValue);




    }

}
