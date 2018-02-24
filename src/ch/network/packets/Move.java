package ch.network.packets;

import java.io.Serializable;
import java.util.UUID;

public class Move implements Serializable {

    public UUID id;
    public float x, y, rot, mx, my;

    public Move(UUID id, float x, float y, float rot, float mx, float my) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.rot = rot;
        this.mx = mx;
        this.my = my;
    }
}
