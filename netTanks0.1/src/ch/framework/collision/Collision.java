package ch.framework.collision;

import java.util.ArrayList;

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

