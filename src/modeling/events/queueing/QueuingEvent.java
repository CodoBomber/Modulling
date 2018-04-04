package modeling.events.queueing;

class QueuingEvent extends Event {

    private final Subject subject;

    QueuingEvent(Subject subject, double time) {
        super(time);
        this.subject = subject;
    }

    @Override
    public void handle(QueuingSystem context) {
        context.pollFromQueue();
    }

    @Override
    public Subject getSubject() {
        return subject;
    }

    @Override
    public int hashCode() {
        return 3;
    }
}
