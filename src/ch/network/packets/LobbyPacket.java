package ch.network.packets;

public class LobbyPacket extends Packet {

    public JoinPacket[] joinPackets;

    public LobbyPacket(JoinPacket[] joinPackets) {
        this.joinPackets = joinPackets;
    }
}
