package ch.network;

public interface SocketListener {
    void connected(Connection connection, Packet.Connect packet);
    void received(Connection connection, Packet packet);
    void disconnected(Connection connection);
}
