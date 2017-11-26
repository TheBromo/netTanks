package ch.framework;

import ch.framework.collision.Circle;
import ch.framework.collision.Collision;
import ch.framework.collision.Segment;
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

    private ArrayList<Tank> tanks;
    private ArrayList<Bullet> bullets;
    private ArrayList<Mine> mines;
    private ArrayList<PickUp> pickUps;

    public Handler(Framework framework) {
        this.framework = framework;
        player = framework.getPlayer();
        random = new Random();

        bullets = new ArrayList<>();
        tanks = new ArrayList<>();
        mines = new ArrayList<>();
        pickUps = new ArrayList<>();
    }

    public void update(GraphicsContext gc) {

        //Check for any collisions before updating
        handleCollision();

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
    }

    private void handleCollision() {

        Tank player = this.player.getTank();

        // BULLET -
        ArrayList<Bullet> removedBullets = new ArrayList<>();
        for (Bullet bullet : bullets) {

            //MAP BOUNDARIES
            for (Segment segment : framework.getMap().getBounds().getSegments()) {
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
            for (Block block : framework.getMap().getBlocks()) {
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
        for (Block block : framework.getMap().getBlocks()) {
            //FRONT LEFT
            if (Collision.testRectangleToPoint(block.getBounds(), player.getFutureBounds().getPoints().get(0))) {
                if (player.getVelocity() < 0) {
                    player.setVelocity(0);
                }
                if (player.getVelRotation() < 0) { //TODO
                    player.setVelRotation(0);
                }
            }

            //FRONT RIGHT
            if (Collision.testRectangleToPoint(block.getBounds(), player.getFutureBounds().getPoints().get(1))) {
                if (player.getVelocity() < 0) {
                    player.setVelocity(0);
                }
                if (player.getVelRotation() > 0) { //TODO
                    player.setVelRotation(0);
                }
            }

            //BACK RIGHT
            if (Collision.testRectangleToPoint(block.getBounds(), player.getFutureBounds().getPoints().get(2))) {
                if (player.getVelocity() > 0) {
                    player.setVelocity(0);
                }
                if (player.getVelRotation() > 0) { //TODO
                    player.setVelRotation(0);
                }
            }

            //BACK LEFT
            if (Collision.testRectangleToPoint(block.getBounds(), player.getFutureBounds().getPoints().get(3))) {
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
}
