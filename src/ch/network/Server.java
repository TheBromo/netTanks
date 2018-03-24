package ch.network;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.util.ArrayList;

public class Server implements SocketListener {

    private Socket socket;
    private ArrayList<Connection> connections;
    private PacketListener packetListener;

    public Server(int port) {
        socket = new Socket(port);
        connections = new ArrayList<>();
    }

    public void setPacketListener(PacketListener packetListener) {
        this.packetListener = packetListener;
        for (Connection connection : connections) {
            connection.setPacketListener(packetListener);
        }
    }

    public void tick() {

    }

    @Override
    public void received(Packet packet, InetSocketAddress sender) {

        // New Connection
        if (packet.getType() == Packet.Type.REQUEST) {
            // Connection attempt
            Connection connection = new Connection(sender);
            connections.add(connection);

            Packet ack = new Packet(Packet.Type.ACK);
            ack.getContent().put()
            connection.;

        }
    }

}
