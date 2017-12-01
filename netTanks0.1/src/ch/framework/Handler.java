package ch.framework;

import ch.framework.collision.Circle;
import ch.framework.collision.Collision;
import ch.framework.collision.Segment;
import ch.framework.gameobjects.GameObject;
import ch.framework.gameobjects.Mine;
import ch.framework.gameobjects.PickUp;
import ch.framework.gameobjects.bullet.Bullet;
import ch.framework.gameobjects.tank.Tank;
import ch.framework.map.block.Block;
import ch.match.Player;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;
import java.util.Random;

public class Handler {

    private Framework framework;
    private Player player;
    private Random random;

    private ArrayList<GameObject> gameObjects;

    private ArrayList<Tank> tanks;
    private ArrayList<Bullet> bullets;
    private ArrayList<Mine> mines;
    private ArrayList<PickUp> pickUps;

    public Handler(Framework framework) {
        this.framework = framework;
        player = framework.getPlayer();
        random = new Random();

        gameObjects = new ArrayList<>();

        bullets = new ArrayList<>();
        tanks = new ArrayList<>();
        mines = new ArrayList<>();
        pickUps = new ArrayList<>();
    }

    public void update(GraphicsContext gc) {

        //Check for any collisions before updating
        handleCollision();

        for (GameObject go : gameObjects) {
            go.update(gc);
        }
    }

    private void handleCollision() {

        Tank player = this.player.getTank();

        // BULLET -
        ArrayList<Bullet> removedBullets = new ArrayList<>();
        for (Bullet bullet : bullets) {

            //MAP BOUNDARIES
            for (Segment segment : framework.getMap().getBounds().getSegments()) {
                if (Collision.testCircleToSegment(bullet.getBounds().getCircles().get(0), segment)) {
                    if (bullet.getRebounds() < bullet.getBulletType().rebounds()) {
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
            for (Block block : framework.getMap().getBlocks()) {
                if (!block.getType().isShootable()) {

                    for (Segment seg : block.getBounds().getSegments()) {
                        if (Collision.testCircleToSegment(bullet.getBounds().getCircles().get(0), seg)) {
                            if (bullet.getRebounds() < bullet.getBulletType().rebounds()) {
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
                if (tank.getBounds().intersects(bullet.getBounds())) {
                    System.out.println("HIT!");
                }
            }

            //OTHER BULLETS
            for (Bullet b : bullets) {
                if (b != bullet) { //Check whether bullet is the same
                    if (bullet.getBounds().intersects(b.getBounds())) {
                        removedBullets.add(bullet);
                    }
                }
            }
        }
        bullets.removeAll(removedBullets);

        //TODO collision TANK - BLOCK
        for (Block block : framework.getMap().getBlocks()) {
            //FRONT LEFT
            if (Collision.testRectangleToPoint(block.getBounds(), player.getFutureBounds().getPoints()[0])) {
                if (player.getVelocity() < 0) {
                    player.setVelocity(0);
                }
                if (player.getVelRotation() < 0) { //TODO
                    player.setVelRotation(0);
                }
            }

            //FRONT RIGHT
            if (Collision.testRectangleToPoint(block.getBounds(), player.getFutureBounds().getPoints()[1])) {
                if (player.getVelocity() < 0) {
                    player.setVelocity(0);
                }
                if (player.getVelRotation() > 0) { //TODO
                    player.setVelRotation(0);
                }
            }

            //BACK RIGHT
            if (Collision.testRectangleToPoint(block.getBounds(), player.getFutureBounds().getPoints()[2])) {
                if (player.getVelocity() > 0) {
                    player.setVelocity(0);
                }
                if (player.getVelRotation() > 0) { //TODO
                    player.setVelRotation(0);
                }
            }

            //BACK LEFT
            if (Collision.testRectangleToPoint(block.getBounds(), player.getFutureBounds().getPoints()[3])) {
                if (player.getVelocity() > 0) {
                    player.setVelocity(0);
                }
                if (player.getVelRotation() < 0) { //TODO
                    player.setVelRotation(0);
                }
            }
        }

        //PICKUP - TANK
        for (PickUp pickUp : pickUps) {
            if (!pickUp.isPickedUp()) {
                if (Collision.testCircleToRectangle(pickUp.getBounds().getCircles().get(0), player.getBounds().getRectangles().get(0))) {
                    pickUp = new PickUp(player);
                }
            }
        }

        //MINE - TANK
        ArrayList<Mine> removedMines = new ArrayList<>();
        for (Mine mine : mines) {
            if (Collision.testCircleToRectangle(mine.getActivationBounds(), player.getBounds().getRectangles().get(0))) {
                if (mine.isActive()) {
                    System.out.println("EXPLOSION!");
                    removedMines.add(mine);
                }
            }
        }
        mines.removeAll(removedMines);

        //TODO MINE - BULLET

    }

    private void handleExplosion() {

    }

    private void handleShot() {

    }

    private void handleMinePlaced() {

    }

    private void handlePickUp() {

    }

    public void spawnTank(float x, float y, float rotation, Player player) {
        Circle circle = new Circle(x, y, 45);

        boolean valid = true;
        for (Block block : framework.getMap().getBlocks()) {
            if (Collision.testCircleToRectangle(circle, block.getBounds())) {
                valid = false;
            }
        }

        if (valid) {
            Tank tank = new Tank(x, y, rotation);
            player.setTank(tank);
            this.tanks.add(tank);
        }
    }

    public void spawnTankRandom(Player player) {

        float x = 0;
        float y = 0;
        boolean valid = true;

        while (valid) {
            x = random.nextInt(Start.WIDTH);
            y = random.nextInt(Start.HEIGHT);

            Circle circle = new Circle(x, y, 45);

            for (Block block : framework.getMap().getBlocks()) {
                if (!Collision.testCircleToRectangle(circle, block.getBounds())) {
                    valid = false;
                }
            }
        }

        Tank tank = new Tank(x, y, 0);
        player.setTank(tank);
        this.tanks.add(tank);
    }

    public void checkObjects() {
        for (GameObject gameObject : gameObjects) {

            switch (gameObject.getType()) {
                case Tank:
                    if (!tanks.contains(gameObject)) {
                        tanks.add((Tank) gameObject);
                    }
                    break;

                case Bullet:
                    if (!bullets.contains(gameObject)) {
                        bullets.add((Bullet) gameObject);
                    }
                    break;

                case Mine:
                    if (!mines.contains(gameObject)) {
                        mines.add((Mine) gameObject);
                    }
                    break;

                case PickUp:
                    if (!pickUps.contains(gameObject)) {
                        pickUps.add((PickUp) gameObject);
                    }
                    break;

            }
        }
    }

    public ArrayList<Tank> getTanks() {
        return tanks;
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

    public void addGameObject(GameObject gameObject) {
        this.gameObjects.add(gameObject);

        checkObjects();
    }

    public void removeGameObject(GameObject gameObject) {
        this.gameObjects.remove(gameObject);

        checkObjects();
    }

    public ArrayList<GameObject> getGameObjects() {
        return gameObjects;
    }
}
