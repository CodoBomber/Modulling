package modeling.cluster;

import modeling.events.queueing.Event;
import modeling.events.queueing.QueuingSystem;
import modeling.events.queueing.Subject;

public class TaskQueuingEvent extends Event {

    private final Task task;

    public TaskQueuingEvent(Task task, double time) {
        super(time);
        this.task = task;
    }

    @Override
    protected void handle(QueuingSystem context) {

    }

    @Override
    public Subject getSubject() {
        return task;
    }
}
