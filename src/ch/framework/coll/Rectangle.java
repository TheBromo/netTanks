package ch.framework.coll;

public class Rectangle extends Shape {

    private float a, w, h;

    public Rectangle(float x, float y, float w, float h, float a) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.a = a;

        this.vectors = new Vector[4];
        this.vectors[0] = new Vector(x - w/2, y + h/2, x + w/2, y + h/2);
        this.vectors[1] = new Vector(x + w/2, y + h/2, x + w/2, y - h/2);
        this.vectors[2] = new Vector(x + w/2, y - h/2, x - w/2, y - h/2);
        this.vectors[3] = new Vector(x - w/2, y - h/2, w - w/2, y + h/2);

        // TODO IMPLEMENT ROTATION
    }

    @Override
    public void setPosition(float x, float y) {

    }

    @Override
    public void setX(float x) {

    }

    @Override
    public void setY(float y) {

    }

}
