package ch.framework.coll;

import java.util.ArrayList;

public abstract class Shape {

    protected float x, y;
    protected Vector[] vectors;

    public abstract void setPosition(float x, float y);
    public abstract void setX(float x);
    public abstract void setY(float y);

    public Vector[] getVectors() {
        return vectors;
    }

    public Intersection[] getIntersections(Shape shape) {
        ArrayList<Intersection> intersections = new ArrayList<>();
        if (this instanceof Circle) {
            if (shape instanceof Circle) {

            } else {
                for (Vector v : shape.getVectors()) {
                    Intersection i = Intersection.getIntersection(vector, v);
                    if (i != null)
                        intersections.add(i);
                }
            }
        } else {
            for (Vector vector : vectors) {
                for (Vector v : shape.getVectors()) {
                    Intersection i = Intersection.getIntersection(vector, v);
                    if (i != null)
                        intersections.add(i);
                }
            }
        }
        return intersections.toArray(new Intersection[0]);
    }
}
