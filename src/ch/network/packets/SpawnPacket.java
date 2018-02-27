package ch.network.packets;

public class SpawnPacket extends Packet {

    public float x, y, rot;

    public SpawnPacket(float x, float y, float rot) {
        this.x = x;
        this.y = y;
        this.rot = rot;
    }
}
