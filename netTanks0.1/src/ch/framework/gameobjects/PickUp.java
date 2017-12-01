package ch.framework.gameobjects;

import ch.framework.collision.Bounds;
import ch.framework.collision.Circle;
import ch.framework.gameobjects.bullet.BulletType;
import ch.framework.gameobjects.tank.Tank;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class PickUp extends GameObject {

    private float radius;
    private int time;
    private boolean pickedUp, expired;

    //private PickUpType pickUpType;
    private Tank tank;

    public PickUp(float x, float y) {
        this.x = x;
        this.y = y;
        this.radius = 16;
        this.bounds = new Bounds(x, y);
        bounds.addCircle(new Circle(x, y, radius));

        time = 60 * 10;
    }

    public PickUp(Tank tank) {
        pickedUp = true;

        time = 60 * 20;
        pickedUp = true;
        tank.setBulletType(BulletType.ROCKET);
    }

    public void update(GraphicsContext gc) {
        gc.setFill(Color.LIGHTBLUE);
        gc.fillOval(x - radius, y - radius, radius * 2, radius * 2);

        if (time > 0) {
            time--;
        }
        if (time <= 0) {
            expired = true;
        }
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public void setTime(int timeInSeconds) {
        this.time = 60 * timeInSeconds;
    }

    public int getTime() {
        return time;
    }

    public boolean isPickedUp() {
        return pickedUp;
    }

    public void setPickedUp(boolean pickedUp) {
        this.pickedUp = pickedUp;
    }

    public boolean isExpired() {
        return expired;
    }

    public void setTank(Tank tank) {
        this.tank = tank;
    }

    public Tank getTank() {
        return tank;
    }
}