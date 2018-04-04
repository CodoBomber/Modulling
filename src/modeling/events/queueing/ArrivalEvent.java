package modeling.events.queueing;

class ArrivalEvent extends Event {

    private final Subject subject;

    ArrivalEvent(Subject subject, double time) {
        super(time);
        this.subject = subject;
    }

    @Override
    public void handle(QueuingSystem context) {
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
