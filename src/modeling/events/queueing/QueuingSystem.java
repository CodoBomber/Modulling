package modeling.events.queueing;

public interface QueuingSystem {

    default void setSum(double sum) {}

    boolean addToQueue(Subject subject);

    boolean pollFromQueue();

    boolean isQueueEmpty();

    void simulate();

}
