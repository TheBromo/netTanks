package ch.framework;

import ch.framework.gameobjects.Bullet;
import ch.framework.gameobjects.GameObject;
import ch.framework.gameobjects.Mine;
import ch.framework.gameobjects.tank.Tank;

public interface ActionListener {

    void onHit(Tank tank, Bullet bullet);
    //void onRebound(Bullet bullet);
    void onBulletBreak(Bullet b1);
    void onExplosion(GameObject trigger, Mine mine);
    //void onMove();
    //void onPickUp();
    //void onMinePlaced();
    //void onShoot();
    //void onSpawn(Tank tank);

}
