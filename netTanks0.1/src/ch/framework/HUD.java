package ch.framework;

import ch.framework.collision.Point;
import ch.framework.collision.Segment;
import ch.framework.gameobjects.bullet.Bullet;
import ch.framework.map.block.Block;
import ch.match.Player;
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

//        gc.setStroke(Color.LIGHTGRAY);
//        gc.strokeLine(framework.getPlayer().getX(), framework.getPlayer().getY(), framework.getMouseX(), framework.getMouseY());

        if (playerInfoVisibility) {
            showPlayerInfo(gc);
        }

        if (overlayVisibility) {
            showOverlay(gc);
        }
    }

    public void showPlayerInfo(GraphicsContext gc) {
        for (Player player : framework.getMatch().getPlayers()) {
            if (player.getTank() != null) {
                int width = player.getUsername().length() * 7 + 10;
                gc.setFill(Color.LIGHTGRAY);
                gc.fillRoundRect(player.getTank().getX() - width / 2, player.getTank().getY() - 65, width, 25, 4, 4);

                gc.setFill(Color.WHITESMOKE);
                gc.fillText(player.getUsername(), player.getTank().getX() - width / 2 + 5, player.getTank().getY() - 50);
            }
        }
    }

    public void showOverlay(GraphicsContext gc) {
        int width = 200, height = 100, spacing = 15;

        gc.setFill(Color.rgb(100, 100, 100, 0.5));
        gc.fillRect(0, 0, width, height);
        gc.setFill(Color.WHITESMOKE);
        gc.fillText("mouse x: " + framework.getMouseX() + "\t mouse y: " + framework.getMouseY(), 15, spacing * 1, width);
        gc.fillText("x: " + framework.getPlayer().getTank().getX() + "\ty: " + framework.getPlayer().getTank().getY(), 15, spacing * 2, width);
        gc.fillText("angle: " + framework.getPlayer().getTank().getRotation(), 15, spacing * 3, width);
        gc.fillText("turret angle: " + framework.getPlayer().getTank().getTurret().getAngle(), 15, spacing * 4, width);
        gc.fillText("bullets: " + framework.getHandler().getBullets().size(), 15, spacing * 5, width);


        //LINES & BOUNDS//

        //Line
        gc.setStroke(Color.RED);
        gc.strokeLine(framework.getPlayer().getTank().getX(), framework.getPlayer().getTank().getY(), framework.getMouseX(), framework.getMouseY());

        //Bullet bounds
        gc.setStroke(Color.RED);
        for (Bullet b : framework.getHandler().getBullets()) {
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
        for (Point p : framework.getPlayer().getTank().getBounds().getPoints()) {
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
