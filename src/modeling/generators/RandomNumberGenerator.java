package modeling.generators;

import java.util.concurrent.ThreadLocalRandom;

public class RandomNumberGenerator {

    private final ThreadLocalRandom random = ThreadLocalRandom.current();

    public RandomNumberGenerator() {
        generate();
    }

    private void generate() {
        double ksi, x, y, f;
        for (int i = 0; i < 10000; i++) {
            ksi = Math.random();
            if (ksi <= 4d/7d) {
                do {
                    x = Math.random();
                    y = random.nextDouble(0, 12 / 7);
                    f = 12 / 7 * Math.pow(x - 1, 2);
                    if (y < f) {
                        System.out.println(x);
                    }
                } while (y > f);
            } else  {
                System.out.println(4d * ksi - (11d / 7d));
            }
        }
    }

}
