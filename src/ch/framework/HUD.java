package ch.framework;

import ch.framework.collision.*;
import ch.framework.gameobjects.Bullet;
import ch.framework.map.Block;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

public class HUD extends AnchorPane {

    private Mainframe mainframe;
    private boolean overlayVisibility, playerInfoVisibility;

    private Canvas canvas;
    private GraphicsContext gc;

    public HUD(Mainframe mainframe) {
        this.mainframe = mainframe;
        this.playerInfoVisibility = false;
        this.playerInfoVisibility = false;

        canvas = new Canvas();
        canvas.setWidth(NetTanks.getInstance().getWidth());
        canvas.setHeight(NetTanks.getInstance().getHeight());
        this.gc = canvas.getGraphicsContext2D();
        this.getChildren().add(canvas);
        this.setTopAnchor(canvas, 0d);
        this.setLeftAnchor(canvas, 0d);
    }

    public void render() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

//        gc.setStroke(Color.LIGHTGRAY);
//        gc.strokeLine(mainframe.getPlayer().getCx(), mainframe.getPlayer().getCy(), mainframe.getMouseX(), mainframe.getMouseY());

//        if (playerInfoVisibility) {
//            showPlayerInfo();
//        }

        if (overlayVisibility) {
            showOverlay();
        }
    }

//    public void showPlayerInfo() { //TODO
//        for (Player player : mainframe.getMatch().getPlayers()) {
//            if (player.getTank() != null) {
//                int width = player.getUsername().length() * 7 + 10;
//                gc.setFill(Color.LIGHTGRAY);
//                gc.fillRoundRect(player.getTank().getX() - width / 2, player.getTank().getY() - 65, width, 25, 4, 4);
//
//                gc.setFill(Color.WHITESMOKE);
//                gc.fillText(player.getUsername(), player.getTank().getX() - width / 2 + 5, player.getTank().getY() - 50);
//            }
//        }
//    }

    public void showOverlay() {
        int width = 200, height = 100, spacing = 15;

        gc.setFill(Color.rgb(100, 100, 100, 0.5));
        gc.fillRect(0, 0, width, height);
        gc.setFill(Color.WHITESMOKE);
//        gc.fillText("mouse cx: " + mainframe.getMouseX() + "\t mouse cy: " + mainframe.getMouseY(), 15, spacing * 1, width);
//        gc.fillText("cx: " + mainframe.getPlayer().getTank().getX() + "\tcy: " + mainframe.getPlayer().getTank().getY(), 15, spacing * 2, width);
//        gc.fillText("angle: " + mainframe.getPlayer().getTank().getRotation(), 15, spacing * 3, width);
//        gc.fillText("turret angle: " + mainframe.getPlayer().getTank().getTurret().getRotation(), 15, spacing * 4, width);
//        gc.fillText("bullets: " + mainframe.getHandler().getBullets().size(), 15, spacing * 5, width);


        //LINES & BOUNDS//

        //Line
        gc.setStroke(Color.RED);
        //gc.strokeLine(mainframe.getPlayer().getTank().getX(), mainframe.getPlayer().getTank().getY(), mainframe.getMouseX(), mainframe.getMouseY());

        //Bullet bounds
        gc.setStroke(Color.RED);
        for (Bullet b : mainframe.getSession().getHandler().getBullets()) {
            gc.strokeOval(b.getX() - b.getRadius(), b.getY() - b.getRadius(), b.getRadius() * 2, b.getRadius() * 2);
        }

        //Block bounds
        gc.setStroke(Color.RED);
        for (Block b : mainframe.getSession().getHandler().getMap().getBlocks()) {
            for (Segment s : b.getBounds().getSegments()) {
                gc.strokeLine(s.getA().getX(), s.getA().getY(), s.getB().getX(), s.getB().getY());
            }
        }

        //Player Points
        gc.setFill(Color.RED);

//        for (GameObject go : mainframe.getHandler().getGameObjects()) {
//            for (Rectangle rectangle : go.getBounds().getRectangles()) {
//                double x = rectangle.getCenterX() - rectangle.getWidth() / 2;
//                double y = rectangle.getCenterY() - rectangle.getHeight() / 2;
//                gc.strokeRect(x, y, rectangle.getWidth(), rectangle.getHeight());
//                gc.fillOval(rectangle.getCenterX() - 2, rectangle.getCenterY() - 2, 4, 4);
//            }
//
//            for (Circle circle : go.getBounds().getCircles()) {
//                double x = circle.getCenterX() - circle.getRadius();
//                double y = circle.getCenterY() - circle.getRadius();
//                double r = circle.getRadius();
//                gc.strokeOval(x, y, r * 2, r * 2);
//            }
//        }
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
