package ch.network.packets;

import java.util.UUID;

public class SpawnPacket extends Packet {

    public float x, y, rot;

    public SpawnPacket(UUID id, float x, float y, float rot) {
        super(id);
        this.x = x;
        this.y = y;
        this.rot = rot;
    }
}
