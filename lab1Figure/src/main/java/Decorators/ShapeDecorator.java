package Decorators;

import java.awt.*;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class ShapeDecorator implements Shape {
    Shape shape;
    private static final Color COLOR_1 = Color.white;
    private static final Color COLOR_2 = Color.black;
    protected static final Paint GRADIENT_PAINT = new GradientPaint(0, 0, COLOR_1, 2, 2, COLOR_2, true);
    protected int additional = 10;
    protected boolean select;
    protected ArrayList<Integer> points;

    public ShapeDecorator(Shape shape) {
        this.shape = shape;
    }

    public double getPerimeter() {
        return 0;
    }

    public double getArea() {
        return 0;
    }

    public void draw(Graphics g) { }

    public void save(File file, FileWriter writer, DecimalFormat formatter) throws IOException { }

    public void drawFrame(Graphics g) { }

    public void setSelect(boolean select) {
        this.select = select;
    }

    public boolean getSelect() {
        return select;
    }

    public void update(Point currPt, Point prevPt){ }

    @Override
    public Rectangle getBounds() {
        return shape.getBounds();
    }

    @Override
    public Rectangle2D getBounds2D() {
        return shape.getBounds2D();
    }

    @Override
    public boolean contains(double x, double y) {
        return shape.contains(x, y);
    }

    @Override
    public boolean contains(Point2D p) {
        return shape.contains(p);
    }

    @Override
    public boolean intersects(double x, double y, double w, double h) {
        return shape.intersects(x, y, w, h);
    }

    @Override
    public boolean intersects(Rectangle2D r) {
        return shape.intersects(r);
    }

    @Override
    public boolean contains(double x, double y, double w, double h) {
        return shape.intersects(x, y, w, h);
    }

    @Override
    public boolean contains(Rectangle2D r) {
        return shape.contains(r);
    }

    @Override
    public PathIterator getPathIterator(AffineTransform at) {
        return shape.getPathIterator(at);
    }

    @Override
    public PathIterator getPathIterator(AffineTransform at, double flatness) {
        return shape.getPathIterator(at);
    }
}
