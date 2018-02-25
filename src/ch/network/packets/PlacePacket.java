package ch.network.packets;

import java.util.UUID;

public class PlacePacket extends Packet {

    public float x, y;

    public PlacePacket(UUID id, float x, float y) {
        super(id);
        this.x = x;
        this.y = y;
    }
}
