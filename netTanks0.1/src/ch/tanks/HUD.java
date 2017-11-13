package ch.tanks;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class HUD {

    Framework framework;

    public HUD(Framework framework) {
        this.framework = framework;
    }

    //TODO

    public void showOverlay(GraphicsContext gc) {
        int width = 200, height = 100, spacing = 15;

        gc.setFill(Color.rgb(100, 100, 100, 0.5));
        gc.fillRect(0, 0, width, height);
        gc.setFill(Color.WHITESMOKE);
        gc.fillText("mouse x: " + Framework.MOUSEX + "\t mouse y: " + Framework.MOUSEY, 15, spacing * 1, width);
        gc.fillText("x: " + framework.getPlayer().getX() + "\ty: " + framework.getPlayer().getY(), 15, spacing * 2, width);
        gc.fillText("angle: " + framework.getPlayer().getAngle(), 15, spacing * 3, width);
        gc.fillText("turret angle: " + framework.getPlayer().getTurret().getAngle(), 15, spacing * 4, width);
        gc.fillText("bullets: " + framework.getBullets().size(), 15, spacing * 5, width);


        //LINES & BOUNDS
        gc.setStroke(Color.LIGHTPINK);
        gc.strokeLine(framework.getPlayer().getX(), framework.getPlayer().getY(), Framework.MOUSEX, Framework.MOUSEY);
    }
}
