package KindOfDucks;

import DanceStrategy.MinuetBehavior;
import DanceStrategy.WaltzBehavior;
import FlyStrategy.FlyWithWings;
import QuackStrategy.QuackBehavior;

public class MallardDuck extends Duck{

    public MallardDuck() {
        this.fly = new FlyWithWings();
        this.dance = new WaltzBehavior();
        this.quack = new QuackBehavior();
    }

    @Override
    public void display() {
        System.out.println("I'm a mallard duck!");
    }
}
