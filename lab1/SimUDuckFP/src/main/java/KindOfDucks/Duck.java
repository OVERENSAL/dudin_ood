package KindOfDucks;

import javax.swing.*;

public class Duck {

    Action fly;
    Action dance;
    Action quack;
    
    public Duck(Action fly, Action dance, Action quack) {
        this.fly = fly;
        this.dance = dance;
        this.quack = quack;
    }

    public void fly() {
        this.fly();
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
