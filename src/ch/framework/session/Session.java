package ch.framework.session;

import ch.framework.*;
import ch.framework.gameobjects.Bullet;
import ch.framework.gameobjects.GameObject;
import ch.framework.gameobjects.Mine;
import ch.framework.gameobjects.tank.Tank;
import ch.framework.map.Block;
import ch.framework.map.Map;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Session implements ActionListener, PlayerActionListener {

    private Player player;
    private List<Player> players;

    private Handler handler;

    public Session(Player player) {
        this.player = player;
        this.player.setListener(this);
        players = new ArrayList<>();
        handler = new Handler(new Map(Map.Maps.MAP1));
        handler.setActionListener(this);
    }

    public void tick() {
        handler.tick();
    }

    // HANDLER ACTIONS //////////////////////////////////////////////////////////

    @Override
    public void onExplosion(GameObject trigger, Mine mine) {
        handler.handleExplosion(trigger, mine);
    }

    @Override
    public void onBulletBreak(Bullet bullet) {
        handler.handleBulletBreak(bullet);
    }

    @Override
    public void onKill(GameObject trigger, Tank tank) {
        System.out.println("Killed");
    }

    @Override
    public void onBlockDestroyed(GameObject trigger, Block block) {
        handler.handleBlockDestroyed(trigger, block);
    }

    // PLAYER ACTIONS ///////////////////////////////////////////////////////////

    @Override
    public void handleVelocityChanged(float vel, Player player) {
        Tank tank = player.getTank();
        tank.setVelocity(vel);
    }

    @Override
    public void handleRotationChanged(float vel, Player player) {
        Tank tank = player.getTank();
        tank.setVelRotation(vel);
    }

    @Override
    public void handleTurretRotationChanged(float rot, Player player) {
        Tank tank = player.getTank();
        tank.getTurret().setRotation(rot);
    }

    @Override
    public void handleShot(Player player) {
        Tank tank = player.getTank();
        Bullet bullet = new Bullet(ID.randomID(), tank.getTurret().getMuzzleX(), tank.getTurret().getMuzzleY(), tank.getTurret().getRotation(), tank.getBulletType());
        handler.handleShot(tank, bullet);
        player.addBullet(bullet);
    }

    @Override
    public void handlePlace(Player player) {
        Tank tank = player.getTank();
        Mine mine = new Mine(ID.randomID(), tank.getX(), tank.getY());
        handler.handleMinePlaced(tank, mine);
        player.addMine(mine);
    }

    @Override
    public void handleSpawn(Player player) {
        Tank tank = new Tank(ID.randomID(), 100, 100, 0);
        tank.setColor(player.getColor());
        handler.spawnTank(tank);
        player.setTank(tank);
    }

    // GETTERS & SETTERS ////////////////////////////////////////////////////////

    public void addPlayers(Player... players) {
        for (Player player : players) {
            player.setListener(this);
            this.players.add(player);
        }
    }

    public void removePlayer(Player player) {
        players.remove(player);
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public Player getPlayer(ID id) {
        for (Player player : players) {
            if (id == player.getId()) {
                return player;
            }
        }

        return null;
    }

    public Handler getHandler() {
        return handler;
    }

}