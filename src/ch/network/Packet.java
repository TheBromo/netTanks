package ch.network;

import ch.framework.ID;

import java.nio.ByteBuffer;

public class Packet {

    private ByteBuffer buffer;

    public Packet(ByteBuffer buffer) {
        this.buffer = buffer;
    }

    public Packet(Type type) {
        buffer = ByteBuffer.allocate(type.length);
        buffer.put(type.b);
        ID id = ID.randomID();
        buffer.putInt(id.getValue());
    }

    public ID getID() {
        buffer.position(1);
        return new ID(buffer.getInt());
    }

    public Type getType() {
        buffer.position(0);
        return Type.getType(buffer.get());
    }

    public ByteBuffer getContent() {
        buffer.position(5);
        return buffer.slice();
    }

    public ByteBuffer getBuffer() {
        buffer.position(buffer.limit() - 1);
        return buffer;
    }

    public boolean isValid() {
        return !(getType() == Type.UNDEFINED || buffer.limit() > 5);
    }

    public enum Type {

        UNDEFINED(0, 0),
        RECEIVED(1, 64),
        PING(2, 64),
        REQUEST(3, 5),
        ACK(4, 5),
        DISCONNECT(5, 64),
        JOIN(6, 19);

        public final byte b;
        public final int length;

        Type(int b, int length) {
            this.b = (byte) b;
            this.length = length;
        }

        public static Type getType(byte b) {
            for (Type type : values()) {
                if (type.b == b)
                    return type;
            }
            return UNDEFINED;
        }

    }

}
