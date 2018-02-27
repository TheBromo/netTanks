package ch.framework;

import ch.framework.gameobjects.tank.Tank;
import javafx.scene.paint.Color;

public class Player {

    private int id;
    private Tank tank;
    private String username;
    private String color;

    public Player(String username, String color, int id) {
        this.username = username;
        this.color = color;
        this.id = id;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
