package ch.framework.gameobjects.tank;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.transform.Affine;
import javafx.scene.transform.Rotate;

public class Turret {

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
