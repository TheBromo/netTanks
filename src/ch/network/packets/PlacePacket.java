package ch.network.packets;

import java.util.UUID;

public class PlacePacket extends Packet {

    public UUID mine;
    public float x, y;

    public PlacePacket(UUID id, float x, float y, UUID mine) {
        super(id);
        this.x = x;
        this.y = y;
        this.mine = mine;
    }
}
