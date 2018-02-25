package ch.network.packets;

import ch.framework.gameobjects.Bullet.Type;

import java.util.UUID;

public class ShootPacket extends Packet {

    public float x, y, rot;
    public UUID bullet;
    public Type type;

    public ShootPacket(UUID id, float x, float y, float rot, UUID bullet, Type type) {
        super(id);
        this.x = x;
        this.y = y;
        this.rot = rot;
        this.bullet = bullet;
        this.type = type;
    }
}
