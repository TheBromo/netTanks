package ch.network.host;

import ch.framework.Loop;
import ch.framework.Player;
import ch.framework.PlayerActionListener;
import ch.framework.gameobjects.Bullet;
import ch.framework.gameobjects.GameObject;
import ch.framework.gameobjects.Mine;
import ch.framework.gameobjects.tank.Tank;
import ch.framework.map.Block;
import ch.framework.session.Session;

public class HostSession extends Session implements PlayerActionListener {

    private Loop loop;

    public HostSession() {
        loop = new Loop(this::update);
    }

    public void start() {
        loop.run();
    }

    public void update() {
        handler.tick();
    }

    @Override
    public void changeVelocity(float vel, Player player) {

    }

    @Override
    public void changeRotation(float vel, Player player) {

    }

    @Override
    public void changeTurretRotation(float rot, Player player) {

    }

    @Override
    public void shoot(Player player) {

    }

    @Override
    public void place(Player player) {

    }

    @Override
    public void spawn(Player player) {

    }

    // HANDLER ACTIONS //////////////////////////////////////////////////////////

    @Override
    public void onExplosion(GameObject trigger, Mine mine) {

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

}
