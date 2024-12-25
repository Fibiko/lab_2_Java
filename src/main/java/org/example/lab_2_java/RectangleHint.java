package org.example.lab_2_java;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class RectangleHint {
    private final double x1, y1, x2, y2;
    private final String text;

    public RectangleHint(double x1, double y1, double x2, double y2, String text) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.text = text;
    }

    public void draw(GraphicsContext gc) {
        gc.strokeRect(x1, y1, x2 - x1, y2 - y1);
        gc.setFill(Color.LIGHTGRAY);
        gc.fillRect(x1, y1, x2 - x1, y2 - y1);

        gc.setFont(javafx.scene.text.Font.font(10));

        gc.setFill(Color.BLACK);
        gc.fillText(text, (x1+5), y1 + (y2 - y1) / 2);
    }
}
