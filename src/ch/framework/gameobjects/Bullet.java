package ch.framework.gameobjects;

import ch.framework.collision.Circle;

public class Bullet extends GameObject {

    private float rootX, rootY, rotation;
    private float radius;
    private int ticks, rebounds;
    private boolean active;

    private Type type;

    public Bullet(float rootX, float rootY, float rotation, Type type) {
        this.rootX = rootX;
        this.rootY = rootY;
        this.rotation = rotation;
        x = rootX;
        y = rootY;
        this.type = type;
        this.radius = type.radius();
        bounds = new Circle(x, y, radius);
        active = false;
    }

    public void update() {
        ticks++;
        x -= Math.sin(Math.toRadians(-rotation)) * type.speed();
        y -= Math.cos(Math.toRadians(-rotation)) * type.speed();
        bounds.setLocation(x, y, rotation);
    }

    public void rebound(float x, float y, float rotation) {
        this.x = x;
        this.y = y;
        this.rebounds++;
        System.out.println("Rotation: " + this.rotation);
        if (rotation == 90 || rotation == 270) { //TODO make a general statement for reflection rotation
            this.rotation = 180 - this.rotation;
        } else {
            this.rotation = -this.rotation;
        }
        System.out.println("Rebound rotation: " + this.rotation);
        System.out.println(rotation);
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

    public float getRotation() {
        return rotation;
    }

    public Type getType() {
        return type;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getNextX() {
        return (float) (x - Math.sin(Math.toRadians(-rotation)) * type.speed());
    }

    public float getNextY() {
        return (float) (y - Math.cos(Math.toRadians(-rotation)) * type.speed());
    }

    public float getRadius() {
        return radius;
    }

    public int getRebounds() {
        return rebounds;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public enum Type {

        STANDARD(3, 1, 3),
        ROCKET(5, 0, 3),
        BOUNCY(3, 2, 3);

        private final float speed;
        private final int rebounds;
        private final float radius;

        Type(float speed, int rebounds, float radius) {
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

    }

}