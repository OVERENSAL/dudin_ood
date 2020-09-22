import FlyStrategy.FlyWithWings;
import KindOfDucks.*;

public class Main {
    public static void main(String[] args) {
        MallardDuck mallardDuck = new MallardDuck();
        playWithDuck(mallardDuck);
        playWithDuck(mallardDuck);

        RedHeadDuck redHeadDuck = new RedHeadDuck();
        playWithDuck(redHeadDuck);

        RubberDuck rubberDuck = new RubberDuck();
        playWithDuck(rubberDuck);

        DecoyDuck decoyDuck = new DecoyDuck();
        playWithDuck(decoyDuck);

        ModelDuck modelDuck = new ModelDuck();
        playWithDuck(modelDuck);
        modelDuck.setFlyBehavior(new FlyWithWings());
        playWithDuck(modelDuck);
    }

    public static void drawDuck(Duck duck) {
        duck.display();
    }

    public static void playWithDuck(Duck duck) {
        drawDuck(duck);
        duck.quack();
        duck.fly();
        duck.dance();
        System.out.println();
    }
}
