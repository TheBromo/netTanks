package ch.framework;

import ch.framework.gameobjects.Bullet;
import ch.framework.gameobjects.Mine;
import ch.framework.gameobjects.tank.Tank;

import java.util.ArrayList;
import java.util.List;

public class Player {

    private int id;
    private String username;
    private String color;

    private Tank tank;
    private List<Bullet> bullets;
    private List<Mine> mines;

    private PlayerActionListener listener;

    public Player(String username, String color) {
        this.username = username;
        this.color = color;
        this.id = 0;
        bullets = new ArrayList<>();
        mines = new ArrayList<>();
    }

    public void changeVelocity(float vel) {
        listener.changeVelocity(vel, this);
    }

    public void changeRotation(float vel) {
        listener.changeRotation(vel, this);
    }

    public void changeTurretRotation(float rot) {
        listener.changeTurretRotation(rot, this);
    }

    public void changeTurretRotation(float mx, float my) {
        float rot = ((float) Math.toDegrees(Math.atan2(-my, -mx)) + 90); // TODO Check for errors
        if (rot < 0) {
            rot += 360;
        }
        listener.changeTurretRotation(rot, this);
    }

    public void shoot() {
        listener.shoot(this);
    }

    public void place() {
        listener.place(this);
    }

    public void spawn() {
        listener.spawn(this);
    }

    // GETTERS & SETTERS ////////////////////////////////////////////////////////

    public void setListener(PlayerActionListener listener) {
        this.listener = listener;
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

    public Tank getTank() {
        return tank;
    }

    public void setTank(Tank tank) {
        this.tank = tank;
    }

    public void addBullet(Bullet bullet) {
        bullets.add(bullet);
    }

    public void removeBullet(Bullet bullet) {
        bullets.remove(bullet);
    }

    public List<Bullet> getBullets() {
        return bullets;
    }

    public void addMine(Mine mine) {
        mines.add(mine);
    }

    public void removeMine(Mine mine) {
        mines.remove(mine);
    }

    public List<Mine> getMines() {
        return mines;
    }

}
