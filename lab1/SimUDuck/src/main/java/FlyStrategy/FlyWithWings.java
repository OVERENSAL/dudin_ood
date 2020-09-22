package FlyStrategy;

public class FlyWithWings implements Fly {

    private int flyCount = 0;

    @Override
    public void fly() {
        flyCount++;
        System.out.println("I'm flying with wings! It's my " + flyCount + " flight!");
    }
}
