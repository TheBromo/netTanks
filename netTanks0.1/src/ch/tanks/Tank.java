package ch.tanks;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.transform.Affine;
import javafx.scene.transform.Rotate;

public class Tank {

    private float x, y, angle;
    private float velocity, rotation;
    private Turret turret;
    private Color color = Color.valueOf("#5cb0cc");

    public Tank(float x, float y, float angle) {
        this.x = x;
        this.y = y;
        this.angle = angle;

        turret = new Turret(this, angle);
    }

    public void update(GraphicsContext gc) {

        angle += rotation;
        x += Math.sin(Math.toRadians(-angle)) * velocity;
        y += Math.cos(Math.toRadians(-angle)) * velocity;

        //RENDER
        gc.save();

        gc.transform(new Affine(new Rotate(angle, x, y)));
        gc.setFill(Color.GREY);
        gc.fillRoundRect(x - 32, y - 32, 64, 64, 3, 3);
        gc.setFill(color);
        gc.fillRect(x - 32 + 12, y - 32, 64 - 24, 64);

        gc.restore();

        //Update turret
        turret.update(gc);
    }

    public void shoot() {

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
}

class Turret {

    float angle;
    private Tank tank;

    public Turret(Tank tank, float angle) {
        this.tank = tank;
        this.angle = angle;
    }

    public void update(GraphicsContext gc) {
        angle = (float) Math.toDegrees(Math.atan2((tank.getY() - Framework.MOUSEY), (tank.getX() - Framework.MOUSEX)));

        gc.save();

        gc.transform(new Affine(new Rotate(angle, tank.getX(), tank.getY())));
        gc.setFill(Color.valueOf("77d4f3"));
        gc.fillRoundRect(tank.getX() - 16, tank.getY() - 16, 32, 32, 7, 7);
        gc.setFill(Color.LIGHTGRAY);
        gc.fillRect(tank.getX() - 40, tank.getY() - 2, 25, 4);

        gc.restore();

//        gc.fillRect(Framework.MOUSEX, Framework.MOUSEY, 3, 3);
//        gc.strokeLine(tank.getX(), tank.getY(), Framework.MOUSEX, Framework.MOUSEY);
    }

    public float getAngle() {
        return angle;
    }
}
