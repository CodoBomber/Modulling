package modeling.generators;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PVSGenerator {

    public int size;
    private PVSGenerator backup;

    /*1/10 = 0.1*/
    private List<Float> P;
    private List<Integer> values;
    private List<Float> S;

    private Map<Float, Integer> valuesMap;
    private ThreadLocalRandom random = ThreadLocalRandom.current();

    public PVSGenerator(int size) {
        this.size = size;
        this.P = new ArrayList<>(
                Collections.nCopies(size, (float) 1 / size)
        );
        randomizeProbability();
        generateRandomValues();
        this.S = calculateCumulativeSum();
        this.backup = new PVSGenerator(this);
//        mapSumOnValues();
    }

    private void generateRandomValues() {
        this.values = IntStream.rangeClosed(1, size)
                .boxed()
                .collect(Collectors.toList());
    }

    //正直、いらないコンストラクターですが、ラボ５のために一応作りました。
    public PVSGenerator(float[] P) {
        this.P = new ArrayList<>();
        for (int i = 0; i < P.length; i++) {
            this.P.add(P[i]);
        }
        this.size = this.P.size();
        generateRandomValues();
        this.S = calculateCumulativeSum();
        this.backup = new PVSGenerator(this);
    }

    public PVSGenerator(List<Float> P, List<Integer> values, List<Float> S) {
        this.P = P;
        this.values = values;
        this.S = S;
        this.size = P.size();
        this.backup = new PVSGenerator(this);
    }

    private PVSGenerator(PVSGenerator pvsGenerator) {
        this.P = new ArrayList<>(pvsGenerator.P);
        this.values = new ArrayList<>(pvsGenerator.values);
        this.S = new ArrayList<>(pvsGenerator.S);
        this.size = values.size();
    }

    private void mapSumOnValues() {
        valuesMap = new HashMap<>();
        for (int i = 0 ; i < size; i++) {
            valuesMap.put(S.get(i), values.get(i));
        }
    }

    //make possible iterating by specified count
    //示した通りにループを仕上げること！
    private void randomizeProbability() {
        float chunk = P.get(0), sub;
        int index;
        for (int i = 0; i < size; i++) {
            sub = (float) random.nextDouble(chunk);
            do {
                index = random.nextInt(size);
            } while (index == i);
            randomize(sub, i, index);
        }
    }

    private void randomize(float rand, int current, int another) {
        float value1 = P.get(current);
        float value2 = P.get(another);
        float sub = value2 - rand;
        if (sub < 0) {
            return;
        }
        P.set(current, value1 + rand);
        P.set(another, sub);
    }

    private List<Float> calculateCumulativeSum() {
        //default capacity == 10 :D
        List<Float> result = new ArrayList<>();
        result.add(P.get(0));
        for (int i = 1; i < size; i++) {
            result.add(result.get(i - 1) + P.get(i));
        }
        return result;
    }

    public List<Float> getP() {
        return P;
    }

    public List<Integer> getValues() {
        return values;
    }

    public List<Float> getS() {
        return S;
    }

    public void prettyPrint() {
        System.out.println("P: " + P.toString());
        System.out.println("V: " + values.toString());
        System.out.println("S: " + S.toString() + "\n");

    }

    public float getMaxSValue() {
        return Collections.max(S);
    }

    /*一番近い数字を探してくれるファンクション*/
    public int findValue(float pick) {
        int index = findFirstOccurrence(pick);
        return values.get(index);
    }

    /*数字の居場所を探してくれるファンクション*/
    public int findFirstOccurrence(float pick) {
        return Collections.binarySearch(S, pick) * -1 - 1;
    }

    public int pollElement(float pick) {
        int index = findFirstOccurrence(pick);
        int value = values.get(index);
        int tail = size - 1;
        P.set(index, P.get(tail));
        values.set(index, values.get(tail));
        S.set(index, S.get(tail));
        removeFromTail();
        S = calculateCumulativeSum();
        return value;
    }

    public Float[] toArray() {
        Float[] result = new Float[size];
        P.toArray(result);
        return result;
    }

    private void removeFromTail() {
        int tail = size - 1;
        P.remove(tail);
        values.remove(tail);
        S.remove(tail);
        size--;
    }

    public void backupData() {
        this.P = new ArrayList<>(backup.P);
        this.values = new ArrayList<>(backup.values);
        this.S = new ArrayList<>(backup.S);
        this.size = P.size();
    }
}
