package modeling.cluster;

import modeling.events.queueing.Subject;

public class Task extends Subject {

    private final int memoryChunks;
    private final int coresNumber;
    private final double passTime;
    //arrival event
    //execution event
    //leaving event

    public Task(Subject subject, int memoryChunks, int coresNumber, double passTime) {
        super(subject.getSubjectId(), subject.getArrivalTime(), subject.getQueuingTime());
        this.memoryChunks = memoryChunks;
        this.coresNumber = coresNumber;
        this.passTime = passTime;
    }

    public int getMemoryChunks() {
        return memoryChunks;
    }

    public int getCoresNumber() {
        return coresNumber;
    }

    public double getPassTime() {
        return passTime;
    }
}
