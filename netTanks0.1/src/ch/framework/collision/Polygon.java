package ch.framework.collision;

public class Polygon { //TODO

    private Point[] points;

    public Polygon(Point... points) {
        this.points = points;
    }

    public Point[] getPoints() {
        return points;
    }

    public Segment[] getSegments() {
        Segment[] segments = new Segment[points.length];

        for (int i = 0; i < points.length; i++) {
            if (i != points.length) {
                Segment segment = new Segment(points[i], points[i + 1]);
                segments[i] = segment;
            } else {
                Segment segment = new Segment(points[i], points[0]);
                segments[i] = segment;
            }
        }

        return segments;
    }
}
