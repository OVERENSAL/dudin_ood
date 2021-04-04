public interface Observable {

    void registerObserver();
    void notifyObservers();
    
}
