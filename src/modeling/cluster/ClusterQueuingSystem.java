package modeling.cluster;

import modeling.events.queueing.Event;
import modeling.events.queueing.QueuingSystem;
import modeling.events.queueing.Subject;
import modeling.generators.distribution.Distribution;

import java.util.LinkedList;
import java.util.NoSuchElementException;
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
    private final LinkedList<Task> taskPool;
    private int queuedTaskCounter;
    private double queuedTime;

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
                    e.printInformation();
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
                random.nextInt(16, 65),
                random.nextInt(8, 33),
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

    void executeTasks() {
        for (Task task = taskPool.peek(); cluster.isSuitable(task); task = taskPool.peek()) {
            Task suitableTask = taskPool.poll();
            eventCalendar.add(new TaskLeavingEvent(suitableTask, currentTime + task.getExecutionTime()));
            suitableTask.setExecutionStartTime(currentTime);
            queuedTime += currentTime - suitableTask.getArrivalEvent().getTime();
            if (queuedTime > 0)
                queuedTaskCounter++;
            cluster.execute(suitableTask);
        }
    }

    /**
     * queue rebalancing method
     */
    void schedule() {
        rebalanceAvalanchely();
        executeTasks();
    }

    /**
     * Avalanche algorithm which moves all suitable tasks to direction on the end of our queue.
     */
    private void rebalanceAvalanchely() {
        int index;

        for (int i = taskPool.size() - 2; i >= 0; i--) {
            if (!cluster.isSuitable(taskPool.get(i)) && cluster.isSuitable(taskPool.get(i + 1))) {
                index = i;
                while ((index + 1) < taskPool.size() && cluster.isSuitable(taskPool.get(index + 1))
                        && taskPool.get(index).getPassTime() > 0) {
//                    tempTask = taskPool.get(index);
                    taskPool.set(index + 1, taskPool.set(index, taskPool.get(index + 1)));
                    taskPool.get(index).decrementExecutionTime(taskPool.get(index + 1).getExecutionTime());
                    taskPool.get(index + 1).incrementExecutionTime(taskPool.get(index).getExecutionTime());
                }
            }
        }
    }

    @Override
    public boolean addToQueue(Subject subject) {
//        queuedTaskCounter++;
        return taskPool.add((Task) subject);
    }

    @Override
    @Deprecated
    public boolean pollFromQueue() {
        return false;
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
                    taskPool.forEach(task -> task.onTimeTick(currentTime));
                    event.handle(this);
                    System.out.println(event.getClass());
                    System.out.println(event.getTime());
                    event.printInformation();
                    System.out.println(taskPool);
                }
        );

        System.out.println("################################TOTAL##############################");
        System.out.println("NOT FOR ALL: " + queuedTime / queuedTaskCounter);
        System.out.println("FOR ALL: " + queuedTime / size);
    }


    public void addLeavingEvent(Task task, double time) {
        eventCalendar.add(new TaskLeavingEvent(task, time));
    }

    public void finishTask(Task task) {
        cluster.freeTaskResource(task);
    }
}
