package ch.network.packets;

import java.util.UUID;

public class SpawnPacket extends Packet {

    public UUID tank;
    public float x, y, rot;

    public SpawnPacket(UUID tank, float x, float y, float rot) {
        this.tank = tank;
        this.x = x;
        this.y = y;
        this.rot = rot;
    }
}
