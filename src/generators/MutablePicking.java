package generators;

import java.util.concurrent.ThreadLocalRandom;

public class MutablePicking {

    private final PVSGenerator pvs;
    private ThreadLocalRandom random = ThreadLocalRandom.current();

    public MutablePicking(PVSGenerator pvs) {
        this.pvs = pvs;
    }

    public void pick(int count, int iterations) {
        float pick;
        float max;
        for (int i = 0; i < iterations; i++) {
            for (int j = 0; j < count && pvs.size != 0; j++) {
                max = pvs.getMaxSValue();
                pick = (float) random.nextDouble(max);
//            System.out.print(pick + ": ");
                System.out.println(pvs.pollElement(pick));
            }
            pvs.backupData();
        }
    }
}
