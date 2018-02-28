package ch.framework;

import java.util.ArrayList;
import java.util.UUID;

public class Player {

    private int id;
    private String username;
    private String color;

    UUID tank;
    ArrayList<UUID> bullets;
    ArrayList<UUID> mines;

    public Player(String username, String color, int id) {
        this.username = username;
        this.color = color;
        this.id = id;
        bullets = new ArrayList<>();
        mines = new ArrayList<>();
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

    public UUID getTank() {
        return tank;
    }

    public void setTank(UUID tank) {
        this.tank = tank;
    }

    public void addBullet(UUID id) {
        bullets.add(id);
    }

    public void removeBullet(UUID id) {
        bullets.remove(id);
    }

    public ArrayList<UUID> getBullets() {
        return bullets;
    }

    public void addMine(UUID id) {
        mines.add(id);
    }

    public void removeMine(UUID id) {
        mines.remove(id);
    }

    public ArrayList<UUID> getMines() {
        return mines;
    }

}
