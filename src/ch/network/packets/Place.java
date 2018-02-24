package ch.network.packets;

import java.io.Serializable;

public class Place implements Serializable {

    public float x, y;

    public Place(float x, float y) {
        this.x = x;
        this.y = y;
    }
}
