package ch.network.packets;

import java.io.Serializable;

public class Lobby implements Serializable {

    public Connect[] connects;

    public Lobby(Connect[] connects) {
        this.connects = connects;
    }
}
