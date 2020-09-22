package KindOfDucks;

import DanceStrategy.NoDanceBehavior;
import FlyStrategy.FlyNoWay;
import QuackStrategy.QuackBehavior;

public class ModelDuck extends Duck{

    public ModelDuck() {
        this.dance = new NoDanceBehavior();
        this.fly = new FlyNoWay();
        this.quack = new QuackBehavior();
    }

    @Override
    public void display() {
        System.out.println("I'm a model duck!");
    }
}
