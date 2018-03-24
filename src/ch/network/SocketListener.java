package ch.network;

import java.net.InetSocketAddress;

public interface SocketListener {
    void received(Packet packet, InetSocketAddress sender);
}
