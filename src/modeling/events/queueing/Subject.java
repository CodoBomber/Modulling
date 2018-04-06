
package modeling.events.queueing;

public class Subject implements Comparable<Subject> {

    private final int subjectId;
    /**
     * Через сколько придёт от предыдущего
     */
    private final double arrivalPause;
    /**
     * Время, сколько будет обрабатываться
     */
    private final double executionTime;

    private Event arrivalEvent;
    private Event leavingEvent;

    private double executionStartTime;

    public Subject(int subjectId, double arrivalTime, double queuingTime) {
        this.subjectId = subjectId;
        this.arrivalPause = arrivalTime;
        this.executionTime = queuingTime;
    }

    public double getExecutionStartTime() {
        return executionStartTime;
    }

    public int getSubjectId() {
        return subjectId;
    }

    public double getArrivalPause() {
        return arrivalPause;
    }

    public double getExecutionTime() {
        return executionTime;
    }

    @Override
    public int compareTo(Subject o) {
        return Double.compare(arrivalEvent.getTime(), o.arrivalEvent.getTime());
    }

    public void printInformation() {
        System.out.println(toString());
        System.out.println("Arrival in: " + arrivalEvent.getTime());
        System.out.println("Processed in: " + executionStartTime);
//        System.out.println("Leaving in: " + leavingEvent.getTime());
        System.out.println("Arrival pause: " + arrivalPause);
        System.out.println("Execution time: " + executionTime);
    }

    public Event getArrivalEvent() {
        return arrivalEvent;
    }

    public Event getLeavingEvent() {
        return leavingEvent;
    }

    public void setArrivalEvent(Event arrivalEvent) {
        this.arrivalEvent = arrivalEvent;
    }

    public void setLeavingEvent(Event leavingEvent) {
        this.leavingEvent = leavingEvent;
    }

    public void setExecutionStartTime(double time) {
        this.executionStartTime = time;
    }

    public void decrementExecutionTime(double executionTime) {
        this.executionStartTime -= executionTime;
    }

    public void incrementExecutionTime(double executionTime) {
        this.executionStartTime += executionTime;
    }

    @Override
    public String toString() {
        return "Subject #" + String.valueOf(subjectId);
    }
}
