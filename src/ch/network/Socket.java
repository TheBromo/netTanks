package ch.network;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class Socket {

    protected SocketListener socketListener;
    protected InetSocketAddress socket;
    protected DatagramChannel channel;

    public Socket(int port) {
        try {
            this.channel = DatagramChannel.open();
            this.channel.configureBlocking(false);
            this.socket = new InetSocketAddress(port);
            this.channel.bind(socket);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setSocketListener(SocketListener socketListener) {
        this.socketListener = socketListener;
    }

    public void close() {
        try {
            channel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void send(Packet packet, InetSocketAddress receiver) {
        try {
            ByteBuffer buffer = packet.getBuffer();
            buffer.flip();
            channel.send(buffer, receiver);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public InetSocketAddress receive() {
        ByteBuffer buffer = ByteBuffer.allocate(64);
        InetSocketAddress sender = null;
        Packet packet = null;
        try {
            sender = (InetSocketAddress) channel.receive(buffer);
            buffer.flip();
            packet = new Packet(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (buffer.hasRemaining() && sender != null) {
            if (socketListener != null)
                socketListener.received(packet, sender);
            return sender;
        }

        return null;
    }


}
