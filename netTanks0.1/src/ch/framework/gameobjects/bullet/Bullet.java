package ch.framework.gameobjects.bullet;

import ch.framework.collision.Circle;
import ch.framework.effects.SmokeEffect;
import javafx.scene.canvas.GraphicsContext;

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