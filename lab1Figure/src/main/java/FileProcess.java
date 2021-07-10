import Decorators.ShapeDecorator;
import java.io.*;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class FileProcess {
    public static final String NON_CREATE = "This shape will not to be create. ";

    public ArrayList<ShapeDecorator> readShapes(ArrayList<ShapeDecorator> shapes, String file) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                createShape(line.split("\\s"), shapes);
            }

            return shapes;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void saveShapes(ArrayList<ShapeDecorator> shapes, File file) {
        try (FileWriter writer = new FileWriter(file)) {
            DecimalFormat formatter = new DecimalFormat("#.###");
            for (ShapeDecorator shape: shapes) {
                shape.save(file, writer, formatter);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createShape(String[] shape, ArrayList<ShapeDecorator> shapes) {
        ShapeFactory shapeFactory = ShapeFactory.createShapeBySpecialty(shape[0]);
        if (shapeFactory != null) {
            shapeFactory.createShape(shape, shapes);
        }
    }
}
