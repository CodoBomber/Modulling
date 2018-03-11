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
        int compare = Double.compare(this.time, o.time);
        return compare == 0
                ? Integer.compare(this.hashCode(), o.hashCode())
                : compare;
    }
}
