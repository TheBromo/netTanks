package ch.network.packets;

import java.io.Serializable;

public class Lobby implements Serializable {

    public Hello[] hellos;

    public Lobby(Hello[] hellos) {
        this.hellos = hellos;
    }
}
