package ch.network.packets;

import java.util.UUID;

public class CorrectionPacket extends Packet {

    public float x, y, rot, trot;

    public CorrectionPacket(UUID id, float x, float y, float rot, float trot) {
        super(id);
        this.x = x;
        this.y = y;
        this.rot = rot;
        this.trot = trot;
    }
}
