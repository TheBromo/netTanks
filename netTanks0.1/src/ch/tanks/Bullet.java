package ch.tanks;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.transform.Affine;
import javafx.scene.transform.Rotate;

public class Bullet {

    private float rootX, rootY, angle;
    private float x, y, radius;
    private int ticks, rebounds;

    private BulletType type;
    private Circle bounds;
    private SmokeEffect smokeEffect;

    public Bullet(float rootX, float rootY, float angle, BulletType type) {
        this.rootX = rootX;
        this.rootY = rootY;
        this.angle = angle;
        x = rootX;
        y = rootY;
        this.type = type;
        this.radius = type.radius();
        bounds = new Circle(x, y, radius);
        smokeEffect = new SmokeEffect();
    }

    public void update(GraphicsContext gc) {
        ticks++;
        x -= Math.sin(Math.toRadians(-angle)) * type.speed();
        y -= Math.cos(Math.toRadians(-angle)) * type.speed();
        bounds.setLocation(x, y, radius);

        smokeEffect.render(gc, this);
        type.render(gc, this);
    }

    public void setRebound(float x, float y, float angle) {
        this.x = x;
        this.y = y;
        this.rebounds++;
        System.out.println("Angle: " + this.angle);
        if (angle == 90 || angle == 270) { //TODO make a general statement for reflection angle
            this.angle = 180 - this.angle;
        } else {
            this.angle = -this.angle;
        }
        System.out.println("Rebound angle: " + this.angle);
        System.out.println(angle);
    }

    public int getTicks() {
        return ticks;
    }

    public float getRootX() {
        return rootX;
    }

    public float getRootY() {
        return rootY;
    }

    public float getAngle() {
        return angle;
    }

    public BulletType getType() {
        return type;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getNextX() {
        return (float) (x - Math.sin(Math.toRadians(-angle)) * type.speed());
    }

    public float getNextY() {
        return (float) (y - Math.cos(Math.toRadians(-angle)) * type.speed());
    }

    public float getRadius() {
        return radius;
    }

    public int getRebounds() {
        return rebounds;
    }

    public Circle getBounds() {
        return bounds;
    }
}

enum BulletType {

    STANDARD(3, 1, 3),
    ROCKET(5, 0, 3),
    BOUNCY(3, 2, 3);

    private final float speed;
    private final int rebounds;
    private final float radius;

    BulletType(float speed, int rebounds, float radius) {
        this.speed = speed;
        this.rebounds = rebounds;
        this.radius = radius;
    }

    public float speed() {
        return speed;
    }

    public int rebounds() {
        return rebounds;
    }

    public float radius() {
        return radius;
    }

    public void render(GraphicsContext gc, Bullet bullet) {
        gc.save();
        gc.translate(bullet.getX(), bullet.getY());
        gc.transform(new Affine(new Rotate(bullet.getAngle()))); //Rotate the gc to the angle of the bullet's path

        //TODO increase bullet size in general

        if (this == STANDARD) {
            gc.translate(-2, -3); //Move SVG to center of Bullet
            gc.setFill(Color.GRAY);
            gc.beginPath();
            gc.appendSVGPath("M 0 3 Q 0 1 2 0 Q 4 1 4 3 L 4 7 L 0 7 Z"); //SVG PATH OF BULLET
            gc.fill();
            gc.closePath();
        } else if (this == ROCKET) {
            //TODO create rocket SVG
            gc.setFill(Color.GRAY);
            gc.beginPath();
            gc.appendSVGPath("M 0 3 Q 0 1 2 0 Q 4 1 4 3 L 4 7 L 0 7 Z"); //SVG PATH OF BULLET
            gc.fill();
            gc.closePath();
        } else if (this == BOUNCY) {
            gc.setFill(Color.GRAY);
            gc.fillOval(bullet.getX() - bullet.getRadius(), bullet.getY() - bullet.getRadius(), bullet.getRadius() * 2, bullet.getRadius() * 2);
        }

        gc.restore();
    }
}

