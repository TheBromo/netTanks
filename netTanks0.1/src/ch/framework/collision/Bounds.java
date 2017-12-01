package ch.framework.collision;

import java.util.ArrayList;

public class Bounds {

    private double centerX, centerY, rotation;

    private ArrayList<Circle> circles;
    private ArrayList<Rectangle> rectangles;
    //private ArrayList<Polygon> polygons;

    public Bounds(double centerX, double centerY) {
        circles = new ArrayList<>();
        rectangles = new ArrayList<>();
    }

    public void setLocation(double centerX, double centerY, double rotation) {
        this.centerX = centerX;
        this.centerY = centerY;
        this.rotation = rotation;

        for (Circle circle : circles) {
            Point center = setCenters(circle.getCenterX(), circle.getCenterY());
            circle.setLocation(center.getX(), center.getY(), circle.getRadius());
        }

        for (Rectangle rectangle : rectangles) {
            Point center = setCenters(rectangle.getCenterX(), rectangle.getCenterY());
            rectangle.setLocation(center.getX(), center.getY(), rectangle.getWidth(), rectangle.getHeight(), rotation);
        }
    }

    private Point setCenters(double x, double y) {
        Point point;

        double tempX = x - centerX;
        double tempY = y - centerY;

        double rotatedX = tempX * Math.cos(Math.toRadians(rotation)) - tempY * Math.sin(Math.toRadians(rotation));
        double rotatedY = tempX * Math.sin(Math.toRadians(rotation)) + tempY * Math.cos(Math.toRadians(rotation));

        point = new Point(rotatedX + centerX, rotatedY + centerY);

        return point;
    }

    public boolean intersects(Bounds bounds) {

        if (this.containsCircles()) {
            for (Circle c1 : circles) {

                if (bounds.containsCircles()) {
                    for (Circle c2 : bounds.circles) {
                        if (Collision.testCircleToCircle(c1, c2)) {
                            return true;
                        }
                    }
                }

                if (bounds.containsRectangles()) {
                    for (Rectangle r2 : bounds.rectangles) {
                        if (Collision.testCircleToRectangle(c1, r2)) {
                            return true;
                        }
                    }
                }
            }
        }

        if (this.containsRectangles()) {
            for (Rectangle r1 : rectangles) {

                if (bounds.containsCircles()) {
                    for (Circle c2 : bounds.circles) {
                        if (Collision.testCircleToRectangle(c2, r1)) {
                            return true;
                        }
                    }
                }

                if (bounds.containsRectangles()) {
                    for (Rectangle r2 : bounds.rectangles) {
                        if (Collision.testRectangleToRectangle(r1, r2)) {
                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }

//    public Segment[] getSegments() {
//        Segment[] segments;
//
//        for (Rectangle rectangle : rectangles) {
//            segments = new Segment[segments.size]
//        }
//    }

    public boolean containsCircles() {
        if (circles.size() > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean containsRectangles() {
        if (rectangles.size() > 0) {
            return true;
        } else {
            return false;
        }
    }

    public ArrayList<Circle> getCircles() {
        return circles;
    }

    public ArrayList<Rectangle> getRectangles() {
        return rectangles;
    }

    public void addCircle(Circle circle) {
        this.circles.add(circle);
    }

    public void addRectangle(Rectangle rectangle) {
        this.rectangles.add(rectangle);
    }
}
