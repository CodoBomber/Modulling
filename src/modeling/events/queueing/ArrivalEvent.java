package modeling.events.queueing;

public class ArrivalEvent extends Event {

    ArrivalEvent(Client client, double time) {
        super(client, time);
    }

    @Override
    protected void handle(QueuingSystem context) {

    }
}
