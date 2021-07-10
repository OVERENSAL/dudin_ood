public interface IObservable<T> {

    void registerObserver(IObserver<T> observer, int priority);
    void notifyObservers();
    void removeObserver(IObserver<T> observer);
}
