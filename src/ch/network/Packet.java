package ch.network;

import ch.framework.ID;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;

public abstract class Packet {

    public final ID id;

    public Packet(ID id) {
        this.id = id;
    }

    public Packet() {
        this.id = ID.randomID();
    }

    public static Packet fromByteBuffer(ByteBuffer buffer) {

        //Packet id:
        int value = buffer.getInt();
        ID id = new ID(value);

        //Packet type:
        byte type = buffer.get();
        switch (type) {

            case Ping.TYPE:
                long time = buffer.getLong();
                return new Ping(id, time);

            case Connect.TYPE:
                CharBuffer charBuffer = buffer.asCharBuffer();
                String username = charBuffer.toString();
                return new Connect(username);
        }

        // Else: (Not valid Packet)
        return null;
    }

    public abstract ByteBuffer toByteBuffer();

    //PACKET TYPES//////////////////////////////////////////////////////////////////////////////////////////////////////

    public static class Ping extends Packet {
        public static final byte TYPE = 0;
        public final long time;

        public Ping(ID id, long time) {
            super(id);
            this.time = time;
        }

        public Ping(long time) {
            super();
            this.time = time;
        }

        @Override
        public ByteBuffer toByteBuffer() {
            ByteBuffer buffer = ByteBuffer.allocate(64);
            buffer.putInt(id.getValue());
            buffer.put(TYPE);
            buffer.putLong(time);
            return buffer;
        }
    }

    public static class Connect extends Packet {
        public static final byte TYPE = 1;
        public final String username;

        public Connect(ID id, String username) {
            super(id);
            this.username = username;
        }

        public Connect(String username) {
            this.username = username;
        }

        @Override
        public ByteBuffer toByteBuffer() {
            ByteBuffer buffer = ByteBuffer.allocate(64);
            buffer.putInt(id.getValue());
            buffer.put(TYPE);
            char[] value = username.toCharArray();
            for (int i = 0; i < value.length; i++) {
                buffer.putChar(value[i]);
            }
            return buffer;
        }
    }
}
