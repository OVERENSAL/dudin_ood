package Decorators;

import java.awt.*;
import java.awt.geom.Point2D;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class RectangleDecorator extends ShapeDecorator{
    Rectangle rectangle;
    ArrayList<Integer> points;

    public RectangleDecorator(Rectangle rectangle, ArrayList<Integer> points) {
        super(rectangle);
        this.rectangle = rectangle;
        this.points = points;
    }

    @Override
    public double getPerimeter() {
        return rectangle.getHeight() * 2 + rectangle.getWidth() * 2;
    }

    @Override
    public double getArea() {
        return rectangle.getHeight() * rectangle.getWidth();
    }

    @Override
    public void fill(Graphics g) {
        g.setColor(fillColor);
        g.fillRect(points.get(0), points.get(1), points.get(2), points.get(3));
    }

    @Override
    public void draw(Graphics g, int lineWidth) {
        g.setColor(drawColor);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(lineWidth));
        g.drawRect(points.get(0), points.get(1), points.get(2), points.get(3));
        g2d.setStroke(new BasicStroke(1));
    }

    @Override
    public void save(File file, FileWriter writer, DecimalFormat formatter) throws IOException {
        writer.write("RECTANGLE: P = " + formatter.format(getPerimeter()) + "; S = " +
                formatter.format(getArea()) + "\n");
    }

    @Override
    public void drawFrame(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setPaint(GRADIENT_PAINT);
        g.drawRoundRect(points.get(0) - additional, points.get(1) - additional,
                points.get(2) + 2 * additional, points.get(2) + 2 * additional, 10, 10);
        g2.setPaint(Color.BLACK);
    }

    @Override
    public void update(Point currPt, Point prevPt) {
        points.set(0, points.get(0) + (currPt.x - prevPt.x));
        points.set(1, points.get(1) + (currPt.y - prevPt.y));
    }

    @Override
    public boolean contains(Point2D p) {
        double mouseX = p.getX();
        double mouseY = p.getY();
        double shapeX = points.get(0);
        double shapeY = points.get(1);
        if (mouseX > shapeX && mouseX < shapeX + points.get(2)) {
            return mouseY > shapeY && mouseY < shapeY + points.get(3);
        }

        return false;
    }

}
