package ch.framework.collision;

public class Rectangle extends Shape {

    private double width, height;
    private Point a, b, c, d;

    public Rectangle(double cx, double cy, double width, double height, double rotation) {
        super(cx, cy, rotation);
        this.type = Type.Rectangle;

        this.width = width;
        this.height = height;
        setLocation(cx, cy, rotation);
    }

    public void setLocation(double cx, double cy, double rotation) {
        this.cx = cx;
        this.cy = cy;
        this.rotation = rotation;


        a = setPoints(cx - (width / 2), cy - (height / 2));
        b = setPoints(cx + (width / 2), cy - (height / 2));
        c = setPoints(cx + (width / 2), cy + (height / 2));
        d = setPoints(cx - (width / 2), cy + (height / 2));
    }

    private Point setPoints(double x, double y) {
        Point point;

        double tempX = x - cx;
        double tempY = y - cy;

        double rotatedX = tempX * Math.cos(Math.toRadians(rotation)) - tempY * Math.sin(Math.toRadians(rotation));
        double rotatedY = tempX * Math.sin(Math.toRadians(rotation)) + tempY * Math.cos(Math.toRadians(rotation));

        point = new Point(rotatedX + cx, rotatedY + cy);

        return point;
    }

    public Segment[] getSegments() {
        Segment[] segments = {
                new Segment(a, b),
                new Segment(b, c),
                new Segment(c, d),
                new Segment(d, a)};

        return segments;
    }

    public Point[] getPoints() {
        Point[] points = {a, b, c, d};
        return points;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }
}
