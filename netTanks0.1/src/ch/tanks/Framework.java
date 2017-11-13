package ch.tanks;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Random;

public class Framework extends Canvas {

    public static double MOUSEX, MOUSEY, SCALE;
    public static boolean OVERLAY;

    private GraphicsContext gc;
    private Timeline gameloop;
    private Random random;
    private HUD hud;

    private Map map;
    private Tank player;
    private ArrayList<Tank> tanks; //TODO add other players
    private ArrayList<Bullet> bullets;


    public Framework(int width, int height) {
        gc = this.getGraphicsContext2D();
        this.setWidth(width);
        this.setHeight(height);
        random = new Random();
        bullets = new ArrayList<>();
        hud = new HUD(this);
        tanks = new ArrayList<>();

        //Create Game Loop
        gameloop = new Timeline(new KeyFrame(
                Duration.millis(16.666666666667),
                ae -> update()));
        gameloop.setCycleCount(Timeline.INDEFINITE);

        //Set SCALE to current scale of Canvas
        SCALE = this.getScaleX();

        //Make the Canvas register keystrokes
        this.addEventFilter(MouseEvent.ANY, (e) -> this.requestFocus());

        //Set Inputs
        setKeyInput();
        setMouseInput();
    }

    public void start() {
        gameloop.play();

        map = new Map(15, 10); //Size can be changed later
        player = new Tank(100, 100, 0, Color.valueOf("#5cb0cc"), ID.PLAYER, this);
        tanks.add(new Tank(500, 100, -50, Color.DARKGREEN, ID.ENEMY, this));
        tanks.add(player);
    }

    public void stop() {
        gameloop.stop();
    }

    /**
     * Gets called 60 times per second by the gameloop.
     * Used to update and render objects etc.
     */
    public void update() {
        //Clear Canvas (Prevents "smearing" effect)
        gc.clearRect(0, 0, this.getWidth(), this.getHeight());

        //Turn turret to current mouse position
        player.getTurret().setAngle(
                ((float) Math.toDegrees(Math.atan2((MOUSEY - player.getY()), (MOUSEX - player.getX()))) + 90));

        //Check for any collisions before updating
        collision();

        //Update all the things!
        map.update(gc);
        for (Tank tank : tanks) {
            tank.update(gc);
        }

        for (Bullet bullet : bullets) {
            bullet.update(gc);
        }

        if (OVERLAY) {
            hud.showOverlay(gc);
        }
    }

    private void collision() {
        //Bullet vs Map boundaries
        ArrayList<Bullet> removedBullets = new ArrayList<>();

        for (Bullet bullet : bullets) {

            //MAP BOUNDARIES
            for (Segment segment : map.getBoundaries()) {
                if (Collision.testBulletToSegment(bullet, segment)) {
                    if (bullet.getRebounds() < bullet.getType().rebounds()) {
                        //Rebound
                        bullet.setRebound(bullet.getX(), bullet.getY(), segment.isHorizontal());
                    } else {
                        //Remove
                        removedBullets.add(bullet);
                    }
                    break;
                }
            }

            //MAP BLOCKS
            for (Block block : map.getBlocks()) {
                if (!block.getType().isShootable()) {

                    for (Segment seg : block.getSegments()) {
                        if (Collision.testBulletToSegment(bullet, seg)) {
                            if (bullet.getRebounds() < bullet.getType().rebounds()) {
                                //Rebound
                                bullet.setRebound(bullet.getX(), bullet.getY(), seg.isHorizontal());
                            } else {
                                //Remove
                                removedBullets.add(bullet);
                            }
                            break;
                        }
                    }
                }
            }
        }

        bullets.removeAll(removedBullets);
    }

    public void setKeyInput() {
        this.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case W:
                        player.setVelocity(-1.5f);
                        break;
                    case A:
                        player.setRotation(-1);
                        break;
                    case S:
                        player.setVelocity(1.5f);
                        break;
                    case D:
                        player.setRotation(1);
                        break;
                    case SPACE:
                        player.setColor(new Color(random.nextDouble(), random.nextDouble(), random.nextDouble(), 1));
                        break;
                    case F1:
                        OVERLAY = !OVERLAY;
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
                        player.setRotation(0);
                        break;
                    case S:
                        player.setVelocity(0);
                        break;
                    case D:
                        player.setRotation(0);
                        break;
                }
            }
        });
    }

    public void setMouseInput() {
        this.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                player.shoot();
            }
        });

        this.setOnMousePressed((event -> {

        }));

        this.setOnMouseReleased((event -> {

        }));

        this.setOnMouseMoved((event -> {
            //Tell the Canvas to update MOUSEX and MOUSEY every time the mouse was moved
            MOUSEX = event.getX();
            MOUSEY = event.getY();
        }));

//        this.setOnScroll(se -> {
//
//            double maxSCALE = 3, minSCALE = 0.5;
//            double zoom = se.getDeltaY() / 320;
//
//            if (SCALE + zoom > maxSCALE) {
//                SCALE = maxSCALE;
//                gc.getTransform().setMxx(SCALE);
//                gc.getTransform().setMyy(SCALE);
//            } else if (SCALE + zoom < minSCALE) {
//                SCALE = minSCALE;
//                gc.getTransform().setMxx(SCALE);
//                gc.getTransform().setMyy(SCALE);
//            } else {
//                SCALE += zoom;
//                gc.getTransform().setMxx(SCALE);
//                gc.getTransform().setMyy(SCALE);
//            }
//        });
    }

    public Tank getPlayer() {
        return player;
    }

    public Map getMap() {
        return map;
    }

    public ArrayList<Bullet> getBullets() {
        return bullets;
    }
}
