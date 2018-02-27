package ch.network.packets;

public class VelocityPacket extends Packet {

    public float vel, velRot;

    public VelocityPacket(float vel, float velRot) {
        this.vel = vel;
        this.velRot = velRot;
    }
}
