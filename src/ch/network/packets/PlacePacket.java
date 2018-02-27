package ch.network.packets;

import java.util.UUID;

public class PlacePacket extends Packet {

    public UUID mine;
    public float x, y;

    public PlacePacket(float x, float y, UUID mine) {
        this.x = x;
        this.y = y;
        this.mine = mine;
    }
}
