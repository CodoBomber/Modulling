package events;

import generators.markov.MarkovChainGenerator;

import java.util.Arrays;

public class MarkovChain {

    private final int size;
    private Float[][] chain;
    private Float[][] squaredChain;

    public MarkovChain(MarkovChainGenerator generator) {
        this.size = generator.getSize();
        generator.proceed(10000);
        chain = generator.generate();
        squaredChain = new Float[size][size];
    }

    public void multiply() {
        for (int i = 0; i < 20; i++) {
            square();
            chain = deepClone();
        }
    }

    private Float[][] deepClone() {
        return Arrays.stream(squaredChain)
                .map(Float[]::clone)
                .toArray(w ->squaredChain.clone());
    }

    private void square() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                Float result = 0f;
                for (int k = 0; k < size; k++) {
                    result += chain[i][k] * chain[k][j];
                }
                squaredChain[i][j] = result;
            }
        }
    }

    public void prettyPrint() {
        System.out.println("##########START MATRIX#############");
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                System.out.print(chain[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println("##########END MATRIX#############");
    }

}
