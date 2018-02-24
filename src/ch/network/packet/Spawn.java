package ch.network.packet;

import java.io.Serializable;
import java.util.UUID;

public class Spawn implements Serializable {

    public UUID id;
    public float x, y;

    public Spawn(UUID id, float x, float y) {
        this.id = id;
        this.x = x;
        this.y = y;
    }
}
