package modeling.cluster;

import modeling.events.queueing.Subject;

public class Task extends Subject {

    private final int memoryChunks;
    private final int coresNumber;
    private final double passTime;

    public Task(Subject subject, int memoryChunks, int coresNumber, double passTime) {
        super(subject.getSubjectId(), subject.getArrivalTime(), subject.getQueuingTime());
        this.memoryChunks = memoryChunks;
        this.coresNumber = coresNumber;
        this.passTime = passTime;
    }
}
