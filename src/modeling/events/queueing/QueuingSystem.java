package modeling.events.queueing;

import modeling.generators.distribution.Distribution;

import java.util.*;
import java.util.stream.IntStream;

public class QueuingSystem {

    private final int size;
    private final Distribution arrival;
    private final Distribution queuing;
    private TreeSet<Event> events = new TreeSet<>();
    private List<Client> clients = new ArrayList<>();
    private Queue<Client> queue = new LinkedList<>();
    private double sum = 0d;

    public QueuingSystem(Distribution arrival, Distribution queuing, int size) {
        this.size = size;
        this.arrival = arrival;
        this.queuing = queuing;
//        System.out.println("ARRIVAL METHOD: " + arrival.getDistributionName());
//        System.out.println("QUEUING METHOD: " + queuing.getDistributionName());
        generateEventTime();
    }

    private void generateEventTime() {
        IntStream.range(0, size).forEach(this::generateClients);
        for (int i = 0; i < size; i++) {
            generateEvents(i);
        }
//        clients.forEach(this::generateEvents);
    }

    private void generateClients(int subjectId) {
        Client client = new Client(subjectId, arrival.getNextValue(), queuing.getNextValue());
        clients.add(client);
//        events.add(new ArrivalEvent(client, client.getArrivalTime()));
    }

    private void generateEvents(int clientIndex) {
//        client.printInformation();
        Client client = clients.get(clientIndex);
        double readyToProcess, leavingTime;
        if (clientIndex == 0) {
            leavingTime = client.getArrivalTime() + client.getQueuingTime();
            readyToProcess = client.getArrivalTime();
        } else {
            Client previous = clients.get(clientIndex - 1);
            client.setArrivalTime(previous.getArrivalTime() + client.getArrivalTime());
            readyToProcess = Math.max(previous.getLeftTime(), client.getArrivalTime());
            leavingTime = readyToProcess + client.getQueuingTime();
        }
        client.setLeftTime(leavingTime);
        events.add(new ArrivalEvent(client, client.getArrivalTime()));
        events.add(new QueuingEvent(client, readyToProcess));
        events.add(new LeavingEvent(client, client.getLeftTime()));
    }

    public void simulate() {
        events.forEach(e -> {
            e.handle(this);
            printCurrentState(e);
        });
        System.out.println("Medium: " + sum/size);
    }

    public void setSum(double sum) {
        this.sum += sum;
    }

    private void printCurrentState(Event event) {
//        if (true) { return; }
        System.out.println("TIME:" + event.getTime());
        System.out.println("CURRENT EVENT IS: " + event.getClass().getSimpleName());
        System.out.println("CURRENT CLIENT IS: " + event.getClient());
        System.out.println("Queuing TIME" + event.getClient().getQueuingTime());
        System.out.println("Leaving TIME" + event.getClient().getLeftTime());
        System.out.println("Queue size is: " + queue.size());
        System.out.println();
    }

    /*package*/ boolean addToQueue(Client client) { return queue.add(client); }

    /*package*/ boolean pollFromQueue() { return queue.poll() != null; }

    /*package*/ boolean isQueueEmpty() { return queue.isEmpty(); }


}
