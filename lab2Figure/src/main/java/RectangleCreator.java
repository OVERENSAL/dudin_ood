public class RectangleCreator {
    private static RectangleCreator rectangleCreator;
    private RectangleCreator(){};
    public static RectangleCreator getRectangleCreator() {
        if (rectangleCreator == null) {
            rectangleCreator = new RectangleCreator();
        }

        return rectangleCreator;
    }
}
