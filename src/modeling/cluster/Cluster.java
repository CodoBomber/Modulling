package modeling.cluster;

import java.util.TreeSet;

public class Cluster {

    private final int memoryChunks;
    private final int coresNumber;
    private TreeSet<Task> executingTasks;

    public Cluster(int memoryChunks, int coresNumber) {
        this.memoryChunks = memoryChunks;
        this.coresNumber = coresNumber;
    }

    public int getMemoryChunks() {
        return memoryChunks;
    }

    public int getCoresNumber() {
        return coresNumber;
    }

    public boolean isSuitable(Task task) {
        return task != null
                && this.getCoresNumber() >= task.getCoresNumber()
                && this.getMemoryChunks() >= task.getMemoryChunks();
    }

    public double getTimeWhenSuitable(Task taskToExecute) {
        double totalMemoryChunks = 0d, totalCoresNumber = 0d;
        for (Task task : executingTasks) {
            totalCoresNumber += task.getCoresNumber();
            totalMemoryChunks += task.getMemoryChunks();
            if (totalCoresNumber >= taskToExecute.getCoresNumber()
                    && totalMemoryChunks >= taskToExecute.getMemoryChunks()) {
                return task.getLeavingEvent().getTime();
            }
        }
        throw new IllegalStateException("Неверное состояние задачи #" + taskToExecute.getSubjectId());
    }

    public void execute(Task task) {
        executingTasks.add(task);
        //...
    }
}
