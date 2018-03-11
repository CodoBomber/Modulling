package modeling.events.queueing;

public abstract class Event implements Comparable<Event> {

    private final Client client;
    private final double time;

    public Event(Client client, double time) {
        this.client = client;
        this.time = time;
    }

    protected abstract void handle(QueuingSystem context);

    public Client getClient() {
        return client;
    }

    public double getTime() {
        return time;
    }

    @Override
    public int compareTo(Event o) {
        return Double.compare(this.time, o.time);
    }
}
