package ch.network.packets;

public class JoinPacket extends Packet {

    public String username, color;

    public JoinPacket(String username, String color) {
        this.username = username;
        this.color = color;
    }
}
