package ch.network;

import ch.framework.ID;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;

public class PacketManager {

    private PacketListener packetListener;
    private int packetsReceived;

    public PacketManager(PacketListener packetListener) {
        this.packetListener = packetListener;
    }

    public void handlePacket(Connection connection, ByteBuffer buffer) {

        // Packet ID:
        int value = buffer.getInt();
        ID id = new ID(value);

        //Packet type:
        PacketType type = PacketType.getType(buffer.get());
        switch (type) {

            case RECEIVED:
                break;

            case PING:
                long time = buffer.getLong();
                // TODO: Connection - Ping
                break;

            case CONNECT:
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
                packetListener.handleJoin(connection, username, color);
                break;
        }

        packetsReceived++;
    }

    public void sendJoinPacket(String username, String color) {
        ByteBuffer buffer = ByteBuffer.allocate(19);
        ID id = ID.randomID();
        buffer.putInt(id.getValue());
        buffer.put(PacketType.JOIN.b);

        buffer.asCharBuffer().put(username, 4, 4 + 8);
        buffer.asCharBuffer().put(color, 12, 12 + 7);
    }

    public int getPacketsReceived() {
        return packetsReceived;
    }

    public void setPacketListener(PacketListener packetListener) {
        this.packetListener = packetListener;
    }

    private enum PacketType {

        UNDEFINED(0), RECEIVED(1), PING(2), CONNECT(3), DISCONNECT(4), JOIN(5);

        public final byte b;

        PacketType(int b) {
            this.b = (byte) b;
        }

        public static PacketType getType(byte b) {
            for (PacketType type : values()) {
                if (type.b == b)
                    return type;
            }
            return UNDEFINED;
        }
    }
}
