package modeling.events.queueing;

public abstract class Event implements Comparable<Event> {

    private final double time;

    public Event(double time) {
        this.time = time;
    }

    public abstract void handle(QueuingSystem context);

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

    public abstract Subject getSubject();
}
