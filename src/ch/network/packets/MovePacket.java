package ch.network.packets;

import java.util.UUID;

public class MovePacket extends Packet {

    public float x, y, rot, mx, my;

    public MovePacket(UUID id, float x, float y, float rot, float mx, float my) {
        super(id);
        this.x = x;
        this.y = y;
        this.rot = rot;
        this.mx = mx;
        this.my = my;
    }
}
