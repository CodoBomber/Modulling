package modeling.events.queueing;

public class QueuingEvent extends Event {

    QueuingEvent(Client client, double time) {
        super(client, time);
    }

    @Override
    protected void handle(QueuingSystem context) {
        context.pollFromQueue();
    }

    @Override
    public int hashCode() {
        return 3;
    }
}
