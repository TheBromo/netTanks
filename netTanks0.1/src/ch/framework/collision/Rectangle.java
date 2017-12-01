package ch.framework.collision;

import java.util.ArrayList;

public class Rectangle {

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
