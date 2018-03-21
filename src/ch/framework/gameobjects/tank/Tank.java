package ch.framework.gameobjects.tank;

import ch.framework.ID;
import ch.framework.collision.Rectangle;
import ch.framework.gameobjects.Bullet;
import ch.framework.gameobjects.GameObject;
import javafx.scene.paint.Color;

import java.util.UUID;

public class Tank extends GameObject {

    private float rotation;
    private float velocity, velRotation;
    private boolean alive;

    private Turret turret;
    private Bullet.Type bulletType;
    private Color color;

    public Tank(ID id, float x, float y, float rotation) {
        this.x = x;
        this.y = y;
        this.rotation = rotation;
        this.id = id;
        alive = true;

        turret = new Turret(this, rotation);
        bulletType = Bullet.Type.STANDARD;
        bounds = new Rectangle(x, y, 48, 48, rotation);
    }

    public void update() {

        rotation += velRotation;
        x += Math.sin(Math.toRadians(-rotation)) * velocity;
        y += Math.cos(Math.toRadians(-rotation)) * velocity;

        bounds.setLocation(x, y, rotation);

        //Update turret
        turret.update();
    }

    public Turret getTurret() {
        return turret;
    }

    public float getVelocity() {
        return velocity;
    }

    public void setVelocity(float velocity) {
        this.velocity = velocity;
    }

    public float getVelRotation() {
        return velRotation;
    }

    public void setVelRotation(float velRotation) {
        this.velRotation = velRotation;
    }

    public float getRotation() {
        return rotation;
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }

    public Bullet.Type getBulletType() {
        return bulletType;
    }

    public void setBulletType(Bullet.Type bulletType) {
        this.bulletType = bulletType;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public Rectangle getFutureBounds() {

        double x = this.x + Math.sin(Math.toRadians(-rotation + velRotation)) * (velocity);
        double y = this.y + Math.cos(Math.toRadians(-rotation + velRotation)) * (velocity);

        return new Rectangle(x, y, 48, 48, rotation);
    }
}

