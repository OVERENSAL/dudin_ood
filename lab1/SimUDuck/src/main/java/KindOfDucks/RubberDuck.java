package KindOfDucks;

import DanceStrategy.NoDanceBehavior;
import FlyStrategy.FlyNoWay;
import QuackStrategy.SqueakBehavior;

public class RubberDuck extends Duck{

    public RubberDuck() {
        this.dance = new NoDanceBehavior();
        this.fly = new FlyNoWay();
        this.quack = new SqueakBehavior();
    }

    @Override
    public void display() {
        System.out.println("I'm a rubber duck!");
    }
}
