package ch.framework;

import ch.framework.gameobjects.Bullet;
import ch.framework.gameobjects.GameObject;
import ch.framework.gameobjects.Mine;
import ch.framework.gameobjects.tank.Tank;
import ch.framework.map.Block;

import java.util.ArrayList;
import java.util.UUID;

public abstract class Session implements ActionListener {

    protected int ticks;
    protected Player player;
    protected ArrayList<Player> players;

    protected Handler handler;

    public Session(String username, String color) {
        players = new ArrayList<>();
        this.player = new Player(username, color, 0);
        players.add(player);

        handler = new Handler();
        handler.setActionListener(this);
    }

    public void tick() {
        handler.update();
        ticks++;
        update();
    }

    public abstract void update();


    // PLAYER MOVEMENT /////////////////////////////////////////////////////

    public abstract void spawn();

    public abstract void setVelocity(float velocity);

    public abstract void setVelRotation(float velocity);

    public abstract void mouseMoved(float rot);

    public abstract void shoot();

    public abstract void place();

    public abstract void pickUp();

    // HANDLER ACTIONS //////////////////////////////////////////////////////////

    @Override
    public void onExplosion(GameObject trigger, Mine mine) {
        if (trigger != null) {

            if (trigger instanceof Bullet) {

            }

            if (trigger instanceof Tank) {

            }

        } else {

        }
    }

    @Override
    public void onBulletBreak(Bullet b1) {

    }

    @Override
    public void onKill(GameObject trigger, Tank tank) {

    }

    @Override
    public void onBlockDestroyed(GameObject trigger, Block block) {

    }

    // GETTERS & SETTERS ////////////////////////////////////////////////////////

    public Player getPlayer() {
        return player;
    }

    public Player getPlayer(int id) {
        for (Player player : players) {
            if (id == player.getId()) {
                return player;
            }
        }

        return null;
    }

    public Bullet getBullet(UUID id) {
        for (Bullet bullet : handler.getBullets()) {
            if (id.compareTo(bullet.getId()) == 0) {
                return bullet;
            }
        }

        return null;
    }

    public Tank getTank(UUID id) {
        for (Tank tank : handler.getTanks()) {
            if (id.compareTo(tank.getId()) == 0) {
                return tank;
            }
        }

        return null;
    }

    public Handler getHandler() {
        return handler;
    }

}