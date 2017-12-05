package ch.framework;

import ch.framework.collision.Collision;
import ch.framework.gameobjects.Bullet;
import ch.framework.gameobjects.Mine;
import ch.framework.gameobjects.PickUp;
import ch.framework.gameobjects.tank.Tank;
import ch.framework.map.Block;
import ch.match.Match;
import ch.match.Player;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.transform.Affine;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

import java.util.Random;

public class Framework extends Pane {

    private double mouseX, mouseY, scale;
    private static int FRAME, WIDTH, HEIGHT;

    private Timeline gameloop;
    private Random random;

    private Match match;
    private Handler handler;
    private Render render;
    private HUD hud;

    private Player player;

    public Framework(int width, int height) {
        this.WIDTH = width;
        this.HEIGHT = height;

        this.setWidth(WIDTH);
        this.setHeight(HEIGHT);
        random = new Random();

        hud = new HUD(this);
        this.getChildren().add(hud);

        render = new Render(this);

        //Create Game Loop
        gameloop = new Timeline(new KeyFrame(
                Duration.millis(16.666666666667),
                ae -> update()));
        gameloop.setCycleCount(Timeline.INDEFINITE);

        //Set scale to current scale of Canvas
        scale = this.getScaleX();

        //Make the Canvas register keystrokes
        this.addEventFilter(MouseEvent.ANY, (e) -> this.requestFocus());
    }

    public void start() {
//        this.match = match;

        handler = new Handler();
        spawnPlayer(player);

//        player = new Tank(100, 100, 0, Color.valueOf("#5cb0cc"), this);
////        framework.add(new Tank(500, 100, -50, Color.DARKGREEN, ID.ENEMY, this)); Adds second tank
//        tanks.add(player);
//
//        PickUp pip = new PickUp(500, 500);
//        pickUps.add(pip);

//        for (Player player : match.getPlayers()) {
//            //handler.spawnTank(100, 100, 0, player);
//            handler.spawnTankRandom(player);
//        }

        //Set Inputs
        setKeyInput();
        setMouseInput();

        gameloop.play();
    }

    public void stop() {
        gameloop.stop();
    }

    /**
     * Gets called 60 times per second by the gameloop.
     * Used to update and render objects etc.
     */
    private void update() {
        FRAME++;

        if (player.getTank() != null) {
            //Turn turret to current mouse position
            float angle = ((float) Math.toDegrees(Math.atan2((mouseY - player.getTank().getY()), (mouseX - player.getTank().getX()))) + 90);
            if (angle < 0) {
                angle += 360;
            }
            player.getTank().getTurret().setRotation(angle);
        }

        //Update all the things!
        handler.update();

        render.render(handler);
        hud.render();
    }

    private void setKeyInput() {

        this.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case W:
                    player.getTank().setVelocity(-1.5f);
                    break;
                case A:
                    player.getTank().setVelRotation(-1);
                    break;
                case S:
                    player.getTank().setVelocity(1.5f);
                    break;
                case D:
                    player.getTank().setVelRotation(1);
                    break;

                case TAB:
                    hud.setPlayerInfoVisibility(true);
                    break;
            }
        });

        this.setOnKeyReleased(event -> {
            switch (event.getCode()) {
                case W:
                    player.getTank().setVelocity(0);
                    break;
                case A:
                    player.getTank().setVelRotation(0);
                    break;
                case S:
                    player.getTank().setVelocity(0);
                    break;
                case D:
                    player.getTank().setVelRotation(0);
                    break;
                case F1:
                    hud.toggleOverlayVisibility();
                    break;
                case TAB:
                    hud.setPlayerInfoVisibility(false);
                    break;
                case SPACE:
                    handler.handleMinePlaced(player.getTank());
                    break;
                case C:
                    player.setColor(new Color(random.nextDouble(), random.nextDouble(), random.nextDouble(), 1));
                    break;
            }
        });
    }

    private void setMouseInput() {
        this.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                //TODO
            }
        });

        this.setOnMousePressed(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                handler.handleShot(player.getTank());
            }
        });

        this.setOnMouseReleased(event -> {

        });

        this.setOnMouseMoved(event -> {
            //Tell the Canvas to update mouseX and mouseY every time the mouse was moved
            mouseX = event.getX();
            mouseY = event.getY();
        });

        this.setOnMouseDragged(event -> {
            //Still update mouse position even if the mouse buttons are down...
            mouseX = event.getX();
            mouseY = event.getY();
        });

//        this.setOnScroll(se -> {
//
//            double maxSCALE = 3, minSCALE = 0.5;
//            double zoom = se.getDeltaY() / 320;
//
//            if (scale + zoom > maxSCALE) {
//                scale = maxSCALE;
//                gc.getTransform().setMxx(scale);
//                gc.getTransform().setMyy(scale);
//            } else if (scale + zoom < minSCALE) {
//                scale = minSCALE;
//                gc.getTransform().setMxx(scale);
//                gc.getTransform().setMyy(scale);
//            } else {
//                scale += zoom;
//                gc.getTransform().setMxx(scale);
//                gc.getTransform().setMyy(scale);
//            }
//        });
    }

    public void spawnPlayer(Player player) {
        Tank tank = new Tank(100, 100, 0);
        player.setTank(tank);
        handler.addTank(tank);
    }


    public double getMouseX() {
        return mouseX;
    }

    public double getMouseY() {
        return mouseY;
    }

    public static int getFRAME() {
        return FRAME;
    }

    public static int getWIDTH() {
        return WIDTH;
    }

    public static int getHEIGHT() {
        return HEIGHT;
    }

    public Match getMatch() {
        return match;
    }

    public Handler getHandler() {
        return handler;
    }

    public Render getRender() {
        return render;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}