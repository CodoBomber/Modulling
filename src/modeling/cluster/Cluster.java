package modeling.cluster;

public class Cluster {

    private final int memoryChunks;
    private final int coresNumber;
//    private TreeSet<Task>

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
}
