package ch.framework.gameobjects;

import ch.framework.collision.Bounds;
import javafx.scene.canvas.GraphicsContext;

public abstract class GameObject { //TODO

    protected float x, y;
    protected Bounds bounds;
    protected Type type;

    public abstract void update(GraphicsContext gc);

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

    public Bounds getBounds() {
        return bounds;
    }

    public Type getType() {
        return type;
    }
}
