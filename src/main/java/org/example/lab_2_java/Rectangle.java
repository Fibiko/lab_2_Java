package org.example.lab_2_java;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Rectangle {
    private final double x1, y1, x2, y2;
    public int state;
    private boolean circleDrawn = false;

    public Rectangle(double x1, double y1, double x2, double y2, int state, boolean circleDrawn) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.state = state;
        this.circleDrawn = false;
    }

    public void draw(GraphicsContext gc) {
        gc.strokeRect(x1, y1, x2 - x1, y2 - y1);
    }

    public void drawCircle(GraphicsContext gc) {
        double centerX = (x1 + x2) / 2;
        double centerY = (y1 + y2) / 2;
        double radius = Math.min(x2 - x1, y2 - y1) / 4;

        gc.setFill(Color.BLACK);
        gc.fillOval(centerX - radius, centerY - radius, radius * 2, radius * 2);
    }

    public boolean contains(double x, double y) {
        return x >= x1 && x <= x2 && y >= y1 && y <= y2;
    }

    public double getX1() {
        return x1;
    }

    public double getY1() {
        return y1;
    }

    public void handleClick() {
        System.out.println("Rectangle clicked at (" + x1 + ", " + y1 + ")");
    }

    public boolean isCircleDrawn() {
        return circleDrawn;
    }

    // Установить флаг о том, что круг нарисован
    public void setCircleDrawn(boolean circleDrawn) {
        this.circleDrawn = circleDrawn;
    }
}
