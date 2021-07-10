import java.util.*;

public class Observable<T> implements IObservable<T>{
    Map<IObserver<T>, Integer> observers = new HashMap<>();
    TreeSet<Integer> priorities = new TreeSet<>();
    TreeSet<Integer> prioritiesReverse = new TreeSet<>();

    protected T getChangedData() {
        return null;
    }

    @Override
    public void registerObserver(IObserver<T> observer, int priority) {
        observers.put(observer, priority);
        priorities.add(priority);
        prioritiesReverse = (TreeSet<Integer>)priorities.descendingSet();
    }

    @Override
    public void notifyObservers() {
        T data = getChangedData();
        for(Integer priority: prioritiesReverse) {
            for (Map.Entry<IObserver<T>, Integer> entry : observers.entrySet()) {
                if (entry.getValue().equals(priority)) {
                    entry.getKey().update(data);
                }
            }
        }
    }

    @Override
    public void removeObserver(IObserver<T> observer) {
        observers.remove(observer);
    }

}
