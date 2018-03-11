package modeling.events.queueing;

import modeling.generators.distribution.Distribution;

import java.util.LinkedList;
import java.util.Queue;
import java.util.TreeSet;
import java.util.stream.IntStream;

public class QueuingSystem {

    private final int size;
    private final Distribution arrival;
    private final Distribution queuing;
    private TreeSet<Event> events = new TreeSet<>();
    private TreeSet<Client> clients = new TreeSet<>();
    private Queue<Client> queue = new LinkedList<>();

    public QueuingSystem(Distribution arrival, Distribution queuing, int size) {
        this.size = size;
        this.arrival = arrival;
        this.queuing = queuing;
        System.out.println("ARRIVAL METHOD: " + arrival.getDistributionName());
        System.out.println("QUEUING METHOD: " + queuing.getDistributionName());
        generateEventTime();
    }

    private void generateEventTime() {
        IntStream.rangeClosed(0, size).forEach(this::generateClients);
        clients.forEach(this::generateEvents);
    }

    private void generateClients(int subjectId) {
        Client previous;
        Client client = new Client(subjectId, arrival.getNextValue(), queuing.getNextValue());
        previous = clients.lower(client);
        double leavingTime = previous == null
                ? client.getArrivalTime() + client.getQueuingTime()
                : previous.getLeftTime() + client.getQueuingTime();
        client.setLeftTime(leavingTime);
        clients.add(client);
        events.add(new ArrivalEvent(client, client.getArrivalTime()));
    }

    private void generateEvents(Client client) {
        Client previous = clients.lower(client);
        events.add(new QueuingEvent(client, previous == null ? client.getArrivalTime() : previous.getLeftTime()));
        events.add(new LeavingEvent(client, client.getLeftTime()));
    }

    public void simulate() {
        events.forEach(e -> {
            e.handle(this);
            printCurrentState(e);
        });
    }

    private void printCurrentState(Event event) {
        if (true) { return; }
        System.out.println("TIME:" + event.getTime());
        System.out.println("CURRENT EVENT IS: " + event.getClass().getSimpleName());
        System.out.println("CURRENT CLIENT IS: " + event.getClient());
        System.out.println("Queue size is: " + queue.size());
        System.out.println();
    }

    /*package*/ boolean addToQueue(Client client) { return queue.add(client); }

    /*package*/ boolean pollFromQueue() { return queue.poll() != null; }

    /*package*/ boolean isQueueEmpty() { return queue.isEmpty(); }


}
