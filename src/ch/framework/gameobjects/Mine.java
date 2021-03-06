package ch.framework.gameobjects;

import ch.framework.collision.Circle;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.UUID;

public class Mine extends GameObject {

    private UUID id;
    
    private float radius;
    private float explosionRadius, explosionDamage;
    private int counter;
    private boolean active;

    private Circle explosionBounds;

    public Mine(float x, float y) {
        this.x = x;
        this.y = y;
        init();
        this.id = UUID.randomUUID();
    }
    
    public Mine(UUID id, float x, float y) {
        this.id = id;
        this.x = x;
        this.y = y;
        init();
    }
    
    private void init() {
        radius = 16;
        explosionRadius = 64;
        explosionDamage = 100;
        counter = 60 * 6;

        bounds = new Circle(x, y, radius);
        explosionBounds = new Circle(x, y, explosionRadius);
    }

    public void update() {
    }

    public void tick() {
        counter--;
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

    public Circle getExplosionBounds() {
        return explosionBounds;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int getCounter() {
        return counter;
    }

    public UUID getId() {
        return id;
    }
}
