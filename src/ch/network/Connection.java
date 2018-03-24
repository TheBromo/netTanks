package ch.network;

import ch.framework.ID;

import java.net.InetSocketAddress;
import java.nio.CharBuffer;

public class Connection implements SocketListener {

    private Socket socket;
    private InetSocketAddress receiver;
    private PacketListener packetListener;
    private int ping;

    public Connection(Socket socket, String host, int port) {
        this.socket = socket;
        this.receiver = new InetSocketAddress(host, port);
        socket.setSocketListener(this);
    }

    public Connection(InetSocketAddress receiver) {
        this.receiver = receiver;
    }

    public void setPacketListener(PacketListener packetListener) {
        this.packetListener = packetListener;
    }

    private void ping() {

    }

    public int getPing() {
        return ping;
    }

    public void tick() {

        // TODO: Ping once per second
    }

    public void sendPacket(Packet packet) {
        socket.send(packet, receiver);
    }

    public void sendJoin(String username, String color) {
        Packet packet = new Packet(Packet.Type.JOIN);
        buffer.asCharBuffer().put(username, 4, 4 + 8);
        buffer.asCharBuffer().put(color, 12, 12 + 7);
        send(buffer);
    }

    @Override
    public void received(Packet packet, InetSocketAddress sender) {
        // Packet ID:
        ID id = packet.getID();

        //Packet type:
        Packet.Type type = packet.getType();
        switch (type) {

            case RECEIVED:
                break;

            case PING:
                long time = packet.getContent().getLong();
                // TODO: Connection - Ping
                break;

            case REQUEST:

                break;

            case DISCONNECT:
                break;

            case JOIN:
                CharBuffer charBuffer = buffer.asCharBuffer();
                char[] u = new char[8];
                char[] c = new char[7];
                charBuffer.get(u, 0, 8);
                charBuffer.get(u, 0, 7);
                String username = new String(u);
                String color = new String(c);
                packetListener.handleJoin(this, username, color);
                break;
        }
    }

    public InetSocketAddress getReceiver() {
        return receiver;
    }
}
