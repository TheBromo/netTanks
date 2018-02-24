package ch.network.packets;

import java.io.Serializable;
import java.util.UUID;

public class Spawn implements Serializable {

    public UUID id;
    public float x, y, rot;

    public Spawn(UUID id, float x, float y, float rot) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.rot = rot;
    }
}
