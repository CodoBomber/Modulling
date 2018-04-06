package modeling.cluster;

import modeling.events.queueing.Event;
import modeling.events.queueing.QueuingSystem;

import java.util.List;

class ExecutionEvent extends Event {

    private final List<Task> tasks;

    /**
     * Принимаем задачи, готовые для запуска в данный момент
     * При этом задачи должны проходить ВСЕ вместе, а не только какие-нибудь из них.
     * @implNote Реализовать фильтрацию задач, которые проходят через стримы
     * @param tasks
     * @param time
     */
    ExecutionEvent(List<Task> tasks, double time) {
        super(time);
        this.tasks = tasks;
//        this.tasks.setExecutionEvent(this);
    }

    @Override
    public void handle(QueuingSystem context) {
        handleConcrete((ClusterQueuingSystem) context);
    }

    @Override
    public void printInformation() {

    }

    private void handleConcrete(ClusterQueuingSystem context) {
//        context.addLeavingEvent(tasks, this.getTime() + tasks.getExecutionTime());
        context.executeTasks();
    }

    public List<Task> getExecutableTasks() {
        return tasks;
    }

    @Override
    public int hashCode() {
        return 2;
    }
}
