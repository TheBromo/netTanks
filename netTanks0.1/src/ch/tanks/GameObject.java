package ch.tanks;

import javafx.scene.canvas.GraphicsContext;

public abstract class GameObject {

    protected float x, y, radius;
    // Velocity, angle

    public GameObject(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void update(GraphicsContext gc) {

    }

    public void getBounds() {

    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }
}
