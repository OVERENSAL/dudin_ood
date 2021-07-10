package KindOfDucks;

import DanceStrategy.Dance;
import FlyStrategy.Fly;
import QuackStrategy.Quack;

public class Duck {
    Fly fly;
    Quack quack;
    Dance dance;

    public void fly() {
        fly.fly();
    }

    public void quack() {
        quack.quack();
    }

    public void dance() {
        dance.dance();
    }

    public void swim() {
        System.out.println("I'm swimming!");
    }

    public void setFlyBehavior(Fly fly) {
        this.fly = fly;
    }

    public void display() {}
}

