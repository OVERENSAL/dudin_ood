import ColorStates.BlackColorState;
import ColorStates.BlueColorState;
import ColorStates.RedColorState;
import Command.Invoker;
import Decorators.ShapeDecorator;
import ColorStates.ColorState;
import MouseStates.DragAndDropState;
import MouseStates.FillState;
import MouseStates.MouseState;
import SizeStates.FatState;
import SizeStates.SizeState;
import SizeStates.TinyState;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class DragPanel extends JPanel {
    HashMap<ShapeDecorator, Integer> shapes = new HashMap<>();
    ButtonListener listener = new ButtonListener();
    private final ArrayList<JButton> buttons = new ArrayList<>();
    Point prevPt, currPt;
    static boolean shift, ctrl, u, g;
    private static MouseState mouseState = new DragAndDropState();
    private static SizeState sizeState = new TinyState();
    private static ColorState colorState = new BlackColorState();
    private static ColorState outlineColorState = new BlackColorState();
    private final Invoker invoker = new Invoker(shapes);

    DragPanel() {
        setPreferredSize(new Dimension(700, 600));
        setFocusable(true);
        addMouseListener(new ClickListener());
        addMouseMotionListener(new DragListener());
        addKeyListener(new KeyListener());
        createButtons();
        for (JButton button : buttons) {
            button.setBackground(Color.white);
            button.setBorder(BorderFactory.createEtchedBorder());
            button.addActionListener(listener);
            this.add(button);
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        for(ShapeDecorator shapeDecorator: shapes.keySet()) {
            shapeDecorator.fill(g);
            shapeDecorator.draw(g, shapeDecorator.getLineWidth());
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
            mouseState.clickObject(shape, colorState, sizeState, outlineColorState);

        }
        repaint();
    }

    public void toGroupShapes() {
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

    public void unGroupShapes() {
        if (ctrl && u) {
            for(Map.Entry<ShapeDecorator, Integer> shapeDecorator: shapes.entrySet()) {
                if (shapeDecorator.getKey().getSelect()) {
                    shapeDecorator.setValue(getFreeIndex());
                }
            }
        }
    }

    private int getFreeIndex() {
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

    private class KeyListener extends KeyAdapter {

        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case (KeyEvent.VK_SHIFT) -> shift = true;
                case (KeyEvent.VK_CONTROL) -> ctrl = true;
                case (KeyEvent.VK_G) -> g = true;
                case (KeyEvent.VK_U) -> u = true;
            }
            toGroupShapes();
            unGroupShapes();
        }

        public void keyReleased(KeyEvent e) {
            switch (e.getKeyCode()) {
                case (KeyEvent.VK_SHIFT):
                    shift = false;
                case (KeyEvent.VK_CONTROL):
                    ctrl = false;
                case (KeyEvent.VK_G):
                    g = false;
                case (KeyEvent.VK_U):
                    u = false;
            }
        }
    }

    private class ButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            requestFocus();
            switch (((JButton) e.getSource()).getActionCommand()) {
                case ("circle") -> invoker.createDefaultCircle();
                case ("triangle") -> invoker.createDefaultTriangle();
                case ("rectangle") -> invoker.createDefaultRectangle();
                case ("red") -> {
                    DragPanel.colorState = new RedColorState();
                    DragPanel.mouseState.clickFillColor(colorState, getShapeDecorator());
                }
                case ("blue") -> {
                    DragPanel.colorState = new BlueColorState();
                    DragPanel.mouseState.clickFillColor(colorState, getShapeDecorator());
                }
                case ("black") -> {
                    DragPanel.colorState = new BlackColorState();
                    DragPanel.mouseState.clickFillColor(colorState, getShapeDecorator());
                }
                case ("tinyLine") -> {
                    DragPanel.sizeState = new TinyState();
                    DragPanel.mouseState.clickSize(sizeState, getShapeDecorator());
                }
                case ("fatLine") -> {
                    DragPanel.sizeState = new FatState();
                    DragPanel.mouseState.clickSize(sizeState, getShapeDecorator());
                }
                case ("redLine") -> {
                    DragPanel.outlineColorState = new RedColorState();
                    DragPanel.mouseState.clickDrawColor(outlineColorState, getShapeDecorator());
                }
                case ("blueLine") -> {
                    DragPanel.outlineColorState = new BlueColorState();
                    DragPanel.mouseState.clickDrawColor(outlineColorState, getShapeDecorator());
                }
                case ("blackLine") -> {
                    DragPanel.outlineColorState = new BlackColorState();
                    DragPanel.mouseState.clickDrawColor(outlineColorState, getShapeDecorator());
                }
                case ("mouse") -> DragPanel.mouseState = new DragAndDropState();
                case ("fill") -> DragPanel.mouseState = new FillState();
            }
            repaint();
        }
    }

    private ShapeDecorator getShapeDecorator() {
        for(Map.Entry<ShapeDecorator, Integer> shapeDecorator: shapes.entrySet()) {
            if (shapeDecorator.getKey().getSelect()) {
                return shapeDecorator.getKey();
            }
        }
        return null;
    }

    private void createButtons() {
        JButton circleButton = new JButton(new ImageIcon("D:\\ThirdCourse\\OOD\\lab1Figure\\src\\main\\resources\\circle.png"));
        circleButton.setActionCommand("circle");
        JButton rectButton = new JButton(new ImageIcon("D:\\ThirdCourse\\OOD\\lab1Figure\\src\\main\\resources\\rectangle.png"));
        rectButton.setActionCommand("rectangle");
        JButton triButton = new JButton(new ImageIcon("D:\\ThirdCourse\\OOD\\lab1Figure\\src\\main\\resources\\triangle.png"));
        triButton.setActionCommand("triangle");
        JButton redButton = new JButton(new ImageIcon("D:\\ThirdCourse\\OOD\\lab1Figure\\src\\main\\resources\\red.png"));
        redButton.setActionCommand("red");
        JButton blueButton = new JButton(new ImageIcon("D:\\ThirdCourse\\OOD\\lab1Figure\\src\\main\\resources\\blue.png"));
        blueButton.setActionCommand("blue");
        JButton blackButton = new JButton(new ImageIcon("D:\\ThirdCourse\\OOD\\lab1Figure\\src\\main\\resources\\black.png"));
        blackButton.setActionCommand("black");
        JButton tinyLineButton = new JButton(new ImageIcon("D:\\ThirdCourse\\OOD\\lab1Figure\\src\\main\\resources\\tinyLine.png"));
        tinyLineButton.setActionCommand("tinyLine");
        JButton fatLineButton = new JButton(new ImageIcon("D:\\ThirdCourse\\OOD\\lab1Figure\\src\\main\\resources\\fatLine.png"));
        fatLineButton.setActionCommand("fatLine");
        JButton redLineButton = new JButton(new ImageIcon("D:\\ThirdCourse\\OOD\\lab1Figure\\src\\main\\resources\\redLine.png"));
        redLineButton.setActionCommand("redLine");
        JButton blueLineButton = new JButton(new ImageIcon("D:\\ThirdCourse\\OOD\\lab1Figure\\src\\main\\resources\\blueLine.png"));
        blueLineButton.setActionCommand("blueLine");
        JButton blackLineButton = new JButton(new ImageIcon("D:\\ThirdCourse\\OOD\\lab1Figure\\src\\main\\resources\\blackLine.png"));
        blackLineButton.setActionCommand("blackLine");
        JButton mouseButton = new JButton(new ImageIcon("D:\\ThirdCourse\\OOD\\lab1Figure\\src\\main\\resources\\mouse.png"));
        mouseButton.setActionCommand("mouse");
        JButton fillButton = new JButton(new ImageIcon("D:\\ThirdCourse\\OOD\\lab1Figure\\src\\main\\resources\\fill.png"));
        fillButton.setActionCommand("fill");
        buttons.add(circleButton);
        buttons.add(rectButton);
        buttons.add(triButton);
        buttons.add(redButton);
        buttons.add(blueButton);
        buttons.add(blackButton);
        buttons.add(tinyLineButton);
        buttons.add(fatLineButton);
        buttons.add(redLineButton);
        buttons.add(blueLineButton);
        buttons.add(blackLineButton);
        buttons.add(mouseButton);
        buttons.add(fillButton);
    }
}
