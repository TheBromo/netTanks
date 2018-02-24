package ch.framework.gameobjects;

import ch.framework.collision.Point;
import ch.framework.collision.Shape;

public abstract class GameObject {

    protected float x, y;
    protected Shape bounds;

    public abstract void update();

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

    public Shape getBounds() {
        return bounds;
    }

    public Point getLocation() {
        return new Point(x, y);
    }
}
