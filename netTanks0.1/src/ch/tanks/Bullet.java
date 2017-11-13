package ch.tanks;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Bullet {

    private Framework framework;

    private float rootX, rootY, angle;
    private float x, y, radius;
    private int ticks, rebounds;
    private BulletType type;
    //TODO add TankID

    public Bullet(float rootX, float rootY, float angle, BulletType type, Framework framework) {
        this.framework = framework;
        this.rootX = rootX;
        this.rootY = rootY;
        this.angle = angle;
        x = rootX;
        y = rootY;
        this.type = type;
        this.radius = 3;

        framework.getBullets().add(this);
    }

    public void update(GraphicsContext gc) {
        ticks++;
        x -= Math.sin(Math.toRadians(-angle)) * type.speed();
        y -= Math.cos(Math.toRadians(-angle)) * type.speed();
        gc.setFill(Color.GRAY);
        gc.fillOval(x - radius, y - radius, radius * 2, radius * 2);
    }

    public void setRebound(float x, float y) {
        this.x = x;
        this.y = y;
        this.rebounds++;
        angle = -angle;
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

    public float getRadius() {
        return radius;
    }

    public int getRebounds() {
        return rebounds;
    }
}

enum BulletType {

    // ...(Bullet-speed, Rebound-amount);
    STANDARD(3, 1),
    ROCKET(5, 0),
    BOUNCY(3, 2);

    private final float speed;
    private final int rebounds;

    BulletType(float speed, int rebounds) {
        this.speed = speed;
        this.rebounds = rebounds;
    }

    public float speed() {
        return speed;
    }

    public int rebounds() {
        return rebounds;
    }
}

