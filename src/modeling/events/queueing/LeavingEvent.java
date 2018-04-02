package modeling.events.queueing;

/**
 *ただ客の正確な情報をスクリーンに表示しますだけのクラスです。
 */
class LeavingEvent extends Event {

    private final Subject subject;

    public LeavingEvent(Subject subject, double time) {
        super(time);
        this.subject = subject;
    }

    @Override
    protected void handle(QueuingSystem context) {
        /*System.out.print(
                "Клиент №" + getClient().getSubjectId() + " простоял в очереди: ");*/
        double queuingTime = subject.getLeftTime() - subject.getQueuingTime() - subject.getArrivalTime();
//        if (queuingTime <= 0.0000001) { return; }
        context.setSum(queuingTime);
//        System.out.format("%.5f", queuingTime);
        System.out.println();
    }

    @Override
    public Subject getSubject() {
        return subject;
    }

    @Override
    public int hashCode() {
        return 2;
    }
}
