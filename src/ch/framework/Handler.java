package ch.framework;

import ch.framework.collision.*;
import ch.framework.effects.BulletTrail;
import ch.framework.effects.Explosion;
import ch.framework.gameobjects.Bullet;
import ch.framework.gameobjects.GameObject;
import ch.framework.gameobjects.Mine;
import ch.framework.gameobjects.PickUp;
import ch.framework.gameobjects.tank.Tank;
import ch.framework.map.Block;
import ch.framework.map.Map;

import java.util.ArrayList;
import java.util.HashMap;

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

    private Map map;
    private ArrayList<Block> removedBlocks;

    public Handler(Map map) {
        this.map = map;
        bullets = new ArrayList<>();
        tanks = new ArrayList<>();
        mines = new ArrayList<>();
        pickUps = new ArrayList<>();

        removedTanks = new ArrayList<>();
        removedBullets = new ArrayList<>();
        removedMines = new ArrayList<>();
        removedPickUps = new ArrayList<>();
        removedBlocks = new ArrayList<>();
    }

    public void tick() {
        //Check for any collisions before updating
        handleCollision();

        tanks.forEach(Tank::update);
        bullets.forEach(bullet -> {
            bullet.update();
            bullet.setActive(true);
        });
        mines.forEach(mine -> {
            mine.update();
            mine.setActive(true);
            if (mine.isExploded() && actionListener != null) actionListener.onExplosion(null, mine);
        });

        // TODO check if Bullets and Mines are not inside owner Tank


        // REMOVE REMOVED OBJECTS
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
//                    for (Segment segment : map.getBounds().getSegments()) {
//                        if (Collision.testCircleToSegment((Circle) bullet.getBounds(), segment)) {
//                            handleRebound(segment, bullet);
//                            break collision;
//                        }
//                    }

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
                            actionListener.onKill(bullet, tank);
                            break collision;
                        }
                    }

                    //OTHER BULLETS
                    for (Bullet b : bullets) {
                        if (b != bullet) { //Check whether bullet is the same
                            if (Collision.testCircleToCircle((Circle) bullet.getBounds(), (Circle) b.getBounds())) {
                                actionListener.onBulletBreak(bullet);
                                break collision;
                            }
                        }
                    }

                    //MINES
                    for (Mine mine : mines) {
                        if (Collision.testCircleToCircle((Circle) bullet.getBounds(), (Circle) mine.getBounds())) {
                            actionListener.onBulletBreak(bullet);
                            break collision;
                        }
                    }
                }
            }
        }

        //TODO collision TANK -
        for (Tank tank : tanks) {

            // MAP
            for (Block block : map.getBlocks()) {
                //FRONT LEFT
                if (Collision.testRectangleToPoint(block.getBounds(), tank.getFutureBounds().getPoints()[0])) {
                    if (tank.getVelocity() < 0) {
                        tank.setVelocity(0);
                    }
                    if (tank.getVelRotation() < 0) { //TODO
                        tank.setVelRotation(0);
                    }
                }

                //FRONT RIGHT
                if (Collision.testRectangleToPoint(block.getBounds(), tank.getFutureBounds().getPoints()[1])) {
                    if (tank.getVelocity() < 0) {
                        tank.setVelocity(0);
                    }
                    if (tank.getVelRotation() > 0) { //TODO
                        tank.setVelRotation(0);
                    }
                }

                //BACK RIGHT
                if (Collision.testRectangleToPoint(block.getBounds(), tank.getFutureBounds().getPoints()[2])) {
                    if (tank.getVelocity() > 0) {
                        tank.setVelocity(0);
                    }
                    if (tank.getVelRotation() > 0) { //TODO
                        tank.setVelRotation(0);
                    }
                }

                //BACK LEFT
                if (Collision.testRectangleToPoint(block.getBounds(), tank.getFutureBounds().getPoints()[3])) {
                    if (tank.getVelocity() > 0) {
                        tank.setVelocity(0);
                    }
                    if (tank.getVelRotation() < 0) { //TODO
                        tank.setVelRotation(0);
                    }
                }
            }

            //MINE
            for (Mine mine : mines) {
                if (Collision.testCircleToRectangle((Circle) mine.getBounds(), (Rectangle) tank.getBounds())) {
                    if (mine.isActive()) {
                        actionListener.onExplosion(tank, mine);
                    }
                }
            }
        }

    }

    // PUBLIC HANDLERS ////////////////////////////////////////////////////////////////////////

//    public void handleHit(Bullet bullet, Tank tank) {
//        if (bullet.isActive()) {
//            removedTanks.add(tank);
//            removeBullet(bullet);
//        }
//    }

    public void handleRebound(Segment segment, Bullet bullet) {
        if (bullet.getRebounds() < bullet.getType().rebounds()) {
            //Rebound
            bullet.rebound(bullet.getX(), bullet.getY(), (float) segment.getAngle()); //TODO
        } else {
            this.removedBullets.add(bullet);
        }
    }

    public void handleShot(Tank tank, Bullet bullet) {
        if (tank.isAlive()) {
            bullets.add(bullet);
        }
    }

    public void handleMinePlaced(Tank tank, Mine mine) {
        if (tank.isAlive()) {
            mines.add(mine);
        }
    }

    public void handleBulletBreak(Bullet bullet) {
        removedBullets.add(bullet);
    }

    public void handleExplosion(GameObject trigger, Mine mine) {
        if (mine.isActive()) {

            // CHECK FOR RADIUS
            for (Tank tank : tanks) {
                if (Collision.testCircleToRectangle(mine.getExplosionBounds(), (Rectangle) tank.getBounds())) {
                    actionListener.onKill(mine, tank);
                }
            }

            for (Bullet bullet : bullets) {
                if (Collision.testCircleToCircle(mine.getExplosionBounds(), (Circle) bullet.getBounds())) {
                    actionListener.onBulletBreak(bullet);
                }
            }

            for (Block block : map.getBlocks()) {
                if (block.getType().isExplosionDamage()) {
                    if (Collision.testCircleToRectangle(mine.getExplosionBounds(), (Rectangle) block.getBounds())) {
                        actionListener.onBlockDestroyed(mine, block);
                    }
                }
            }

            removedMines.add(mine);
        }
    }

    public void handleBlockDestroyed(GameObject trigger, Block block) {
        removedBlocks.add(block);
    }

    public void spawnTank(Tank tank) {
        tanks.add(tank);
    }

    public void removeTank(Tank tank) {
        removedTanks.add(tank);
    }

    // GETTERS & SETTERS //////////////////////////////////////////////////////

    public Tank getTank(ID id) {
        return tanks.stream().filter(tank -> tank.getId().equals(id)).findFirst().orElse(null);
    }

    public Bullet getBullet(ID id) {
        return bullets.stream().filter(bullet -> bullet.getId().equals(id)).findFirst().orElse(null);
    }

    public Mine getMine(ID id) {
        return mines.stream().filter(mine -> mine.getId().equals(id)).findFirst().orElse(null);
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

    public void setActionListener(ActionListener actionListener) {
        this.actionListener = actionListener;
    }

}
