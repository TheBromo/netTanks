package ch.network.packet;

import java.util.UUID;

public class Hit {

    public UUID id;
    public float x, y;

    public Hit(UUID id, float x, float y) {
        this.id = id;
        this.x = x;
        this.y = y;
    }
}
