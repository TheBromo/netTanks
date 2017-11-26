package ch.framework.gameobjects;

import ch.framework.collision.Circle;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Mine {

    private float x, y, radius;
    private float explosionRadius, explosionDamage;
    private int time;
    private boolean active;

    private Circle activationBounds, explosionBounds;

    public Mine(float x, float y) {
        this.x = x;
        this.y = y;

        radius = 16;
        explosionRadius = 16;
        explosionDamage = 100;
        time = 60 * 4;

        activationBounds = new Circle(x, y, radius);
        explosionBounds = new Circle(x, y, explosionRadius);
    }

    public void update(GraphicsContext gc) {
        gc.setFill(Color.YELLOW);
        gc.fillOval(x - radius, y - radius, radius * 2, radius * 2);

        if (time > 0) {
            time--;
        }
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

    public float getExplosionRadius() {
        return explosionRadius;
    }

    public float getExplosionDamage() {
        return explosionDamage;
    }

    public int getTime() {
        return time;
    }

    public Circle getActivationBounds() {
        return activationBounds;
    }

    public Circle getExplosionBounds() {
        return explosionBounds;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
