package modeling.generators.markov;

import modeling.generators.PVSGenerator;

import java.util.concurrent.ThreadLocalRandom;

public class RowBasedGenerator implements MarkovChainGenerator {

    private final int size;
    private final PVSGenerator[] markovChain;
    private final ThreadLocalRandom random = ThreadLocalRandom.current();
    private float[] stateProbability;

    public RowBasedGenerator(int size) {
        System.out.println("############ROW#BASED#GENERATOR############");
        this.size = size;
        this.markovChain = new PVSGenerator[size];
        initializeMarkovChain();
        this.stateProbability = new float[size];
    }

    private void initializeMarkovChain() {
        for (int i = 0; i < size; i++) {
            markovChain[i] = new PVSGenerator(size);
        }
    }

    @Override
    public Float[][] generate() {
        Float[][] result = new Float[size][size];
        for (int i = 0; i < size; i++) {
            result[i] = markovChain[i].toArray();
        }
        return result;
    }

    @Override
    public void proceed(int count) {
        PVSGenerator pvs = markovChain[random.nextInt(size)];
        for (int i = 0; i < count; i++) {
            float maxS = pvs.getMaxSValue();
            int index = pvs.findFirstOccurrence((float) random.nextDouble(maxS));
            stateProbability[index]++;
            pvs = markovChain[index];
        }
        for (int i = 0; i < size; i++) {
            stateProbability[i] = stateProbability[i] / count;
            System.out.println("state #"+ i + ": " + stateProbability[i]);
        }
    }

    @Override
    public int getSize() {
        return this.size;
    }
}
