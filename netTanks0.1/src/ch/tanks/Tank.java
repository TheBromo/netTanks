package ch.tanks;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.transform.Affine;
import javafx.scene.transform.Rotate;

import java.util.ArrayList;

public class Tank {

    private Framework framework;

    private float x, y, angle;
    private float velocity, rotation;
    private Color color;
    private ID id;
    private Rectangle bounds;

    private Turret turret;
    private ArrayList<Bullet> bullets;
    private ArrayList<Mine> mines;

    private BulletType bulletType;
    private PickUp currentPickUp;

    public Tank(float x, float y, float angle, Color color, ID id, Framework framework) {
        this.framework = framework;
        this.x = x;
        this.y = y;
        this.angle = angle;
        this.id = id;

        if (color != null) {
            this.color = color;
        } else {
            this.color = Color.valueOf("#babbbc"); //Default color
        }

        turret = new Turret(this, angle);
        bullets = new ArrayList<>();
        bulletType = BulletType.STANDARD;
        mines = new ArrayList<>();
        bounds = new Rectangle(x, y, 64, 64, angle);
    }

    public void update(GraphicsContext gc) {

        angle += rotation;
        x += Math.sin(Math.toRadians(-angle)) * velocity;
        y += Math.cos(Math.toRadians(-angle)) * velocity;

        bounds.setLocation(x, y, 64, 64, angle);

        //RENDER
        gc.save();

        Affine transform = new Affine(new Rotate(angle, x, y));
        gc.transform(transform);
        gc.setFill(Color.GREY);
        gc.fillRoundRect(x - 32, y - 32, 64, 64, 3, 3);
        gc.setFill(color);
        gc.fillRect(x - 32 + 12, y - 32, 64 - 24, 64);

        gc.restore();

        //Update turret
        turret.update(gc);

        if (currentPickUp != null && currentPickUp.getTime() == 0) {
            currentPickUp = null;
            bulletType = BulletType.STANDARD;
        }
    }

    public void shoot() {
        Bullet bullet = new Bullet(turret.getMuzzleX(), turret.getMuzzleY(), turret.getAngle(), bulletType);
        bullets.add(bullet);
        framework.getBullets().add(bullet);
        System.out.println("Pew! " + turret.getAngle());
    }

    public void place() {
        Mine mine = new Mine(x, y);
        mines.add(mine);
        framework.getMines().add(mine);
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

    public float getRotation() {
        return rotation;
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
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

    public float getAngle() {
        return angle;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public ID getId() {
        return id;
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

        double x = this.x + Math.sin(Math.toRadians(-angle + rotation)) * (velocity);
        double y = this.y + Math.cos(Math.toRadians(-angle + rotation)) * (velocity);

        return new Rectangle(x, y, 64, 64, angle);
    }

    public ArrayList<Bullet> getBullets() {
        return bullets;
    }

    public ArrayList<Mine> getMines() {
        return mines;
    }

}

class Turret {

    private float angle, barrelLength;
    private Tank tank;

    public Turret(Tank tank, float angle) {
        this.tank = tank;
        this.angle = angle;
        this.barrelLength = 40;
    }

    public void update(GraphicsContext gc) {
        gc.save();

        gc.transform(new Affine(new Rotate(angle, tank.getX(), tank.getY())));
        gc.setFill(tank.getColor().brighter());
        gc.fillRoundRect(tank.getX() - 16, tank.getY() - 16, 32, 32, 7, 7);
        gc.setFill(Color.LIGHTGRAY);
        gc.fillRect(tank.getX() - 2, tank.getY() - 40, 4, 25);

        gc.restore();
    }

    public float getAngle() {
        return angle;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }

    public float getMuzzleX() {
        return (float) (tank.getX() - Math.sin(Math.toRadians(-angle)) * barrelLength);
    }

    public float getMuzzleY() {
        return (float) (tank.getY() - Math.cos(Math.toRadians(-angle)) * barrelLength);
    }
}