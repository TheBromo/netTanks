package ch.framework.effects;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Explosion extends Effect {

    private double x, y;
    private int time;
    private boolean expired;
    private double explotionradius;

    public Explosion(double x, double y) {
        this.x = x;
        this.y = y;

        time = 60 * 2;
        explotionradius = 64;
    }

    public void render(GraphicsContext gc) {
        gc.setFill(Color.ORANGE);
        gc.fillOval(x - explotionradius, y - explotionradius, explotionradius*2, explotionradius*2);
    }

    public void tick() {
        if (time > 0) {
            time--;
        } else {
            expired = true;
        }
    }

    public boolean isExpired() {
        return expired;
    }
}