package algorithms;

import generators.PVSGenerator;

import java.util.concurrent.ThreadLocalRandom;

public class ImmutablePicking {

    private final PVSGenerator pvs;
    private ThreadLocalRandom random = ThreadLocalRandom.current();

    public ImmutablePicking(PVSGenerator pvs) {
        this.pvs = pvs;
    }

    public void pick(int count) {
        float pick;
        float max = pvs.getMaxSValue();
        for (int i = 0; i < count; i++) {
            pick = (float) random.nextDouble(max);
//            System.out.print(pick + ": ");
            System.out.println(pvs.findValue(pick));
        }
    }

}
