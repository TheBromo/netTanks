package ch.network.packets;

import java.util.UUID;

public class MousePacket extends Packet {

    public float rot;
    public MousePacket(UUID id, float rot) {
        super(id);
        this.rot = rot;
    }
}
