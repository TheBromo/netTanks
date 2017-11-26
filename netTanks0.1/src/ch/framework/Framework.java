package ch.framework;

import ch.framework.gameobjects.tank.Tank;
import ch.framework.map.Map;
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
import javafx.util.Duration;

import java.util.Random;

public class Framework extends Pane {

    private double mouseX, mouseY, scale;
    private static int FRAME;

    private Canvas canvas;
    private GraphicsContext gc;
    private Timeline gameloop;
    private Random random;
    private HUD hud;

    private Match match;

    private Map map;
    private Handler handler;

    private Player player;

    public Framework(int width, int height) {

        this.setWidth(width);
        this.setHeight(height);
        random = new Random();
        //player = new Player("My Username"); //

        canvas = new Canvas(width, height);
        gc = canvas.getGraphicsContext2D();
        canvas.setWidth(width);
        canvas.setHeight(height);
        this.getChildren().add(canvas);

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

    public void start(Match match) {
        this.match = match;

        hud = new HUD(this);
        handler = new Handler(this);
        map = new Map(15, 10, this); //Size can be changed later
        this.getChildren().add(0, map);

//        player = new Tank(100, 100, 0, Color.valueOf("#5cb0cc"), this);
////        framework.add(new Tank(500, 100, -50, Color.DARKGREEN, ID.ENEMY, this)); Adds second tank
//        tanks.add(player);
//
//        PickUp pip = new PickUp(500, 500);
//        pickUps.add(pip);

        for (Player player : match.getPlayers()) {
            //handler.spawnTank(100, 100, 0, player);
            handler.spawnTankRandom(player);
        }

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
        //Clear Canvas (Prevents "smearing" effect)
        gc.clearRect(0, 0, this.getWidth(), this.getHeight());

        //Turn turret to current mouse position
        float angle = ((float) Math.toDegrees(Math.atan2((mouseY - player.getTank().getY()), (mouseX - player.getTank().getX()))) + 90);
        if (angle < 0) {
            angle += 360;
        }
        player.getTank().getTurret().setAngle(angle);

        //Update all the things!
        map.update(gc);
        handler.update(gc);
        hud.render(gc);
    }

    private void setKeyInput() {

        Tank player = this.player.getTank();

        this.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case W:
                        player.setVelocity(-1.5f);
                        break;
                    case A:
                        player.setVelRotation(-1);
                        break;
                    case S:
                        player.setVelocity(1.5f);
                        break;
                    case D:
                        player.setVelRotation(1);
                        break;

                    case TAB:
                        hud.setPlayerInfoVisibility(true);
                        break;
                }
            }
        });

        this.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case W:
                        player.setVelocity(0);
                        break;
                    case A:
                        player.setVelRotation(0);
                        break;
                    case S:
                        player.setVelocity(0);
                        break;
                    case D:
                        player.setVelRotation(0);
                        break;
                    case F1:
                        hud.toggleOverlayVisibility();
                        break;
                    case TAB:
                        hud.setPlayerInfoVisibility(false);
                        break;
                    case SPACE:
                        //TODO
                        break;
                    case C:
                        player.setColor(new Color(random.nextDouble(), random.nextDouble(), random.nextDouble(), 1));
                        break;
                }
            }
        });
    }

    private void setMouseInput() {
        this.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                //TODO
            }
        });

        this.setOnMousePressed((event -> {

        }));

        this.setOnMouseReleased((event -> {

        }));

        this.setOnMouseMoved((event -> {
            //Tell the Canvas to update mouseX and mouseY every time the mouse was moved
            mouseX = event.getX();
            mouseY = event.getY();
        }));

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

    public Map getMap() {
        return map;
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

    public Match getMatch() {
        return match;
    }

    public Handler getHandler() {
        return handler;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}