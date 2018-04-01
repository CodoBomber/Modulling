package modeling.events.queueing;

public class ArrivalEvent extends Event {

    private final Subject subject;

    ArrivalEvent(Subject subject, double time) {
        super(time);
        this.subject = subject;
    }

    @Override
    protected void handle(QueuingSystem context) {
        context.addToQueue(subject);
    }

    @Override
    public Subject getSubject() {
        return subject;
    }

    @Override
    public int hashCode() {
        return 1;
    }
}
