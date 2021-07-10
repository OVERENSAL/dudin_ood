import Decorators.ShapeDecorator;

import java.io.File;
import java.util.ArrayList;

public class Application {
    private static Application application;
    private Application() { }
    public static synchronized Application getApplication() {
        if (application == null) {
            application = new Application();
        }

        return application;
    }

    public void run(String[] args) {
        ArrayList<ShapeDecorator> shapes = new ArrayList<>();
        drawShape(processShape(shapes, args));
    }

    private ArrayList<ShapeDecorator> processShape(ArrayList<ShapeDecorator> shapes, String[] args) {
        FileProcess fileProcess = new FileProcess();
        shapes = fileProcess.readShapes(shapes, args[0]);
        fileProcess.saveShapes(shapes, new File(args[1]));

        return shapes;
    }

    private void drawShape(ArrayList<ShapeDecorator> shapes) {
        Frame frame = new Frame();
        frame.panel.setShapes(shapes);
    }
}

