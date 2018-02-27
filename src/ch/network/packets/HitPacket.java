package ch.network.packets;

public class HitPacket extends Packet {

    public float x, y;

    public HitPacket(float x, float y) {
        this.x = x;
        this.y = y;
    }
}
