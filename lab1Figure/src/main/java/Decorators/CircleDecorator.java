package Decorators;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class CircleDecorator extends ShapeDecorator{
    Ellipse2D ellipse2D;

    public CircleDecorator(Ellipse2D ellipse2D, ArrayList<Integer> points) {
        super(ellipse2D);
        this.ellipse2D = ellipse2D;
        this.points = points;
    }

    @Override
    public double getPerimeter() {
        return 2 * Math.PI * (ellipse2D.getHeight() / 2);
    }

    @Override
    public double getArea() {
        return Math.PI * Math.pow(ellipse2D.getHeight() / 2, 2);
    }

    @Override
    public void fill(Graphics g) {
        g.setColor(fillColor);
        g.fillOval(points.get(0), points.get(1), points.get(2), points.get(2));
    }

    @Override
    public void draw(Graphics g, int lineWidth) {
        g.setColor(drawColor);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(lineWidth));
        g.drawOval(points.get(0), points.get(1), points.get(2), points.get(2));
        g2d.setStroke(new BasicStroke(1));
    }

    @Override
    public void save(File file, FileWriter writer, DecimalFormat formatter) throws IOException {
        writer.write("CIRCLE: P = " + formatter.format(getPerimeter()) + "; S = " +
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
            return mouseY > shapeY && mouseY < shapeY + points.get(2);
        }

        return false;
    }

}
