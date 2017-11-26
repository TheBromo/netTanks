package ch.framework.collision;

public class Segment {

    private Point a, b;

    public Segment(double ax, double ay, double bx, double by) {
        this.a = new Point(ax, ay);
        this.b = new Point(bx, by);
    }

    public Segment(Point a, Point b) {
        this.a = a;
        this.b = b;
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

    public double getAngle() {
        double angle = Math.toDegrees(Math.atan2((b.getY() - a.getY()), (b.getX() - a.getX()))) + 90;

        if (angle < 0) {
            angle += 360;
        }

        return angle;
    }
}
