package modeling.events.queueing;

public class Client implements Comparable<Client> {

    private final int subjectId;
    private double arrivalTime;
    private final double queuingTime;
    private double leftTime;

    public Client(int subjectId, double arrivalTime, double queuingTime) {
        this.subjectId = subjectId;
        this.arrivalTime = arrivalTime;
        this.queuingTime = queuingTime;
    }

    public void setLeftTime(double leftTime) {
        this.leftTime = leftTime;
    }

    public void setArrivalTime(double arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public int getSubjectId() {
        return subjectId;
    }

    public double getArrivalTime() {
        return arrivalTime;
    }

    public double getQueuingTime() {
        return queuingTime;
    }

    public double getLeftTime() {
        return leftTime;
    }

    @Override
    public int compareTo(Client o) {
        return Double.compare(arrivalTime, o.getArrivalTime());
    }

    void printInformation() {
        System.out.println(toString());
        System.out.println("Arrival: " + getArrivalTime());
        System.out.println("Processing: " + getQueuingTime());
        System.out.println("Leaving: " + getLeftTime());
    }

    @Override
    public String toString() {
        return "Client #" + String.valueOf(subjectId);
    }
}
