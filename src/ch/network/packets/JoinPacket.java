package ch.network.packets;

import java.util.UUID;

public class JoinPacket extends Packet {

    public String username, color;

    public JoinPacket(UUID id, String username, String color) {
        super(id);
        this.username = username;
        this.color = color;
    }
}
