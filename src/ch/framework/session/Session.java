package ch.framework.session;

import ch.framework.ActionListener;
import ch.framework.Handler;
import ch.framework.Player;
import ch.framework.gameobjects.Bullet;
import ch.framework.gameobjects.GameObject;
import ch.framework.gameobjects.Mine;
import ch.framework.gameobjects.tank.Tank;
import ch.framework.map.Block;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public abstract class Session implements ActionListener {

    protected int ticks;
    protected List<Player> players;

    protected Handler handler;

    public Session() {
        players = new ArrayList<>();
        handler = new Handler();
        handler.setActionListener(this);
    }

    public void tick() {
        handler.tick();
        ticks++;
    }

    // HANDLER ACTIONS //////////////////////////////////////////////////////////

    @Override
    public abstract void onExplosion(GameObject trigger, Mine mine);

    @Override
    public abstract void onBulletBreak(Bullet b1);

    @Override
    public abstract void onKill(GameObject trigger, Tank tank);

    @Override
    public abstract void onBlockDestroyed(GameObject trigger, Block block);

    // GETTERS & SETTERS ////////////////////////////////////////////////////////

    public void addPlayer(Player player) {
        players.add(player);
    }

    public void removePlayer(Player player) {
        players.remove(player);
    }

    public List<Player> getPlayers() {
        return players;
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