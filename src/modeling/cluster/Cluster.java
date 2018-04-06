package modeling.cluster;

import java.util.TreeSet;

public class Cluster {

    private int memoryChunks;
    private int coresNumber;
    private TreeSet<Task> executingTasks;

    public Cluster(int memoryChunks, int coresNumber) {
        this.memoryChunks = memoryChunks;
        this.coresNumber = coresNumber;
        executingTasks = new TreeSet<>();
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

    public void execute(Task task) {
        executingTasks.add(task);
        coresNumber -= task.getCoresNumber();
        memoryChunks -= task.getMemoryChunks();
        if (memoryChunks < 0 || coresNumber < 0) {
            throw new IllegalStateException("Прошла большая задача (???) #" + task.getSubjectId());
        }
    }

    public void freeTaskResource(Task task) {
        if (!executingTasks.remove(task)) {
            throw new IllegalStateException("Извлекается　несуществующая　задача　#" + task.getSubjectId());
        }
        memoryChunks += task.getMemoryChunks();
        coresNumber += task.getCoresNumber();
    }
}
