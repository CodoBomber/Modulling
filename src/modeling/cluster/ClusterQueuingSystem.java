package modeling.cluster;

import modeling.events.queueing.Event;
import modeling.events.queueing.QueuingSystem;
import modeling.events.queueing.Subject;
import modeling.generators.distribution.Distribution;

import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

public class ClusterQueuingSystem implements QueuingSystem {

    private double currentTime;
    private final double maxPassTime;
    private final Distribution arrivalDistribution;
    private final Distribution executionDistribution;
    private final ConcurrentSkipListSet<Event> eventCalendar;
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
        this.eventCalendar = new ConcurrentSkipListSet<>();
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

        double previousArrive = 0;
        try {
            //100% arrival events guarantee
            previousArrive = eventCalendar.last()
                                          .getTime();
        } catch (NoSuchElementException ignore) {}
        eventCalendar.add(new TaskArrivalEvent(task, task.getArrivalPause() + previousArrive));
    }

    void executeSuitableTasks() {
        for (Task task = taskPool.peek(); cluster.isSuitable(task); task = taskPool.peek()) {
            execute(task);
            pollFromQueue();
        }
        /// TODO: 18/04/04
    }

    void execute(Task task) {

    }

    @Override
    public boolean addToQueue(Subject subject) {
        return taskPool.add((Task) subject);
    }

    @Override
    public boolean pollFromQueue() {
        Task task = taskPool.poll();
        return task != null;
    }

    @Override
    public boolean isQueueEmpty() {
        return false;
    }

    @SuppressWarnings("SimplifyStreamApiCallChains")
    @Override
    public void simulate() {
        createTaskArrivalEvents();
        //functional programming stream is actually categorically necessary!!!
        eventCalendar.stream().forEach(
                event -> {
                    currentTime = event.getTime();
                    event.handle(this);
                    System.out.println(event.getClass());
                    System.out.println(event.getTime());
                    event.getSubject().printInformation();
                }
        );
    }

    public void addExecutionEvent(Task task, double time) {
        eventCalendar.add(new TaskExecutionEvent(task, time));
    }

    public double getNextExecutionTime(Task newTask) {
        Task task = taskPool.peek();
        if (task == null) {
            if (cluster.isSuitable(newTask)) {
                return currentTime;
            }
            return cluster.getTimeWhenSuitable(newTask);
        }
        return task.getExecutionEvent().getTime() + task.getExecutionTime();
    }

    /**
     *
     * @param taskExecutionEvent
     */
    public void shiftExecutionEvent(TaskExecutionEvent taskExecutionEvent) {

    }
}
