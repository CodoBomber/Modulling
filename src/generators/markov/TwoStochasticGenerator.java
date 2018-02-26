package generators.markov;

import generators.PVSGenerator;

import java.util.concurrent.ThreadLocalRandom;

public class TwoStochasticGenerator implements MarkovChainGenerator {

    private final int size;
    private float[][] chain;
    private PVSGenerator[] chainPVS;
    private final float[] stateProbability;
    private final ThreadLocalRandom random = ThreadLocalRandom.current();

    public TwoStochasticGenerator(int size) {
        System.out.println("############TWO#STOCHASTIC#GENERATOR############");
        this.size = size;
        this.stateProbability = new float[size];
        buildTwoStochasticChain();
        convertToPVSChain();
    }

    private void buildTwoStochasticChain() {
        chain = new float[size][size];
        //cannot cast to primitive in stream
        Float[] firstRow = new PVSGenerator(size).toArray();
        for (int i = 0; i < size; i++) {
            chain[0][i] = firstRow[i];
        }
        for (int i = 1; i < size - 1; i++) {
            //rows
            for (int j = 0; j < size - 1; j++) {
                //columns
                float probabilityBound = getMaxProbabilityBound(i, j);
                chain[i][j] = (float) random.nextDouble(probabilityBound);
            }
            chain[i][size - 1] = 1f - getRowSum(i, size - 1);
        }
        for (int j = 0; j < size; j++) {
            chain[size - 1][j] = 1f - getColumnSum(size - 1, j);
        }
    }

    private float getColumnSum(int currentRow, int currentColumn) {
        float sum = 0f;
        for (int i = 0; i < currentRow; i++) {
            //cumulative sum
            sum += chain[i][currentColumn];
        }
        return sum;
    }

    private float getMaxProbabilityBound(int currentRow, int currentColumn) {
        float sum = getColumnSum(currentRow, currentColumn);
        float columnBound = 1f - sum;
        float rowBound = 1f - getRowSum(currentRow, currentColumn);
        return rowBound <= columnBound ? rowBound : columnBound;
    }

    private float getRowSum(int currentRow, int currentColumn) {
        float sum = 0f;
        for (int i = 0; i < currentColumn; i++) {
            sum += chain[currentRow][i];
        }
        return sum;
    }

    @Override
    public Float[][] generate() {
        Float[][] result = new Float[size][size];
        for (int i = 0; i < size; i++) {
            result[i] = chainPVS[i].toArray();
        }
        return result;
    }

    private void convertToPVSChain() {
        chainPVS = new PVSGenerator[size];
        for (int i = 0; i < size; i++) {
            this.chainPVS[i] = new PVSGenerator(chain[i]);
        }
    }

    @Override
    public void proceed(int count) {
        PVSGenerator pvs = chainPVS[random.nextInt(size)];
        for (int i = 0; i < count; i++) {
            float maxS = pvs.getMaxSValue();
            int index = pvs.findFirstOccurrence((float) random.nextDouble(maxS));
            stateProbability[index]++;
            pvs = chainPVS[index];
        }
        for (int i = 0; i < size; i++) {
            stateProbability[i] = stateProbability[i] / count;
            System.out.println("state #"+ i + ": " + stateProbability[i]);
        }
    }

    @Override
    public int getSize() {
        return size;
    }

}
