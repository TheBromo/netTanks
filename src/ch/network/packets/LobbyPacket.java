package ch.network.packets;

public class LobbyPacket extends Packet {

    public JoinPacket[] joinPackets;

    public LobbyPacket(JoinPacket[] joinPackets) {
        super(null);
        this.joinPackets = joinPackets;
    }
}
