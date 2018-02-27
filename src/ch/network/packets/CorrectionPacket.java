package ch.network.packets;

public class CorrectionPacket extends Packet {

    public float x, y, rot, trot;

    public CorrectionPacket(float x, float y, float rot, float trot) {
        this.x = x;
        this.y = y;
        this.rot = rot;
        this.trot = trot;
    }
}
