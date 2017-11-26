package ch.tanks;

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

import java.util.ArrayList;
import java.util.Random;

public class Framework extends Pane {

    private double mouseX, mouseY, scale;
    private static int FRAME;

    private Canvas canvas;
    private GraphicsContext gc;
    private Timeline gameloop;
    private Random random;
    private HUD hud;

    private Map map;
    private Tank player;
    private ArrayList<Tank> tanks;
    private ArrayList<Bullet> bullets;
    private ArrayList<Mine> mines;
    private ArrayList<PickUp> pickUps;

    public Framework(int width, int height) {

        this.setWidth(width);
        this.setHeight(height);
        random = new Random();
        bullets = new ArrayList<>();
        tanks = new ArrayList<>();
        mines = new ArrayList<>();
        pickUps = new ArrayList<>();
        hud = new HUD(this);

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

        //Set Inputs
        setKeyInput();
        setMouseInput();
    }

    public void start() {
        gameloop.play();

        map = new Map(15, 10, this); //Size can be changed later
        this.getChildren().add(0, map);

        player = new Tank(100, 100, 0, Color.valueOf("#5cb0cc"), ID.PLAYER, this);
//        tanks.add(new Tank(500, 100, -50, Color.DARKGREEN, ID.ENEMY, this)); Adds second tank
        tanks.add(player);

        PickUp pip = new PickUp(500, 500);
        pickUps.add(pip);
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
        float angle = ((float) Math.toDegrees(Math.atan2((mouseY - player.getY()), (mouseX - player.getX()))) + 90);
        if (angle < 0) {
            angle += 360;
        }
        player.getTurret().setAngle(angle);

        //Check for any collisions before updating
        collision();

        //Update all the things!
        map.update(gc);

        PickUp removedPickUp = null;
        for (PickUp pickUp : pickUps) {
            pickUp.update(gc);
            if (pickUp.isExpired()) {
                removedPickUp = pickUp;
            }
        }
        pickUps.remove(removedPickUp);

        for (Tank tank : tanks) {
            tank.update(gc);
        }

        for (Bullet bullet : bullets) {
            bullet.update(gc);
        }

        for (Mine mine : mines) {
            mine.update(gc);
        }

        hud.render(gc);
    }

    private void collision() {

        // BULLET -
        ArrayList<Bullet> removedBullets = new ArrayList<>();
        for (Bullet bullet : bullets) {

            //MAP BOUNDARIES
            for (Segment segment : map.getBounds().getSegments()) {
                if (Collision.testCircleToSegment(bullet.getBounds(), segment)) {
                    if (bullet.getRebounds() < bullet.getType().rebounds()) {
                        //Rebound
                        bullet.setRebound(bullet.getX(), bullet.getY(), (float) segment.getAngle()); //TODO
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

                    for (Segment seg : block.getBounds().getSegments()) {
                        if (Collision.testCircleToSegment(bullet.getBounds(), seg)) {
                            if (bullet.getRebounds() < bullet.getType().rebounds()) {
                                //Rebound
                                bullet.setRebound(bullet.getX(), bullet.getY(), (float) seg.getAngle()); //TODO
                            } else {
                                //Remove
                                removedBullets.add(bullet);
                            }
                            break;
                        }
                    }
                }
            }

            //OTHER TANKS
            for (Tank tank : tanks) {
                if (Collision.testCircleToRectangle(bullet.getBounds(), tank.getBounds())) {
                    System.out.println("HIT!");
                }
            }

            //OTHER BULLETS
            for (Bullet b : bullets) {
                if (b != bullet) { //Check whether bullet is the same
                    if (Collision.testCircleToCircle(b.getBounds(), bullet.getBounds())) {
                        removedBullets.add(bullet);
                    }
                }
            }
        }
        bullets.removeAll(removedBullets);

        //TODO collision TANK - BLOCK
        for (Block block : map.getBlocks()) {
            //FRONT LEFT
            if (Collision.testRectangleToPoint(block.getBounds(), player.getFutureBounds().getPoints().get(0))) {
                if (player.getVelocity() < 0) {
                    player.setVelocity(0);
                }
                if (player.getRotation() < 0) { //TODO
                    player.setRotation(0);
                }
            }

            //FRONT RIGHT
            if (Collision.testRectangleToPoint(block.getBounds(), player.getFutureBounds().getPoints().get(1))) {
                if (player.getVelocity() < 0) {
                    player.setVelocity(0);
                }
                if (player.getRotation() > 0) { //TODO
                    player.setRotation(0);
                }
            }

            //BACK RIGHT
            if (Collision.testRectangleToPoint(block.getBounds(), player.getFutureBounds().getPoints().get(2))) {
                if (player.getVelocity() > 0) {
                    player.setVelocity(0);
                }
                if (player.getRotation() > 0) { //TODO
                    player.setRotation(0);
                }
            }

            //BACK LEFT
            if (Collision.testRectangleToPoint(block.getBounds(), player.getFutureBounds().getPoints().get(3))) {
                if (player.getVelocity() > 0) {
                    player.setVelocity(0);
                }
                if (player.getRotation() < 0) { //TODO
                    player.setRotation(0);
                }
            }
        }

        //PICKUP - TANK
        for (PickUp pickUp : pickUps) {
            if (!pickUp.isPickedUp()) {
                if (Collision.testCircleToRectangle(pickUp.getBounds(), player.getBounds())) {
                    pickUp = new PickUp(player);
                }
            }
        }

        //MINE - TANK
        ArrayList<Mine> removedMines = new ArrayList<>();
        for (Mine mine : mines) {
            if (Collision.testCircleToRectangle(mine.getActivationBounds(), player.getBounds())) {
                if (mine.isActive()) {
                    System.out.println("EXPLOSION!");
                    removedMines.add(mine);
                }
            }
        }
        mines.removeAll(removedMines);

        //TODO MINE - BULLET

    }

    private void setKeyInput() {
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
                        player.setRotation(0);
                        break;
                    case S:
                        player.setVelocity(0);
                        break;
                    case D:
                        player.setRotation(0);
                        break;
                    case F1:
                        hud.toggleOverlayVisibility();
                        break;
                    case TAB:
                        hud.setPlayerInfoVisibility(false);
                        break;
                    case SPACE:
                        player.place();
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
                player.shoot();
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

    public Tank getPlayer() {
        return player;
    }

    public ArrayList<Tank> getTanks() {
        return tanks;
    }

    public Map getMap() {
        return map;
    }

    public ArrayList<Bullet> getBullets() {
        return bullets;
    }

    public ArrayList<Mine> getMines() {
        return mines;
    }

    public ArrayList<PickUp> getPickUps() {
        return pickUps;
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
}