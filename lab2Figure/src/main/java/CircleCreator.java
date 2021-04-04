public class CircleCreator {
    private static CircleCreator circleCreator;
    private CircleCreator(){};
    public static CircleCreator getCircleCreator() {
        if (circleCreator == null) {
            circleCreator = new CircleCreator();
        }

        return circleCreator;
    }

}
