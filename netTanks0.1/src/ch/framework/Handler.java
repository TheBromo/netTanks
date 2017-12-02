package ch.framework;

import ch.framework.collision.Circle;
import ch.framework.collision.Collision;
import ch.framework.collision.Rectangle;
import ch.framework.collision.Segment;
import ch.framework.gameobjects.GameObject;
import ch.framework.gameobjects.Mine;
import ch.framework.gameobjects.PickUp;
import ch.framework.gameobjects.Bullet;
import ch.framework.gameobjects.tank.Tank;
import ch.framework.map.Map;
import ch.framework.map.Block;
import ch.match.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Handler {

    private Random random;

    private ArrayList<Tank> tanks;
    private ArrayList<Bullet> bullets;
    private ArrayList<Mine> mines;
    private ArrayList<PickUp> pickUps;

    private HashMap<Tank, Bullet> bulletHashMap;

    private Map map;

    public Handler() {
        random = new Random();

        bullets = new ArrayList<>();
        tanks = new ArrayList<>();
        mines = new ArrayList<>();
        pickUps = new ArrayList<>();

        bulletHashMap = new HashMap<>();

        this.map = new Map(Map.Maps.MAP1);
    }

    public void update() {
        //Check for any collisions before updating
        handleCollision();

        for (Tank tank : tanks) {
            tank.update();
        }

        for (Bullet bullet : bullets) {
            bullet.update();
        }

        for (Mine mine : mines) {
            mine.update();
        }

        PickUp removedPickUp = null;
        for (PickUp pickUp : pickUps) {
            pickUp.update();
            if (pickUp.isExpired()) {
                removedPickUp = pickUp;
            }
        }
        pickUps.remove(removedPickUp);

    }

    private void handleCollision() {

        // BULLET -
        ArrayList<Bullet> removedBullets = new ArrayList<>();
        for (Bullet bullet : bullets) {

            //MAP BOUNDARIES
            for (Segment segment : map.getBounds().getSegments()) {
                if (Collision.testCircleToSegment((Circle) bullet.getBounds(), segment)) {
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
                        if (Collision.testCircleToSegment((Circle) bullet.getBounds(), seg)) {
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
                if (Collision.testCircleToRectangle((Circle) bullet.getBounds(), (Rectangle) tank.getBounds())) {
                    System.out.println("HIT!");
                }
            }

            //OTHER BULLETS
            for (Bullet b : bullets) {
                if (b != bullet) { //Check whether bullet is the same
                    if (Collision.testCircleToCircle((Circle) bullet.getBounds(), (Circle) b.getBounds())) {
                        removedBullets.add(bullet);
                    }
                }
            }
        }
        bullets.removeAll(removedBullets);

        //TODO collision TANK -
        for (Tank player : tanks) {
            for (Block block : map.getBlocks()) {
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

            //PICKUP
            for (PickUp pickUp : pickUps) {
                if (!pickUp.isPickedUp()) {
                    if (Collision.testCircleToRectangle((Circle) pickUp.getBounds(), (Rectangle) player.getBounds())) {
                        pickUp = new PickUp(player);
                    }
                }
            }

            //MINE
            ArrayList<Mine> removedMines = new ArrayList<>();
            for (Mine mine : mines) {
                if (Collision.testCircleToRectangle(mine.getActivationBounds(), (Rectangle) player.getBounds())) {
                    if (mine.isActive()) {
                        System.out.println("EXPLOSION!");
                        removedMines.add(mine);
                    }
                }
            }
            mines.removeAll(removedMines);

        }
        //TODO MINE - BULLET

    }

    private void handleExplosion() {

    }

    private void handlePickUp() {

    }

    public void handleShot(Tank tank) {
        Bullet bullet = new Bullet(tank.getTurret().getMuzzleX(), tank.getTurret().getMuzzleY(), tank.getTurret().getRotation(), tank.getBulletType());
        bullets.add(bullet);
        bulletHashMap.put(tank, bullet);
//        System.out.println("Pew! " + turret.getRotation());
    }

    public void handleMinePlaced() {

    }



    public void spawnTank(float x, float y, float rotation, Player player) {
        Circle circle = new Circle(x, y, 45);

        boolean valid = true;
        for (Block block : map.getBlocks()) {
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

            for (Block block : map.getBlocks()) {
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

    public ArrayList<GameObject> getGameObjects() {
        ArrayList<GameObject> gameObjects = new ArrayList<>();

        gameObjects.addAll(tanks);
        gameObjects.addAll(bullets);
        gameObjects.addAll(mines);
        gameObjects.addAll(pickUps);

        return gameObjects;
    }

    public Map getMap() {
        return map;
    }
}
