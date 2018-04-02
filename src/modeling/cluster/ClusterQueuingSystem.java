package modeling.cluster;

import modeling.events.queueing.Event;
import modeling.events.queueing.QueuingSystem;
import modeling.events.queueing.Subject;
import modeling.generators.distribution.Distribution;

import java.util.NoSuchElementException;
import java.util.TreeSet;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

public class ClusterQueuingSystem implements QueuingSystem {

    private final double maxPassTime;
    private final Distribution arrivalDistribution;
    private final Distribution executionDistribution;
    private final TreeSet<Event> eventCalendar;
    private final int size;
    private final Cluster cluster;
    private final ThreadLocalRandom random = ThreadLocalRandom.current();

    public ClusterQueuingSystem(Cluster cluster,
                                Distribution arrivalDistribution,
                                Distribution executionDistribution,
                                double maxPassTime,
                                int size) {
        this.cluster = cluster;
        this.maxPassTime = maxPassTime;
        this.arrivalDistribution = arrivalDistribution;
        this.executionDistribution = executionDistribution;
        this.eventCalendar = new TreeSet<>();
        this.size = size;
        createTaskArrivalEvents();
        prettyPrint();
    }

    private void prettyPrint() {
        eventCalendar.forEach(
                e -> {
                    System.out.println("TIME: " + e.getTime());
                    e.getSubject().printInformation();
                }
        );
    }

    private void createTaskArrivalEvents() {
        IntStream.range(0, size)
                 .forEach(this::generateTasks);
    }

    private void generateTasks(int subjectId) {
        Subject subject = new Subject(
                subjectId,
                arrivalDistribution.getNextValue(),
                executionDistribution.getNextValue()
        );
        Task task = new Task(
                subject,
                random.nextInt(1, cluster.getMemoryChunks()),
                random.nextInt(1, cluster.getCoresNumber()),
                maxPassTime
        );
//        tasksLi.add(subject);
        double previousArrive = 0;
        try {
            //100% arrival events guarantee
            previousArrive = eventCalendar.last()
                                          .getTime();
        } catch (NoSuchElementException ignore) {}

        eventCalendar.add(new TaskArrivalEvent(task, task.getArrivalTime() + previousArrive));
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

    @Override
    public void simulate() {

    }
}
