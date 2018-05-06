package ch.framework.coll;

public class Vector {

    private Point a;
    private Point b;

    public Vector(Point a, Point b) {
        this.a = a;
        this.b = b;
    }

    public Vector(float ax, float ay, float bx, float by) {
        this.a = new Point(ax, ay);
        this.b = new Point(bx, by);
    }

    public Point getA() {
        return a;
    }

    public void setA(Point a) {
        this.a = a;
    }

    public Point getB() {
        return b;
    }

    public void setB(Point b) {
        this.b = b;
    }
}
