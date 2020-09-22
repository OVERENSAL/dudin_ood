package Behaviors;

public class Fly {
    private int flyCount = 0;

    public void flyWithWings() {
        flyCount++;
        System.out.println("I'm flying with wings! It's my " + flyCount + " flight!");
    }

    public void flyNoWay() {}
}
