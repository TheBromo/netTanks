package ch.tanks;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Code originally from Stackoverflow by bruce956
 * https://stackoverflow.com/questions/5650032/collision-detection-with-rotated-rectangles
 * (edited)
 */

public class Collision {

    /**
     * Rectangle To Point.
     */
    public static boolean testRectangleToPoint(double rectWidth, double rectHeight, double rectRotation, double rectCenterX, double rectCenterY, double pointX, double pointY) {
        if (rectRotation == 0)   // Higher Efficiency for Rectangles with 0 rotation.
            return Math.abs(rectCenterX - pointX) < rectWidth / 2 && Math.abs(rectCenterY - pointY) < rectHeight / 2;

        double tx = Math.cos(rectRotation) * pointX - Math.sin(rectRotation) * pointY;
        double ty = Math.cos(rectRotation) * pointY + Math.sin(rectRotation) * pointX;

        double cx = Math.cos(rectRotation) * rectCenterX - Math.sin(rectRotation) * rectCenterY;
        double cy = Math.cos(rectRotation) * rectCenterY + Math.sin(rectRotation) * rectCenterX;

        return Math.abs(cx - tx) < rectWidth / 2 && Math.abs(cy - ty) < rectHeight / 2;
    }

    /**
     * Circle To Segment.
     */
    public static boolean testCircleToSegment(double circleCenterX, double circleCenterY, double circleRadius, double lineAX, double lineAY, double lineBX, double lineBY) {
        double lineSize = Math.sqrt(Math.pow(lineAX - lineBX, 2) + Math.pow(lineAY - lineBY, 2));
        double distance;

        if (lineSize == 0) {
            distance = Math.sqrt(Math.pow(circleCenterX - lineAX, 2) + Math.pow(circleCenterY - lineAY, 2));
            return distance < circleRadius;
        }

        double u = ((circleCenterX - lineAX) * (lineBX - lineAX) + (circleCenterY - lineAY) * (lineBY - lineAY)) / (lineSize * lineSize);

        if (u < 0) {
            distance = Math.sqrt(Math.pow(circleCenterX - lineAX, 2) + Math.pow(circleCenterY - lineAY, 2));
        } else if (u > 1) {
            distance = Math.sqrt(Math.pow(circleCenterX - lineBX, 2) + Math.pow(circleCenterY - lineBY, 2));
        } else {
            double ix = lineAX + u * (lineBX - lineAX);
            double iy = lineAY + u * (lineBY - lineAY);
            distance = Math.sqrt(Math.pow(circleCenterX - ix, 2) + Math.pow(circleCenterY - iy, 2));
        }

        return distance < circleRadius;
    }

    public static boolean testCircleToSegment(Circle circle, Segment segment) {

        double circleCenterX = circle.getCenterX();
        double circleCenterY = circle.getCenterY();
        double circleRadius = circle.getRadius();
        double lineAX = segment.getA().getX();
        double lineAY = segment.getA().getY();
        double lineBX = segment.getB().getX();
        double lineBY = segment.getB().getY();

        double lineSize = Math.sqrt(Math.pow(lineAX - lineBX, 2) + Math.pow(lineAY - lineBY, 2));
        double distance;

        if (lineSize == 0) {
            distance = Math.sqrt(Math.pow(circleCenterX - lineAX, 2) + Math.pow(circleCenterY - lineAY, 2));
            return distance < circleRadius;
        }

        double u = ((circleCenterX - lineAX) * (lineBX - lineAX) + (circleCenterY - lineAY) * (lineBY - lineAY)) / (lineSize * lineSize);

        if (u < 0) {
            distance = Math.sqrt(Math.pow(circleCenterX - lineAX, 2) + Math.pow(circleCenterY - lineAY, 2));
        } else if (u > 1) {
            distance = Math.sqrt(Math.pow(circleCenterX - lineBX, 2) + Math.pow(circleCenterY - lineBY, 2));
        } else {
            double ix = lineAX + u * (lineBX - lineAX);
            double iy = lineAY + u * (lineBY - lineAY);
            distance = Math.sqrt(Math.pow(circleCenterX - ix, 2) + Math.pow(circleCenterY - iy, 2));
        }

        return distance < circleRadius;
    }

    /**
     * Rectangle To Circle.
     */
    public static boolean testRectangleToCircle(double rectWidth, double rectHeight, double rectRotation, double rectCenterX, double rectCenterY, double circleCenterX, double circleCenterY, double circleRadius) {
        double tx, ty, cx, cy;

        if (rectRotation == 0) { // Higher Efficiency for Rectangles with 0 rotation.
            tx = circleCenterX;
            ty = circleCenterY;

            cx = rectCenterX;
            cy = rectCenterY;
        } else {
            tx = Math.cos(rectRotation) * circleCenterX - Math.sin(rectRotation) * circleCenterY;
            ty = Math.cos(rectRotation) * circleCenterY + Math.sin(rectRotation) * circleCenterX;

            cx = Math.cos(rectRotation) * rectCenterX - Math.sin(rectRotation) * rectCenterY;
            cy = Math.cos(rectRotation) * rectCenterY + Math.sin(rectRotation) * rectCenterX;
        }

        return testRectangleToPoint(rectWidth, rectHeight, rectRotation, rectCenterX, rectCenterY, circleCenterX, circleCenterY) ||
                testCircleToSegment(tx, ty, circleRadius, cx - rectWidth / 2, cy + rectHeight / 2, cx + rectWidth / 2, cy + rectHeight / 2) ||
                testCircleToSegment(tx, ty, circleRadius, cx + rectWidth / 2, cy + rectHeight / 2, cx + rectWidth / 2, cy - rectHeight / 2) ||
                testCircleToSegment(tx, ty, circleRadius, cx + rectWidth / 2, cy - rectHeight / 2, cx - rectWidth / 2, cy - rectHeight / 2) ||
                testCircleToSegment(tx, ty, circleRadius, cx - rectWidth / 2, cy - rectHeight / 2, cx - rectWidth / 2, cy + rectHeight / 2);
    }

    public static boolean testCircleToRectangle(Circle circle, Rectangle rectangle) {
        double rectWidth = rectangle.getWidth();
        double rectHeight = rectangle.getHeight();
        double rectRotation = rectangle.getRotation();
        double rectCenterX = rectangle.getCenterX();
        double rectCenterY = rectangle.getCenterY();
        double circleCenterX = circle.getCenterX();
        double circleCenterY = circle.getCenterY();
        double circleRadius = circle.getRadius();

        double tx, ty, cx, cy;

        if (rectRotation == 0) { // Higher Efficiency for Rectangles with 0 rotation.
            tx = circleCenterX;
            ty = circleCenterY;

            cx = rectCenterX;
            cy = rectCenterY;
        } else {
            tx = Math.cos(rectRotation) * circleCenterX - Math.sin(rectRotation) * circleCenterY;
            ty = Math.cos(rectRotation) * circleCenterY + Math.sin(rectRotation) * circleCenterX;

            cx = Math.cos(rectRotation) * rectCenterX - Math.sin(rectRotation) * rectCenterY;
            cy = Math.cos(rectRotation) * rectCenterY + Math.sin(rectRotation) * rectCenterX;
        }

        return testRectangleToPoint(rectWidth, rectHeight, rectRotation, rectCenterX, rectCenterY, circleCenterX, circleCenterY) ||
                testCircleToSegment(tx, ty, circleRadius, cx - rectWidth / 2, cy + rectHeight / 2, cx + rectWidth / 2, cy + rectHeight / 2) ||
                testCircleToSegment(tx, ty, circleRadius, cx + rectWidth / 2, cy + rectHeight / 2, cx + rectWidth / 2, cy - rectHeight / 2) ||
                testCircleToSegment(tx, ty, circleRadius, cx + rectWidth / 2, cy - rectHeight / 2, cx - rectWidth / 2, cy - rectHeight / 2) ||
                testCircleToSegment(tx, ty, circleRadius, cx - rectWidth / 2, cy - rectHeight / 2, cx - rectWidth / 2, cy + rectHeight / 2);
    }

    public static boolean testRectangleToPoint(Rectangle rectangle, Point point) {
        double rectWidth = rectangle.getWidth();
        double rectHeight = rectangle.getHeight();
        double rectRotation = rectangle.getRotation();
        double rectCenterX = rectangle.getCenterX();
        double rectCenterY = rectangle.getCenterY();
        double pointX = point.getX();
        double pointY = point.getY();

        if (rectRotation == 0)   // Higher Efficiency for Rectangles with 0 rotation.
            return Math.abs(rectCenterX - pointX) < rectWidth / 2 && Math.abs(rectCenterY - pointY) < rectHeight / 2;

        double tx = Math.cos(rectRotation) * pointX - Math.sin(rectRotation) * pointY;
        double ty = Math.cos(rectRotation) * pointY + Math.sin(rectRotation) * pointX;

        double cx = Math.cos(rectRotation) * rectCenterX - Math.sin(rectRotation) * rectCenterY;
        double cy = Math.cos(rectRotation) * rectCenterY + Math.sin(rectRotation) * rectCenterX;

        return Math.abs(cx - tx) < rectWidth / 2 && Math.abs(cy - ty) < rectHeight / 2;
    }

    public static boolean testCircleToCircle(Circle circle1, Circle circle2) {
        double c1x = circle1.getCenterX();
        double c1y = circle1.getCenterY();
        double c1r = circle1.getRadius();
        double c2x = circle2.getCenterX();
        double c2y = circle2.getCenterY();
        double c2r = circle2.getRadius();

        double dy = c2y - c1y;
        double dx = c2x - c1x;

        double distance = Math.sqrt(Math.pow(dy, 2) + Math.pow(dx, 2));

        return distance - (c1r + c2r) < 0;
    }
}

class Rectangle {

    private double centerX, centerY, width, height, rotation;
    private Point a, b, c, d;

    public Rectangle(double centerX, double centerY, double width, double height, double rotation) {
        setLocation(centerX, centerY, width, height, rotation);
    }

    public void setLocation(double centerX, double centerY, double width, double height, double rotation) {
        this.centerX = centerX;
        this.centerY = centerY;
        this.width = width;
        this.height = height;
        this.rotation = rotation;


        a = setPoints(centerX - (width / 2), centerY - (height / 2));
        b = setPoints(centerX + (width / 2), centerY - (height / 2));
        c = setPoints(centerX + (width / 2), centerY + (height / 2));
        d = setPoints(centerX - (width / 2), centerY + (height / 2));
    }

    private Point setPoints(double x, double y) {
        Point point;

        double tempX = x - centerX;
        double tempY = y - centerY;

        double rotatedX = tempX * Math.cos(Math.toRadians(rotation)) - tempY * Math.sin(Math.toRadians(rotation));
        double rotatedY = tempX * Math.sin(Math.toRadians(rotation)) + tempY * Math.cos(Math.toRadians(rotation));

        point = new Point(rotatedX + centerX, rotatedY + centerY);

        return point;
    }

    public ArrayList<Segment> getSegments() {
        ArrayList<Segment> segments = new ArrayList<>();

        Segment segA = new Segment(a, b);
        Segment segB = new Segment(b, c);
        Segment segC = new Segment(c, d);
        Segment segD = new Segment(d, a);

        segments.add(segA);
        segments.add(segB);
        segments.add(segC);
        segments.add(segD);

        return segments;
    }

    public ArrayList<Point> getPoints() {
        ArrayList<Point> points = new ArrayList<>();

        points.add(a);
        points.add(b);
        points.add(c);
        points.add(d);

        return points;
    }

    public double getCenterX() {
        return centerX;
    }

    public void setCenterX(double centerX) {
        this.centerX = centerX;
    }

    public double getCenterY() {
        return centerY;
    }

    public void setCenterY(double centerY) {
        this.centerY = centerY;
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

    public double getRotation() {
        return rotation;
    }

    public void setRotation(double rotation) {
        this.rotation = rotation;
    }
}

class Circle {

    private double centerX, centerY, radius;

    public Circle(double centerX, double centerY, double radius) {
        this.centerX = centerX;
        this.centerY = centerY;
        this.radius = radius;
    }

    public void setLocation(double centerX, double centerY, double radius) {
        this.centerX = centerX;
        this.centerY = centerY;
        this.radius = radius;
    }

    public double getCenterX() {
        return centerX;
    }

    public void setCenterX(double centerX) {
        this.centerX = centerX;
    }

    public double getCenterY() {
        return centerY;
    }

    public void setCenterY(double centerY) {
        this.centerY = centerY;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }
}

class Polygon { //TODO

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

class Point {

    private double x, y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }
}

class Segment {

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
