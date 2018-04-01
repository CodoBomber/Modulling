package modeling.cluster;

import modeling.events.queueing.QueuingSystem;
import modeling.events.queueing.Subject;

public class Cluster implements QueuingSystem {

    private final double passTime;
    private final int memoryChunks;
    private final int coresNumber;

    public Cluster(int memoryChunks, int coresNumber, double maxPassTime) {
        this.memoryChunks = memoryChunks;
        this.coresNumber = coresNumber;
        this.passTime = maxPassTime;
    }

    @Override
    public boolean addToQueue(Subject subject) {
        return false;
    }

    @Override
    public boolean pollFromQueue() {
        return false;
    }

    @Override
    public boolean isQueueEmpty() {
        return false;
    }
}
