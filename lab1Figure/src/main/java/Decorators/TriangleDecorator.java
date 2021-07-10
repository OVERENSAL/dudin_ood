package Decorators;

import java.awt.*;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class TriangleDecorator extends ShapeDecorator{
    Path2D path2D;
    ArrayList<Integer> points;

    public TriangleDecorator(Path2D path2D, ArrayList<Integer> points) {
        super(path2D);
        this.path2D = path2D;
        this.points = points;
    }

    @Override
    public double getPerimeter() {
        return getSide(points.get(0), points.get(1), points.get(2), points.get(3)) +
                getSide(points.get(2), points.get(3), points.get(4), points.get(5)) +
                getSide(points.get(0), points.get(1), points.get(4), points.get(5));
    }

    @Override
    public double getArea() {
        double halfPerimeter = getPerimeter() / 2;

        return Math.sqrt(halfPerimeter *
                (halfPerimeter - getSide(points.get(0), points.get(1), points.get(2), points.get(3))) *
                (halfPerimeter - getSide(points.get(2), points.get(3), points.get(4), points.get(5))) *
                (halfPerimeter - getSide(points.get(0), points.get(1), points.get(4), points.get(5))));
    }

    @Override
    public void fill(Graphics g) {
        g.setColor(fillColor);
        int[] x = {points.get(0), points.get(2), points.get(4)};
        int[] y = {points.get(1), points.get(3), points.get(5)};
        g.fillPolygon(x, y, 3);
    }

    @Override
    public void draw(Graphics g, int lineWidth) {
        g.setColor(drawColor);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(lineWidth));
        int[] x = {points.get(0), points.get(2), points.get(4)};
        int[] y = {points.get(1), points.get(3), points.get(5)};
        g.drawPolygon(x, y, 3);
        g2d.setStroke(new BasicStroke(1));
    }

    private double getSide(int x1, int y1, int x2, int y2) {
        return Math.sqrt(Math.pow(Math.abs(x1 - x2), 2) + Math.pow(Math.abs(y1 - y2), 2));
    }

    @Override
    public void save(File file, FileWriter writer, DecimalFormat formatter) throws IOException {
        writer.write("TRIANGLE: P = " + formatter.format(getPerimeter()) + "; S = " +
                formatter.format(getArea()) + "\n");
    }

    @Override
    public void drawFrame(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setPaint(GRADIENT_PAINT);
        ArrayList<Integer> framePoints = getFramePoints();
        g.drawRoundRect(framePoints.get(0) - additional, framePoints.get(1) - additional,
                (framePoints.get(3) - framePoints.get(1)) + 2 * additional,
                (framePoints.get(2) - framePoints.get(0)) + 2 * additional, additional, additional);
        g2.setPaint(Color.BLACK);
    }

    @Override
    public boolean contains(Point2D p) {
        int x1 = points.get(0);
        int y1 = points.get(1);
        int x2 = points.get(2);
        int y2 = points.get(3);
        int x3 = points.get(4);
        int y3 = points.get(5);
        double frame1 = (x1 - p.getX()) * (y2 - y1) - (x2 - x1) * (y1 - p.getY());
        double frame2 = (x2 - p.getX()) * (y3 - y2) - (x3 - x2) * (y2 - p.getY());
        double frame3 = (x3 - p.getX()) * (y1 - y3) - (x1 - x3) * (y3 - p.getY());
        return (frame1 >= 0 && frame2 >= 0 && frame3 >= 0) || (frame1 <= 0 && frame2 <= 0 && frame3 <= 0);
    }

    @Override
    public void update(Point currPt, Point prevPt) {
        for (int i = 0; i < points.size(); i += 2) {
            points.set(i, points.get(i) + (currPt.x - prevPt.x));
            points.set(i + 1, points.get(i + 1) + (currPt.y - prevPt.y));
        }
    }

    private ArrayList<Integer> getFramePoints() {
        ArrayList<Integer> framePoints = new ArrayList<>();
        framePoints.add(getMinCoordinates(0));
        framePoints.add(getMinCoordinates(1));
        framePoints.add(getMaxCoordinates(0));
        framePoints.add(getMaxCoordinates(1));

        return framePoints;
    }

    private int getMinCoordinates(int startIndex) {
        int min = Integer.MAX_VALUE;
        for (int i = startIndex; i < points.size(); i += 2) {
            if (min > points.get(i)) {
                min = points.get(i);
            }
        }

        return min;
    }

    private int getMaxCoordinates(int startIndex) {
        int max = 0;
        for (int i = startIndex; i < points.size(); i += 2) {
            if (max < points.get(i)) {
                max = points.get(i);
            }
        }

        return max;
    }

}
