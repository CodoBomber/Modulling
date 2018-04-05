package modeling.cluster;

import modeling.events.queueing.Event;
import modeling.events.queueing.QueuingSystem;
import modeling.events.queueing.Subject;

class TaskLeavingEvent extends Event {

    private final Task task;

    TaskLeavingEvent(Task task, double time) {
        super(time);
        this.task = task;
        this.task.setLeavingEvent(this);
    }

    @Override
    public void handle(QueuingSystem context) {
        handleConcrete((ClusterQueuingSystem) context);
    }

    private void handleConcrete(ClusterQueuingSystem context) {

    }

    @Override
    public Subject getSubject() {
        return task;
    }

    @Override
    public int hashCode() {
        //Самый высший приоритет
        return 0;
    }
}
