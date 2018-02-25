package ch.network.packets;

import java.util.UUID;

public class VelocityPacket extends Packet {

    public float vel, velRot;

    public VelocityPacket(UUID id, float vel, float velRot) {
        super(id);
        this.vel = vel;
        this.velRot = velRot;
    }
}
