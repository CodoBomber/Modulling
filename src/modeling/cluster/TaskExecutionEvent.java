package modeling.cluster;

import modeling.events.queueing.Event;
import modeling.events.queueing.QueuingSystem;
import modeling.events.queueing.Subject;

class TaskExecutionEvent extends Event {

    private final Task task;

    TaskExecutionEvent(Task task, double time) {
        super(time);
        this.task = task;
        this.task.setExecutionEvent(this);
    }

    @Override
    public void handle(QueuingSystem context) {
        handleConcrete((ClusterQueuingSystem) context);
    }

    private void handleConcrete(ClusterQueuingSystem context) {
        context.addLeavingEvent(task, this.getTime() + task.getExecutionTime());
        context.executeTasks(task);
    }

    @Override
    public Subject getSubject() {
        return task;
    }

    @Override
    public int hashCode() {
        return 2;
    }
}
