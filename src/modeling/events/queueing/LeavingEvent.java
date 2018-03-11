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
        System.out.println(
                "Клиент №" + getClient().getSubjectId() + " прошёл обслуживание за "
                + (getClient().getLeftTime() - getClient().getQueuingTime() - getClient().getArrivalTime())
        );
    }
}
