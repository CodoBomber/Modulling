package generators.markov;

public interface MarkovChainGenerator {

    Float[][] generate();

    void proceed(int count);

    int getSize();
}
