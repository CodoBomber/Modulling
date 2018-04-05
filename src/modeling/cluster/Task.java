package modeling.cluster;

import modeling.events.queueing.Subject;

public class Task extends Subject {

    private final int memoryChunks;
    private final int coresNumber;
    private final double passTime;

    public Task(Subject subject, int memoryChunks, int coresNumber, double passTime) {
        super(subject.getSubjectId(), subject.getArrivalPause(), subject.getExecutionTime());
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

    @Override
    public int compareTo(Subject o) {
        return Double.compare(this.getLeavingEvent().getTime(), o.getLeavingEvent().getTime());
    }

    @Override
    public void printInformation() {
        super.printInformation();
        System.out.println("Memory chunks: " + memoryChunks);
        System.out.println("CoresNumber " + coresNumber);
        System.out.println("PassTime " + passTime);
    }
}
