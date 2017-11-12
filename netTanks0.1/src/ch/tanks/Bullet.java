package ch.tanks;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Bullet {

    private float rootX, rootY, angle;
    private float x, y;
    private int ticks, rebounds;
    private BulletType type;
    //TODO add TankID

    public Bullet(float rootX, float rootY, float angle, BulletType type) {
        this.rootX = rootX;
        this.rootY = rootY;
        this.angle = angle;
        x = rootX;
        y = rootY;
        this.type = type;
        Framework.BULLETS.add(this);
    }

    public void update(GraphicsContext gc) {
        ticks++;
        x += Math.sin(Math.toRadians(-angle)) * type.speed();
        y += Math.cos(Math.toRadians(-angle)) * type.speed();
        gc.setFill(Color.RED);
        gc.fillOval(x, y, 3, 3);
    }

    public void setRebound(float x, float y) {
        //TODO rebounds may need a new rootX and rootY

        if (rebounds < type.rebounds()) {
            this.x = x;
            this.y = y;
            this.rebounds++;
            angle = -angle;
        } else {
            Framework.BULLETS.remove(this);
        }
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

    public float getAngle() {
        return angle;
    }

    public BulletType getType() {
        return type;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }
}

enum BulletType {

    // ...(Bullet-speed, Rebound-amount);
    STANDARD(2, 1),
    ROCKET(4, 0),
    BOUNCY(1, 2);

    private final float speed;
    private final int rebounds;

    BulletType(float speed, int rebounds) {
        this.speed = speed;
        this.rebounds = rebounds;
    }

    public float speed() {
        return speed;
    }

    public int rebounds() {
        return rebounds;
    }
}

