package modeling.events.queueing;

public interface QueuingSystem {

    default void setSum(double sum) {}

    default boolean addToQueue(Subject subject) {return false;}

    boolean pollFromQueue();

    boolean isQueueEmpty();

    void simulate();
}
