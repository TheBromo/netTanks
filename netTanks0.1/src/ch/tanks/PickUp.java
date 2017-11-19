package ch.tanks;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class PickUp {

    private float x, y, radius;
    private int time;

    public PickUp(float x, float y) {
        this.x = x;
        this.y = y;
        this.radius = 32;

        time = 60 * 10;
    }

    public void update(GraphicsContext gc) {
        gc.setFill(Color.WHITE);
        gc.fillOval(x - radius, y - radius, radius, radius);
        time--;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public void setTime(int timeInSeconds) {
        this.time = 60 * timeInSeconds;
    }

    public int getTime() {
        return time;
    }

}