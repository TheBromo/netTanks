package ch.tanks;

import javafx.scene.canvas.GraphicsContext;

public class PickUp {

    private float x, y, radius;
    private PickUpType type;

    public PickUp(float x, float y, PickUpType type) {
        this.x = x;
        this.y = y;
        this.type = type;
    }

    public void update(GraphicsContext gc) {
        type.render(gc);
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

    public PickUpType getType() {
        return type;
    }
}

enum PickUpType {

    BULLETTYPE();

    public void render(GraphicsContext gc) {

    }
}
