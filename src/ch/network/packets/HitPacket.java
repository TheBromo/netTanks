package ch.network.packets;

import java.util.UUID;

public class HitPacket extends Packet {

    public UUID oid;
    public float x, y;

    public HitPacket(UUID id, float x, float y, UUID oid) {
        super(id);
        this.x = x;
        this.y = y;
        this.oid = oid;
    }
}
