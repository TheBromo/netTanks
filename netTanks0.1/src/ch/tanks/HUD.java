package ch.tanks;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class HUD {

    private Framework framework;
    private boolean overlayVisibility, playerInfoVisibility;

    public HUD(Framework framework) {
        this.framework = framework;
        this.playerInfoVisibility = false;
        this.playerInfoVisibility = false;
    }

    public void render(GraphicsContext gc) {

        if (playerInfoVisibility) {
            showPlayerInfo(gc);
        }

        if (overlayVisibility) {
            showOverlay(gc);
        }
    }

    public void showPlayerInfo(GraphicsContext gc) {
        for (Tank tank : framework.getTanks()) {
            int width = tank.getId().name().length() * 7 + 10;
            gc.setFill(Color.LIGHTGRAY);
            gc.fillRoundRect(tank.getX() - width / 2, tank.getY() - 65, width, 25, 4, 4);

            gc.setFill(Color.WHITESMOKE);
            gc.fillText(tank.getId().name(), tank.getX() - width / 2 + 5, tank.getY() - 50);
        }
    }

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


        //LINES & BOUNDS//

        //Line
        gc.setStroke(Color.RED);
        gc.strokeLine(framework.getPlayer().getX(), framework.getPlayer().getY(), Framework.MOUSEX, Framework.MOUSEY);

        //Bullet bounds
        gc.setStroke(Color.RED);
        for (Bullet b : framework.getBullets()) {
            gc.strokeOval(b.getX() - b.getRadius(), b.getY() - b.getRadius(), b.getRadius() * 2, b.getRadius() * 2);
        }

        //Block bounds
        gc.setStroke(Color.RED);
        for (Block b : framework.getMap().getBlocks()) {
            for (Segment s : b.getBounds().getSegments()) {
                gc.strokeLine(s.getA().getX(), s.getA().getY(), s.getB().getX(), s.getB().getY());
            }
        }

        //Player Points
        gc.setFill(Color.RED);
        for (Point p : framework.getPlayer().getBounds().getPoints()) {
            gc.fillOval(p.getX() - 1, p.getY() - 1, 2, 2);
        }
    }

    public void toggleOverlayVisibility() {
        this.overlayVisibility = !overlayVisibility;
    }

    public void togglePlayerInfoVisibility() {
        this.playerInfoVisibility = !playerInfoVisibility;
    }

    public void setOverlayVisibility(boolean overlayVisibility) {
        this.overlayVisibility = overlayVisibility;
    }

    public void setPlayerInfoVisibility(boolean playerInfoVisibility) {
        this.playerInfoVisibility = playerInfoVisibility;
    }
}
