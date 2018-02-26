package generators.markov;

public class TwoStochasticGenerator implements MarkovChainGenerator {

    @Override
    public Float[][] generate() {
        return new Float[1][1];
    }

    @Override
    public void proceed(int count) {

    }

    @Override
    public int getSize() {
        return 0;
    }

}
