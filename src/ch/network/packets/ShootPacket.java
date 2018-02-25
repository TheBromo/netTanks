package ch.network.packets;

import java.util.UUID;

public class ShootPacket extends Packet {

    public float x, y, rot;

    public ShootPacket(UUID id, float x, float y, float rot) {
        super(id);
        this.x = x;
        this.y = y;
        this.rot = rot;
    }
}
