package ch.framework;

import ch.framework.collision.*;
import ch.framework.effects.BulletTrail;
import ch.framework.effects.Explosion;
import ch.framework.gameobjects.GameObject;
import ch.framework.gameobjects.Mine;
import ch.framework.gameobjects.PickUp;
import ch.framework.gameobjects.Bullet;
import ch.framework.gameobjects.tank.Tank;
import ch.framework.map.Map;
import ch.framework.map.Block;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Handler {

    private ActionListener actionListener;

    private ArrayList<Tank> tanks;
    private ArrayList<Bullet> bullets;
    private ArrayList<Mine> mines;
    private ArrayList<PickUp> pickUps;

    private ArrayList<Tank> removedTanks;
    private ArrayList<Bullet> removedBullets;
    private ArrayList<Mine> removedMines;
    private ArrayList<PickUp> removedPickUps;

    private HashMap<Tank, ArrayList<Bullet>> bulletHashMap;
    private HashMap<Tank, ArrayList<Mine>> mineHashMap;

    private Map map;
    private ArrayList<Block> removedBlocks;

    private HashMap<Bullet, BulletTrail> bulletTrails;
    private HashMap<Mine, Explosion> mineExplosions;

    public Handler() {

        bullets = new ArrayList<>();
        tanks = new ArrayList<>();
        mines = new ArrayList<>();
        pickUps = new ArrayList<>();

        removedTanks = new ArrayList<>();
        removedBullets = new ArrayList<>();
        removedMines = new ArrayList<>();
        removedPickUps = new ArrayList<>();
        removedBlocks = new ArrayList<>();

        bulletHashMap = new HashMap<>();
        mineHashMap = new HashMap<>();

        bulletTrails = new HashMap<>();
        mineExplosions = new HashMap<>();


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

            Tank owner = null;

            for (Tank tank : bulletHashMap.keySet()) {
                if (bulletHashMap.get(tank).contains(bullet)) {
                    owner = tank;
                    break;
                }
            }

            if (!bullet.isActive()) {
                if (!Collision.testCircleToRectangle((Circle) bullet.getBounds(), (Rectangle) owner.getBounds())) {
                    bullet.setActive(true);
                }
            }
        }

        for (Mine mine : mines) {
            mine.tick();
            if (mine.getCounter() == 0) {
                handleExplosion(mine);
                break;
            }

            Tank owner = null;

            for (Tank tank : mineHashMap.keySet()) {
                if (mineHashMap.get(tank).contains(mine)) {
                    owner = tank;
                    break;
                }
            }

            if (!mine.isActive()) {
                if (!Collision.testCircleToRectangle((Circle) mine.getBounds(), (Rectangle) owner.getBounds())) {
                    mine.setActive(true);
                }
            }
        }

        for (Mine mine : mineExplosions.keySet()) {
            mineExplosions.get(mine).tick();
            if (mineExplosions.get(mine).isExpired()) {
                mineExplosions.remove(mine);
            }
        }


        for (PickUp pickUp : pickUps) {
            pickUp.update();
            if (pickUp.isExpired()) {
                removedPickUps.add(pickUp);
            }
        }

        tanks.removeAll(removedTanks);
        bullets.removeAll(removedBullets);
        mines.removeAll(removedMines);
        pickUps.removeAll(removedPickUps);
        map.getBlocks().removeAll(removedBlocks);
    }

    private void handleCollision() {

        // BULLET -
        for (Bullet bullet : bullets) {

            if (bullet.isActive()) {
                collision:
                {

                    //MAP BOUNDARIES
                    for (Segment segment : map.getBounds().getSegments()) {
                        if (Collision.testCircleToSegment((Circle) bullet.getBounds(), segment)) {
                            handleRebound(segment, bullet);
                            break collision;
                        }
                    }

                    //MAP BLOCKS
                    for (Block block : map.getBlocks()) {

                        if (block.getType() == Block.Type.STANDARD || block.getType() == Block.Type.CORK) {

                            for (Segment segment : block.getBounds().getSegments()) {
                                if (Collision.testCircleToSegment((Circle) bullet.getBounds(), segment)) {

                                    Point corner = null;

                                    for (Point point : block.getBounds().getPoints()) {
                                        if (Collision.testCircleToPoint((Circle) bullet.getBounds(), point)) {
                                            corner = point;
                                            break;
                                        }
                                    }

                                    if (corner != null) {
                                        Segment line = new Segment(corner, bullet.getLocation());

                                        if (line.getAngle() <= bullet.getRotation()) {
                                            handleRebound(block.getBounds().getSegmentsOfPoint(corner)[0], bullet);
                                        } else {
                                            handleRebound(block.getBounds().getSegmentsOfPoint(corner)[1], bullet);
                                        }

                                        bullet.setActive(false);
                                    } else {
                                        handleRebound(segment, bullet);
                                        break collision;
                                    }
                                }
                            }

                        }
                    }

                    //TANKS
                    for (Tank tank : tanks) {
                        if (Collision.testCircleToRectangle((Circle) bullet.getBounds(), (Rectangle) tank.getBounds())) {
                            handleHit(bullet, tank);
                            break collision;
                        }
                    }

                    //OTHER BULLETS
                    for (Bullet b : bullets) {
                        if (b != bullet) { //Check whether bullet is the same
                            if (Collision.testCircleToCircle((Circle) bullet.getBounds(), (Circle) b.getBounds())) {
                                removeBullet(bullet);
                                break collision;
                            }
                        }
                    }

                    //MINES
                    for (Mine mine : mines) {
                        if (Collision.testCircleToCircle((Circle) bullet.getBounds(), (Circle) mine.getBounds())) {
                            removeBullet(bullet);
                            handleExplosion(mine);
                            break collision;
                        }
                    }
                }
            }
        }

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
            for (Mine mine : mines) {
                if (Collision.testCircleToRectangle((Circle) mine.getBounds(), (Rectangle) player.getBounds())) {
                    if (mine.isActive()) {
                        handleExplosion(mine);
                    }
                }
            }

        }

    }

    private void handleRebound(Segment segment, Bullet bullet) {
        if (bullet.getRebounds() < bullet.getType().rebounds()) {
            //Rebound
            bullet.rebound(bullet.getX(), bullet.getY(), (float) segment.getAngle()); //TODO
        } else {
            removeBullet(bullet);
        }
    }

    public void handleHit(Bullet bullet, Tank tank) {
        if (bullet.isActive()) {
            removedTanks.add(tank);
            removeBullet(bullet);
            actionListener.onHit(tank, bullet);
        }
    }

    public void handleExplosion(Mine mine) {
        if (mine.isActive()) {

            // CHECK FOR RADIUS
            for (Tank tank : tanks) {
                if (Collision.testCircleToRectangle(mine.getExplosionBounds(), (Rectangle) tank.getBounds())) {
                    System.out.println("This guy is dead...");
                    removedTanks.add(tank);
                }
            }

            for (Bullet bullet : bullets) {
                if (Collision.testCircleToCircle(mine.getExplosionBounds(), (Circle) bullet.getBounds())) {
                    removeBullet(bullet);
                }
            }

            for (Block block : map.getBlocks()) {
                if (block.getType().isExplosionDamage()) {
                    if (Collision.testCircleToRectangle(mine.getExplosionBounds(), (Rectangle) block.getBounds())) {
                        block.setDestroyed(true);
                        removedBlocks.add(block);
                    }
                }
            }

            mineExplosions.put(mine, new Explosion(mine.getX(), mine.getY()));
            removedMines.add(mine);
        }
    }

    public void handlePickUp() {

    }

    public void handleShot(Tank tank) {
        if (tank.isAlive()) {
            Bullet bullet = new Bullet(tank.getTurret().getMuzzleX(), tank.getTurret().getMuzzleY(), tank.getTurret().getRotation(), tank.getBulletType());
            bullets.add(bullet);
            bulletHashMap.get(tank).add(bullet);
            bulletTrails.put(bullet, new BulletTrail(bullet));
//        System.out.println("Pew! " + turret.getRotation());
        }
    }

    public void handleMinePlaced(Tank tank) {
        if (tank.isAlive()) {
            Mine mine = new Mine(tank.getX(), tank.getY());
            mines.add(mine);
            mineHashMap.get(tank).add(mine);
        }
    }

    private void removeBullet(Bullet bullet) {
        removedBullets.add(bullet);
        bulletTrails.remove(bullet);
    }

    public Tank spawnTank(float x, float y, float rotation) {
        Tank tank = new Tank(x, y, rotation);
        addTank(tank);
        return tank;
    }

    public void addTank(Tank tank) {
        bulletHashMap.put(tank, new ArrayList<>());
        mineHashMap.put(tank, new ArrayList<>());
        tanks.add(tank);
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

    public ArrayList<Tank> getRemovedTanks() {
        return removedTanks;
    }

    public ArrayList<Bullet> getRemovedBullets() {
        return removedBullets;
    }

    public ArrayList<Mine> getRemovedMines() {
        return removedMines;
    }

    public ArrayList<PickUp> getRemovedPickUps() {
        return removedPickUps;
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

    public HashMap<Bullet, BulletTrail> getBulletTrails() {
        return bulletTrails;
    }

    public HashMap<Mine, Explosion> getMineExplosions() {
        return mineExplosions;
    }

    public void setActionListener(ActionListener actionListener) {
        this.actionListener = actionListener;
    }
}
