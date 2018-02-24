package ch.network.packets;

import java.io.Serializable;
import java.util.UUID;

public class Hello implements Serializable {

    public UUID id;
    public String username, color;

    public Hello(String username, String color, UUID id) {
        this.username = username;
        this.id = id;
        this.color = color;
    }
}
