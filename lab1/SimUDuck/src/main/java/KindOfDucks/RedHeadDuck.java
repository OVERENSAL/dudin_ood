package KindOfDucks;

import DanceStrategy.MinuetBehavior;
import FlyStrategy.FlyWithWings;
import QuackStrategy.QuackBehavior;

public class RedHeadDuck extends Duck{

    public RedHeadDuck() {
        this.dance = new MinuetBehavior();
        this.fly = new FlyWithWings();
        this.quack = new QuackBehavior();
    }

    @Override
    public void display() {
        System.out.println("I'm a redhead duck!");
    }
}
