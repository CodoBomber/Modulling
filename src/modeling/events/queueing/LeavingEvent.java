package modeling.events.queueing;

/**
 *ただ客の正確な情報をスクリーンに表示しますだけのクラスです。
 */
public class LeavingEvent extends Event {

    public LeavingEvent(Client client, double time) {
        super(client, time);
    }

    @Override
    protected void handle(QueuingSystem context) {
        /*System.out.print(
                "Клиент №" + getClient().getSubjectId() + " простоял в очереди: ");*/
        double queuingTime = getClient().getLeftTime() - getClient().getQueuingTime() - getClient().getArrivalTime();
//        if (queuingTime <= 0.0000001) { return; }
        context.setMaxQueuedTime(queuingTime);
        System.out.format("%.5f", queuingTime);
        System.out.println();
    }

    @Override
    public int hashCode() {
        return 2;
    }
}
