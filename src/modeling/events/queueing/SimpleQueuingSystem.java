package modeling.events.queueing;

import modeling.generators.distribution.Distribution;

import java.util.LinkedList;
import java.util.Queue;
import java.util.TreeSet;
import java.util.stream.IntStream;

public class SimpleQueuingSystem implements QueuingSystem {

    private final int size;
    private final Distribution arrival;
    private final Distribution queuing;
    private TreeSet<Event> events = new TreeSet<>();
    private TreeSet<Subject> subjects = new TreeSet<>();
    private Queue<Subject> queue = new LinkedList<>();
    private double maxQueuedTime = 0d;
    private double sum;

    public SimpleQueuingSystem(Distribution arrival, Distribution queuing, int size) {
        this.size = size;
        this.arrival = arrival;
        this.queuing = queuing;
//        System.out.println("ARRIVAL METHOD: " + arrival.getDistributionName());
//        System.out.println("QUEUING METHOD: " + queuing.getDistributionName());
        generateEventTime();
    }

    private void generateEventTime() {
        IntStream.range(0, size).forEach(this::generateClients);
        for (Subject subject : subjects) {
            generateEvents(subject);
        }
//        subjects.forEach(this::generateEvents);
    }

    private void generateClients(int subjectId) {
        Subject subject = new Subject(subjectId, arrival.getNextValue(), queuing.getNextValue());
        subjects.add(subject);
        events.add(new ArrivalEvent(subject, subject.getArrivalPause()));
    }

    private void generateEvents(Subject subject) {
//        subject.printInformation();
        Subject previous = subjects.lower(subject);
        double readyToProcess, leavingTime;
        if (previous == null) {
            leavingTime = subject.getArrivalPause() + subject.getExecutionTime();
            readyToProcess = subject.getArrivalPause();
        } else {
//            readyToProcess = Math.max(previous.getLeftTime(), subject.getArrivalPause());
//            leavingTime = readyToProcess + subject.getExecutionTime();
        }
//        subject.setLeftTime(leavingTime);
//        events.add(new QueuingEvent(subject, readyToProcess));
//        events.add(new LeavingEvent(subject, subject.getLeftTime()));
    }

    public void simulate() {
        events.forEach(e -> {
            e.handle(this);
            printCurrentState(e);
        });
        System.out.println("Max queued time: " +  maxQueuedTime);
    }

    private void printCurrentState(Event event) {
//        if (true) { return; }
        System.out.println("TIME:" + event.getTime());
        System.out.println("CURRENT EVENT IS: " + event.getClass().getSimpleName());
//        System.out.println("CURRENT CLIENT IS: " + event.getSubject());
        System.out.println("Queue size is: " + queue.size());
        System.out.println();
    }

    public void setMaxQueuedTime(double maxQueuedTime) {
        this.maxQueuedTime = this.maxQueuedTime > maxQueuedTime ? this.maxQueuedTime : maxQueuedTime;
    }

    public boolean addToQueue(Subject subject) { return queue.add(subject); }

    public boolean pollFromQueue() { return queue.poll() != null; }

    public boolean isQueueEmpty() { return queue.isEmpty(); }

    public void setSum(double sum) {
        this.sum += sum;
    }

}
