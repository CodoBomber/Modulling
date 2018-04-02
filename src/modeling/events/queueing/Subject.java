
package modeling.events.queueing;

public class Subject implements Comparable<Subject> {

    private final int subjectId;
    /**
     * Время когда придёт
     */
    private final double arrivalTime;
    /**
     * Время, сколько будет обрабатываться
     */
    private final double queuingTime;
    /**
     * Время когда уйдёт
     */
    private double leftTime;

    public Subject(int subjectId, double arrivalTime, double queuingTime) {
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
    public int compareTo(Subject o) {
        return Double.compare(arrivalTime, o.getArrivalTime());
    }

    public void printInformation() {
        System.out.println(toString());
        System.out.println("Arrival: " + getArrivalTime());
        System.out.println("Processing: " + getQueuingTime());
        System.out.println("Leaving: " + getLeftTime());
    }

    @Override
    public String toString() {
        return "Subject #" + String.valueOf(subjectId);
    }
}
