package modeling.cluster;

import modeling.events.queueing.Event;
import modeling.events.queueing.QueuingSystem;
import modeling.events.queueing.Subject;
import modeling.generators.distribution.Distribution;

import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.TreeSet;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

public class ClusterQueuingSystem implements QueuingSystem {

    private double currentTime;
    private final double maxPassTime;
    private final Distribution arrivalDistribution;
    private final Distribution executionDistribution;
    private final TreeSet<Event> eventCalendar;
    private final int size;
    private final Cluster cluster;
    private final ThreadLocalRandom random = ThreadLocalRandom.current();
    private final Queue<Task> taskPool;

    public ClusterQueuingSystem(int memoryChunks,
                                int coresNumber,
                                Distribution arrivalDistribution,
                                Distribution executionDistribution,
                                double maxPassTime,
                                int size) {
        this.cluster = new Cluster(memoryChunks, coresNumber);
        this.maxPassTime = maxPassTime;
        this.arrivalDistribution = arrivalDistribution;
        this.executionDistribution = executionDistribution;
        this.eventCalendar = new TreeSet<>();
        this.size = size;
        this.taskPool = new LinkedList<>();
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

        taskPool.add(task);

        double previousArrive = 0;
        try {
            //100% arrival events guarantee
            previousArrive = eventCalendar.last()
                                          .getTime();
        } catch (NoSuchElementException ignore) {}
        TaskArrivalEvent arrivalEvent = new TaskArrivalEvent(
                task,
                task.getArrivalPause() + previousArrive
        );
        eventCalendar.add(arrivalEvent);
        task.setArrivalEvent(arrivalEvent);
    }

    private void executeSuitableTasks() {
        for (Task task = taskPool.peek(); cluster.isSuitable(task); task = taskPool.peek()) {
            execute(task);
            taskPool.poll();
        }
    }

    private void execute(Task task) {

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
        createTaskArrivalEvents();
        eventCalendar.forEach(event -> {
            event.handle(this);
        });
    }
}
