package ch.network.packets;

import java.io.Serializable;

public class Shoot implements Serializable {

    public int x, y, rot;

    public Shoot(int x, int y, int rot) {
        this.x = x;
        this.y = y;
        this.rot = rot;
    }
}
