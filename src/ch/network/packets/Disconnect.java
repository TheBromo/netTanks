package ch.network.packets;

import java.util.UUID;

public class Disconnect {

    public UUID id;

    public Disconnect(UUID id) {
        this.id = id;
    }
}
