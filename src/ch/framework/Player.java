package ch.framework;

import ch.framework.gameobjects.tank.Tank;
import javafx.scene.paint.Color;

import java.util.UUID;

public class Player {

    private UUID id;
    private Tank tank;
    private String username;
    private String color;

    public Player(String username, String color) {
        this.username = username;
        this.color = color;
        id = UUID.randomUUID();
    }

    public Tank getTank() {
        return tank;
    }

    public void setTank(Tank tank) {
        this.tank = tank;
        tank.setColor(Color.valueOf(color));
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
