package ch.framework.collision;

public abstract class Shape {

    protected double cx, cy, rotation;
    protected Type type;

    public Shape(double cx, double cy, double rotation) {
        setLocation(cx, cy, rotation);
    }

    public abstract void setLocation(double cx, double cy, double rotation);

    public double getCenterX() {
        return cx;
    }

    public void setCenterX(double cx) {
        this.cx = cx;
    }

    public double getCenterY() {
        return cy;
    }

    public void setCenterY(double cy) {
        this.cy = cy;
    }

    public double getRotation() {
        return rotation;
    }

    public void setRotation(double rotation) {
        this.rotation = rotation;
    }

    public Type getType() {
        return type;
    }

    public enum Type {
        Circle, Rectangle, Polygon;
    }

}
