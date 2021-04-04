import Creators.ShapeDecorator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class DragPanel extends JPanel {
    static HashMap<ShapeDecorator, Integer> shapes = Application.getApplication().shapes;
    Point prevPt, currPt;
    static boolean shift, ctrl, u, g;

    DragPanel() {
        Application.getApplication().init(this);
        setPreferredSize(new Dimension(600, 600));
        setFocusable(true);
        addMouseListener(new ClickListener());
        addMouseMotionListener(new DragListener());
        addKeyListener(new KeyListener());
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        for(ShapeDecorator shapeDecorator: shapes.keySet()) {
            shapeDecorator.draw(g);
            if (shapeDecorator.getSelect()) {
                shapeDecorator.drawFrame(g);
            }
        }
    }

    public void setShapes(ArrayList<ShapeDecorator> shapes) {
        int i = 0;
        for(ShapeDecorator shapeDecorator: shapes) {
            this.shapes.put(shapeDecorator, i);
            i++;
        }
    }

    private void addVisual(ShapeDecorator shape) {
        if (shape.contains(prevPt) && shift) {
            shape.setSelect(true);
        } else if (shape.contains(prevPt)) {
            for(ShapeDecorator shapeDecorator: shapes.keySet()) {
                shapeDecorator.setSelect(false);
            }
            shape.setSelect(true);
        }
        repaint();
    }

    public static void toGroupShapes() {
        if (ctrl && g) {
            int groupMemorize = -1;
            for(Map.Entry<ShapeDecorator, Integer> shapeDecorator: shapes.entrySet()) {
                if (shapeDecorator.getKey().getSelect()) {
                    if (groupMemorize > -1) {
                        shapeDecorator.setValue(groupMemorize);
                    } else {
                        groupMemorize = shapeDecorator.getValue();
                    }
                }
            }
        }
    }

    public static void unGroupShapes() {
        if (ctrl && u) {
            for(Map.Entry<ShapeDecorator, Integer> shapeDecorator: shapes.entrySet()) {
                if (shapeDecorator.getKey().getSelect()) {
                    shapeDecorator.setValue(getFreeIndex());
                }
            }
        }
    }

    private static int getFreeIndex() {
        int i = 0, memorize;
        ArrayList<Integer> indexList = new ArrayList<>(shapes.values());
        boolean loop = true;
        while(loop) {
            memorize = i;
            for(Integer index: indexList) {
                if (index == i) {
                    i++;
                    break;
                }
            }
            if (memorize == i) {
                loop = false;
            }
        }

        return i;
    }

    private class ClickListener extends MouseAdapter {

        public void mousePressed(MouseEvent e) {
            prevPt = e.getPoint();

            for(ShapeDecorator shapeDecorator: shapes.keySet()) {
                System.out.println(shift);
                addVisual(shapeDecorator);
            }
        }
    }

    private class DragListener extends MouseMotionAdapter {

        public void mouseDragged(MouseEvent e) {
            currPt = e.getPoint();

            for(Map.Entry<ShapeDecorator, Integer> shapeDecorator: shapes.entrySet()) {
                if (shapeDecorator.getKey().getSelect() && shapeDecorator.getKey().contains(currPt)) {
                    for(Map.Entry<ShapeDecorator, Integer> shape: shapes.entrySet()) { //groupDrag
                        if (shape.getValue().equals(shapeDecorator.getValue()) && !shape.getKey().equals(shapeDecorator.getKey())) {
                            shape.getKey().update(currPt, prevPt);
                        }
                    }
                    shapeDecorator.getKey().update(currPt, prevPt);
                    prevPt = currPt;
                    repaint();
                }
            }
        }
    }

//    private class KeyListener extends KeyAdapter {
//
//        public void keyPressed(KeyEvent e) {
//            switch (e.getKeyCode()) {
//                case (KeyEvent.VK_SHIFT) -> shift = true;
//                case (KeyEvent.VK_CONTROL) -> ctrl = true;
//                case (KeyEvent.VK_G) -> g = true;
//                case (KeyEvent.VK_U) -> u = true;
//            }
//            toGroupShapes();
//            unGroupShapes();
//        }
//
//        public void keyReleased(KeyEvent e) {
//            switch (e.getKeyCode()) {
//                case (KeyEvent.VK_SHIFT):
//                    shift = false;
//                case (KeyEvent.VK_CONTROL):
//                    ctrl = false;
//                case (KeyEvent.VK_G):
//                    g = false;
//                case (KeyEvent.VK_U):
//                    u = false;
//            }
//        }
//    }
}
