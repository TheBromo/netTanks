package ch.framework.gameobjects.bullet;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.transform.Affine;
import javafx.scene.transform.Rotate;

public enum BulletType {

    STANDARD(3, 1, 3),
    ROCKET(5, 0, 3),
    BOUNCY(3, 2, 3);

    private final float speed;
    private final int rebounds;
    private final float radius;

    BulletType(float speed, int rebounds, float radius) {
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

    public void render(GraphicsContext gc, Bullet bullet) {
        gc.save();
        gc.translate(bullet.getX(), bullet.getY());
        gc.transform(new Affine(new Rotate(bullet.getAngle()))); //Rotate the gc to the angle of the bullet's path

        //TODO increase bullet size in general

        if (this == STANDARD) {
            gc.translate(-2, -3); //Move SVG to center of Bullet
            gc.setFill(Color.GRAY);
            gc.beginPath();
            gc.appendSVGPath("M 0 3 Q 0 1 2 0 Q 4 1 4 3 L 4 7 L 0 7 Z"); //SVG PATH OF BULLET
            gc.fill();
            gc.closePath();
        } else if (this == ROCKET) {
            //TODO create rocket SVG
            gc.setFill(Color.GRAY);
            gc.beginPath();
            gc.appendSVGPath("M 0 3 Q 0 1 2 0 Q 4 1 4 3 L 4 7 L 0 7 Z"); //SVG PATH OF BULLET
            gc.fill();
            gc.closePath();
        } else if (this == BOUNCY) {
            gc.setFill(Color.GRAY);
            gc.fillOval(bullet.getX() - bullet.getRadius(), bullet.getY() - bullet.getRadius(), bullet.getRadius() * 2, bullet.getRadius() * 2);
        }

        gc.restore();
    }
}
