package ch.framework.gameobjects.tank;

import ch.framework.collision.Rectangle;
import ch.framework.gameobjects.bullet.BulletType;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.transform.Affine;
import javafx.scene.transform.Rotate;

public class Tank {

    private float x, y, rotation;
    private float velocity, velRotation;
    private Color color;
    private Rectangle bounds;

    private Turret turret;
    private BulletType bulletType;

    public Tank(float x, float y, float rotation) {
        this.x = x;
        this.y = y;
        this.rotation = rotation;

        this.color = Color.valueOf("#babbbc"); //Default color

        turret = new Turret(this, rotation);
        bulletType = BulletType.STANDARD;
        bounds = new Rectangle(x, y, 64, 64, rotation);
    }

    public void update(GraphicsContext gc) {

        rotation += velRotation;
        x += Math.sin(Math.toRadians(-rotation)) * velocity;
        y += Math.cos(Math.toRadians(-rotation)) * velocity;

        bounds.setLocation(x, y, 64, 64, rotation);

        //RENDER
        gc.save();

        Affine transform = new Affine(new Rotate(rotation, x, y));
        gc.transform(transform);
        gc.setFill(Color.GREY);
        gc.fillRoundRect(x - 32, y - 32, 64, 64, 3, 3);
        gc.setFill(color);
        gc.fillRect(x - 32 + 12, y - 32, 64 - 24, 64);

        gc.restore();

        //Update turret
        turret.update(gc);
    }

//    public void shoot() {
//        Bullet bullet = new Bullet(turret.getMuzzleX(), turret.getMuzzleY(), turret.getRotation(), bulletType);
//        bullets.add(bullet);
//        framework.getBullets().add(bullet);
//        System.out.println("Pew! " + turret.getRotation());
//    }
//
//    public void place() {
//        Mine mine = new Mine(x, y);
//        mines.add(mine);
//        framework.getMines().add(mine);
//    }

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

    public float getRotation() {
        return rotation;
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public BulletType getBulletType() {
        return bulletType;
    }

    public void setBulletType(BulletType bulletType) {
        this.bulletType = bulletType;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public Rectangle getFutureBounds() {

        double x = this.x + Math.sin(Math.toRadians(-rotation + velRotation)) * (velocity);
        double y = this.y + Math.cos(Math.toRadians(-rotation + velRotation)) * (velocity);

        return new Rectangle(x, y, 64, 64, rotation);
    }
}

