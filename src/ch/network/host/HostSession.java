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

    // PLAYER ACTIONS //////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void handleVelocityChanged(float vel, Player player) {

    }

    @Override
    public void handleRotationChanged(float vel, Player player) {

    }

    @Override
    public void handleTurretRotationChanged(float rot, Player player) {

    }

    @Override
    public void handleShot(Player player) {

    }

    @Override
    public void handlePlace(Player player) {

    }

    @Override
    public void handleSpawn(Player player) {

    }

    // HANDLER ACTIONS /////////////////////////////////////////////////////////////////////////////////////////////////

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
