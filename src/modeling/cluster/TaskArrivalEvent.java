package modeling.cluster;

import modeling.events.queueing.Event;
import modeling.events.queueing.QueuingSystem;
import modeling.events.queueing.Subject;

class TaskArrivalEvent extends Event {

    private final Task task;

    TaskArrivalEvent(Task task, double time) {
        super(time);
        this.task = task;
        this.task.setArrivalEvent(this);
    }

    @Override
    public void handle(QueuingSystem context) {
        handleConcrete((ClusterQueuingSystem) context);
    }

    @Override
    public void printInformation() {
        task.printInformation();
    }

    private void handleConcrete(ClusterQueuingSystem context) {
//        double time = context.getNextExecutionTime(this.task);
//        context.addExecutionEvent(task, time);
        context.addToQueue(task);
        context.schedule();
    }

    public Subject getSubject() {
        return task;
    }

    @Override
    public int hashCode() {
        return 1;
    }
}
