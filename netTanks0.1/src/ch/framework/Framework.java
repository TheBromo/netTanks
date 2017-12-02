package ch.framework;

import ch.framework.gameobjects.Bullet;
import ch.framework.gameobjects.Mine;
import ch.framework.gameobjects.PickUp;
import ch.framework.gameobjects.tank.Tank;
import ch.framework.map.Block;
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

    private Canvas canvas;
    private Canvas ground;
    private GraphicsContext gc;
    private GraphicsContext groundGc;
    private Timeline gameloop;
    private Random random;
    private HUD hud;

    private Match match;
    private Handler handler;

    private Player player;

    public Framework(int width, int height) {
        this.WIDTH = width;
        this.HEIGHT = height;

        this.setWidth(WIDTH);
        this.setHeight(HEIGHT);
        random = new Random();

        ground = new Canvas(WIDTH, HEIGHT);
        groundGc = ground.getGraphicsContext2D();
        this.getChildren().add(ground);
        groundGc.setFill(Color.BEIGE);
        groundGc.fillRect(0, 0, this.getWidth(), this.getHeight());


        canvas = new Canvas(WIDTH, HEIGHT);
        gc = canvas.getGraphicsContext2D();
        this.getChildren().add(canvas);

        hud = new HUD(this);
        this.getChildren().add(hud);

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

        render();
    }

    private void render() {
        //Clear Canvas (Prevents "smearing" effect)
        gc.clearRect(0, 0, WIDTH, HEIGHT);

        //Background
        if (FRAME % 7 == 0) {
            for (Tank tank : handler.getTanks()) {
                groundGc.save();
                groundGc.transform(new Affine(new Rotate(tank.getRotation(), tank.getX(), tank.getY())));
                groundGc.setFill(Color.rgb(229, 229, 211, 0.8));
                groundGc.fillRect(tank.getX() - 32, tank.getY(), 16, 5);
                groundGc.fillRect(tank.getX() + 32 - 16, tank.getY(), 16, 5);
                groundGc.restore();
            }
        }

        //Blocks
        for (Block block : handler.getMap().getBlocks()) {
            if (block.getType() == Block.Type.STANDARD) {
                gc.setFill(Color.valueOf("#debf89"));
                gc.fillRoundRect(block.getX(), block.getY(), 64, 64, 10, 10);
            } else if (block.getType() == Block.Type.CORK) {
                gc.setFill(Color.valueOf("#debf89").brighter().brighter());
                gc.fillRoundRect(block.getX(), block.getY(), 64, 64, 10, 10);
            } else if (block.getType() == Block.Type.HOLE) {
                gc.setFill(Color.GRAY.brighter());
                gc.fillOval(block.getX(), block.getY(), 64, 64);
            }
        }

        //Tanks
        for (Tank tank : handler.getTanks()) {
            Color color = Color.valueOf("#babbbc"); //Default color
            if (tank.getColor() != null) {
                color = tank.getColor();
            }

            gc.save();

            Affine transform = new Affine(new Rotate(tank.getRotation(), tank.getX(), tank.getY()));
            gc.transform(transform);
            gc.setFill(Color.GREY);
            gc.fillRoundRect(tank.getX() - 32, tank.getY() - 32, 64, 64, 3, 3);
            gc.setFill(color);
            gc.fillRect(tank.getX() - 32 + 12, tank.getY() - 32, 64 - 24, 64);

            gc.restore();

            //TURRET
            gc.save();

            gc.transform(new Affine(new Rotate(tank.getTurret().getRotation(), tank.getX(), tank.getY())));
            gc.setFill(color.brighter());
            gc.fillRoundRect(tank.getX() - 16, tank.getY() - 16, 32, 32, 7, 7);
            gc.setFill(Color.LIGHTGRAY);
            gc.fillRect(tank.getX() - 2, tank.getY() - 40, 4, 25);

            gc.restore();
        }

        //Mines TODO
        for (Mine mine : handler.getMines()) {
            gc.setFill(Color.YELLOW);
            gc.fillOval(mine.getX() - mine.getRadius(), mine.getY() - mine.getRadius(), mine.getRadius() * 2, mine.getRadius() * 2);
        }

        //PickUps
        for (PickUp pickUp : handler.getPickUps()) {
            gc.setFill(Color.LIGHTBLUE);
            gc.fillOval(pickUp.getX() - pickUp.getRadius(), pickUp.getY() - pickUp.getRadius(), pickUp.getRadius() * 2, pickUp.getRadius() * 2);
        }

        //Bullets
        for (Bullet bullet : handler.getBullets()) {
            gc.save();
            gc.translate(bullet.getX(), bullet.getY());
            gc.transform(new Affine(new Rotate(bullet.getRotation()))); //Rotate the gc to the angle of the bullet's path

            //TODO increase bullet size in general

            if (bullet.getType() == Bullet.Type.STANDARD) {
                gc.translate(-2, -3); //Move SVG to center of Bullet
                gc.setFill(Color.GRAY);
                gc.beginPath();
                gc.appendSVGPath("M 0 3 Q 0 1 2 0 Q 4 1 4 3 L 4 7 L 0 7 Z"); //SVG PATH OF BULLET
                gc.fill();
                gc.closePath();
            } else if (bullet.getType() == Bullet.Type.ROCKET) {
                //TODO create rocket SVG
                gc.setFill(Color.GRAY);
                gc.beginPath();
                gc.appendSVGPath("M 0 3 Q 0 1 2 0 Q 4 1 4 3 L 4 7 L 0 7 Z"); //SVG PATH OF BULLET
                gc.fill();
                gc.closePath();
            } else if (bullet.getType() == Bullet.Type.BOUNCY) {
                gc.setFill(Color.GRAY);
                gc.fillOval(bullet.getX() - bullet.getRadius(), bullet.getY() - bullet.getRadius(), bullet.getRadius() * 2, bullet.getRadius() * 2);
            }

            gc.restore();
        }

        hud.render();
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
        handler.getTanks().add(tank);
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

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}