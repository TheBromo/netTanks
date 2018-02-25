package ch.network.packets;

import java.io.Serializable;
import java.util.UUID;

public abstract class Packet implements Serializable {

    public UUID id;

    public Packet(UUID id) {
        this.id = id;
    }
}
