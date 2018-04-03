package modeling.cluster;

import modeling.events.queueing.Event;
import modeling.events.queueing.QueuingSystem;
import modeling.events.queueing.Subject;

class TaskArrivalEvent extends Event {

    private final Task task;
    private EventHandler handler;

    public TaskArrivalEvent(Task task, double time) {
        super(time);
        this.task = task;
    }

    public void setHandler(EventHandler handler) {
        this.handler = handler;
    }

    @Override
    public void handle(QueuingSystem context) {
        if (handler != null) {
            handler.handle();
        }
    }

    @Override
    public Subject getSubject() {
        return task;
    }

    @Override
    public int hashCode() {
        return 1;
    }
}
