package modeling.events.queueing;

public class Client implements Comparable<Client> {

    private final int subjectId;
    private final double arrivalTime;
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
        return Integer.compare(subjectId, o.subjectId);
    }
}
