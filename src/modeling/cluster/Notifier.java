package modeling.cluster;

@FunctionalInterface
public interface Notifier {

    void onTimeTick(double currentTime);

}
