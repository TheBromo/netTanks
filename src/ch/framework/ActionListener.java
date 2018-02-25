package ch.framework;

import ch.framework.gameobjects.Bullet;
import ch.framework.gameobjects.Mine;
import ch.framework.gameobjects.tank.Tank;

public interface ActionListener {

    void onHit(Tank tank, Bullet bullet);
    void onExplosion(Tank tank, Mine mine);
    //void onMove();
    //void onPickUp();
    //void onMinePlaced();
    //void onShoot();
    //void onSpawn(Tank tank);

}
