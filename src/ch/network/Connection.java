package ch.network;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class Connection {

    private SocketListener socketListener;
    private InetSocketAddress socket;
    private DatagramChannel channel;
    private InetSocketAddress receiver;

    public Connection(int port, InetSocketAddress receiver) {
        try {
            this.channel = DatagramChannel.open();
            this.channel.configureBlocking(false);
            this.socket = new InetSocketAddress(port);
            this.channel.bind(socket);
            this.receiver = receiver;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setSocketListener(SocketListener socketListener) {
        this.socketListener = socketListener;
    }

    public void send(Packet packet) {
        ByteBuffer buffer = packet.toByteBuffer();
        try {
            buffer.flip();
            channel.send(buffer, receiver);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendByteBuffer(ByteBuffer buffer) {
        try {
            buffer.flip();
            channel.send(buffer, receiver);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void ping() {

    }

    private void receive() {
        ByteBuffer buffer = ByteBuffer.allocate(64);
        try {
            SocketAddress sender = channel.receive(buffer);
            buffer.flip();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (buffer.hasRemaining()) {
            Packet packet = Packet.fromByteBuffer(buffer);

            if (packet instanceof Packet.Ping) {
                Packet.Ping ping = (Packet.Ping) packet;
                System.out.println("ID:" + ping.id + " Time:" + ping.time);
            }

            if (socketListener != null)
                socketListener.received(this, packet);
        }
    }

    public void tick() {
        receive();
        // TODO: Ping once per second
    }

    public static void main(String[] args) {
        Connection connection = new Connection(13014, new InetSocketAddress("localhost", 13013));
        connection.send(new Packet.Ping(System.currentTimeMillis()));
    }


}
