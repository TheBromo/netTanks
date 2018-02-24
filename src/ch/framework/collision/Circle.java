package ch.framework.collision;

public class Circle extends Shape {

    private double radius;

    public Circle(double cx, double cy, double radius) {
        super(cx, cy, 0);
        this.type = Type.Circle;
        this.radius = radius;
    }

    public void setLocation(double cx, double cy, double rotation) {
        this.cx = cx;
        this.cy = cy;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }
}

