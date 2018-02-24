package ch.framework.collision;

/**
 * Contains code from Stackoverflow by bruce956
 * https://stackoverflow.com/questions/5650032/collision-detection-with-rotated-rectangles
 * (edited)
 */

public class Collision {

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

        return testRectangleToPoint(rectangle, new Point(circleCenterX, circleCenterY)) ||
                testCircleToSegment(new Circle(tx, ty, circleRadius), new Segment(cx - rectWidth / 2, cy + rectHeight / 2, cx + rectWidth / 2, cy + rectHeight / 2)) ||
                testCircleToSegment(new Circle(tx, ty, circleRadius), new Segment(cx + rectWidth / 2, cy + rectHeight / 2, cx + rectWidth / 2, cy - rectHeight / 2)) ||
                testCircleToSegment(new Circle(tx, ty, circleRadius), new Segment(cx + rectWidth / 2, cy - rectHeight / 2, cx - rectWidth / 2, cy - rectHeight / 2)) ||
                testCircleToSegment(new Circle(tx, ty, circleRadius), new Segment(cx - rectWidth / 2, cy - rectHeight / 2, cx - rectWidth / 2, cy + rectHeight / 2));
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

    public static boolean testRectangleToRectangle(Rectangle rectangle1, Rectangle rectangle2) { //TODO Test if it actually works

        for (Segment segment1 : rectangle1.getSegments()) {
            for (Segment segment2 : rectangle2.getSegments()) {
                if (testSegmentToSegment(segment1, segment2)) {
                    return true;
                }
            }
        }

        return false;
    }

    public static boolean testSegmentToSegment(Segment segment1, Segment segment2) { //TODO Test if it actually works

        Point cmp = new Point(segment2.getA().getX() - segment1.getA().getX(), segment2.getA().getY() - segment1.getA().getY());
        Point r = new Point(segment1.getB().getX() - segment1.getA().getX(), segment1.getB().getY() - segment1.getA().getY());
        Point s = new Point(segment2.getB().getX() - segment2.getA().getX(), segment2.getB().getY() - segment2.getA().getY());

        float cmpxr = (float) (cmp.getX() * r.getY() - cmp.getY() * r.getX());
        float cmpxs = (float) (cmp.getX() * s.getY() - cmp.getY() * s.getX());
        float rxs = (float) (r.getX() * s.getY() - r.getY() * s.getX());

        if (cmpxr == 0f) {
            // Lines are collinear, and so intersect if they have any overlap

            return ((segment2.getA().getX() - segment1.getA().getX() < 0f) != (segment2.getA().getX() - segment1.getB().getX() < 0f))
                    || ((segment2.getA().getY() - segment1.getA().getY() < 0f) != (segment2.getA().getY() - segment1.getB().getY() < 0f));
        }

        if (rxs == 0f)
            return false; // Lines are parallel.

        float rxsr = 1f / rxs;
        float t = cmpxs * rxsr;
        float u = cmpxr * rxsr;

        return (t >= 0f) && (t <= 1f) && (u >= 0f) && (u <= 1f);
    }

    public static boolean testCircleToPoint(Circle circle, Point point) {
        double r = circle.getRadius();
        double dx = point.getX() - circle.getCenterX();
        double dy = point.getY() - circle.getCenterY();
        double l = Math.hypot(dx, dy);

        return l < r;
    }
}

