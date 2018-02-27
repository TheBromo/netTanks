package ch.network.packets;

public class MousePacket extends Packet {

    public float rot;
    public MousePacket(float rot) {
        this.rot = rot;
    }
}
