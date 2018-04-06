package modeling.cluster;

import modeling.events.queueing.Subject;

public class Task extends Subject implements Notifier {

    private int memoryChunks;
    private int coresNumber;
    private double passTime;

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
        System.out.println("Executing ");
    }

    @Override
    public String toString() {
        return String.format("{%d: %.3f, %.3f}",getSubjectId(), getExecutionTime(), getPassTime());
    }

    @Override
    public void onTimeTick(double currentTime) {
        if (passTime > 0) {
            passTime -=  currentTime - getArrivalEvent().getTime();
        }
    }
}
