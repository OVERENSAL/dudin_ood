public class TriangleCreator {
    private static TriangleCreator triangleCreator;
    private TriangleCreator(){};
    public static TriangleCreator getTriangleCreator() {
        if (triangleCreator == null) {
            triangleCreator = new TriangleCreator();
        }

        return triangleCreator;
    }
}
