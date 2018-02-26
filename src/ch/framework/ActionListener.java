package ch.framework;

import ch.framework.gameobjects.Bullet;
import ch.framework.gameobjects.GameObject;
import ch.framework.gameobjects.Mine;
import ch.framework.gameobjects.tank.Tank;
import ch.framework.map.Block;

public interface ActionListener {

    void onKill(GameObject trigger, Tank tank);
    void onBlockDestroyed(GameObject trigger, Block block);
    //void onRebound(Bullet bullet);
    void onBulletBreak(Bullet b1);
    void onExplosion(GameObject trigger, Mine mine);
    //void onMove();
    //void onPickUp();
    //void onMinePlaced();
    //void onShoot();
    //void onSpawn(Tank tank);

}
