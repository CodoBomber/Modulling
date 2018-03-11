package modeling.events.queueing;

import modeling.generators.distribution.Distribution;

import java.util.TreeSet;
import java.util.stream.IntStream;

public class QueuingSystem {

    private final int size;
    private final Distribution arrival;
    private final Distribution queuing;
    private TreeSet<Event> events = new TreeSet<>();
    private TreeSet<Client> clients = new TreeSet<>();

    public QueuingSystem(Distribution arrival, Distribution queuing, int size) {
        this.size = size;
        this.arrival = arrival;
        this.queuing = queuing;
        generateEventTime();
    }

    private void generateEventTime() {
        IntStream.range(0, size).forEach(this::generateEvents);
    }

    private void generateEvents(int subjectId) {
        System.out.println("USER #" + subjectId);
        System.out.println("ARRIVAL METHOD: " + arrival.getDistributionName());
        System.out.println("QUEUING METHOD: " + queuing.getDistributionName());

        Client previous;
        double startQueuing;
        Client client = new Client(subjectId, arrival.getNextValue(), queuing.getNextValue());
        if (!clients.isEmpty()) {
            previous = clients.lower(client);
            startQueuing = previous.getLeftTime();
            client.setLeftTime(previous.getLeftTime() + client.getQueuingTime());
        } else {
            //if client is first client
            startQueuing = client.getArrivalTime();
            client.setLeftTime(client.getArrivalTime() + client.getQueuingTime());
        }
        clients.add(client);

        events.add(new ArrivalEvent(client, client.getArrivalTime()));
        events.add(new QueuingEvent(client, startQueuing));
        events.add(new LeavingEvent(client, client.getLeftTime()));
    }
}
