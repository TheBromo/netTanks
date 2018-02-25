package ch.network.packets;

import java.util.UUID;

public class HitPacket extends Packet {

    public float x, y;

    public HitPacket(UUID id, float x, float y) {
        super(id);
        this.x = x;
        this.y = y;
    }
}
