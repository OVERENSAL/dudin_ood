package KindOfDucks;

import DanceStrategy.NoDanceBehavior;
import FlyStrategy.FlyNoWay;
import QuackStrategy.MuteQuackBehavior;

public class DecoyDuck extends Duck{

    public DecoyDuck() {
        this.dance = new NoDanceBehavior();
        this.fly = new FlyNoWay();
        this.quack = new MuteQuackBehavior();
    }

    @Override
    public void display() {
        System.out.println("I'm a decoy duck!");
    }
}
