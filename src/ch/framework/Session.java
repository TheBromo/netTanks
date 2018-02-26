package ch.framework;

import ch.framework.gameobjects.Bullet;
import ch.framework.gameobjects.GameObject;
import ch.framework.gameobjects.Mine;
import ch.framework.gameobjects.tank.Tank;
import ch.framework.map.Block;

import java.util.ArrayList;
import java.util.UUID;

public class Session implements ActionListener {

    protected int ticks;
    protected Player player;
    protected ArrayList<Player> players;

    protected Handler handler;

    public Session(String username, String color) {
        players = new ArrayList<>();
        this.player = new Player(username, color, UUID.randomUUID());
        players.add(player);

        handler = new Handler();
        handler.setActionListener(this);

        System.out.println("ID: " + player.getId());
    }

    public void tick() {
        handler.update();
        ticks++;
    }


    // PLAYER MOVEMENT /////////////////////////////////////////////////////

    public void spawn() {

    }

    public void setVelocity(float velocity) {

    }

    public void setVelRotation(float velocity) {

    }

    public void mouseMoved(float rot) {

    }

    public void shoot() {

    }

    public void place() {

    }

    public void pickUp() {

    }

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
        System.out.println("Muahahaha");
    }

    // GETTERS & SETTERS ////////////////////////////////////////////////////////

    public Player getPlayer() {
        return player;
    }

    public Player getPlayer(UUID id) {
        for (Player player : players) {
            if (id.compareTo(player.getId()) == 0) {
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
        return getPlayer(id).getTank();
    }

    public Handler getHandler() {
        return handler;
    }

}