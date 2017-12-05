package ch.framework.effects;

import ch.framework.gameobjects.Bullet;
import javafx.scene.canvas.GraphicsContext;

public class BulletTrail extends Smoke {

    private Bullet bullet;

    public BulletTrail(Bullet bullet) {
        this.bullet = bullet;
    }

    public void render(GraphicsContext gc) {
        super.setLocation(bullet.getX(), bullet.getY());
        super.render(gc);
    }
}
