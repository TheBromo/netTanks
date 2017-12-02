package ch.framework.gameobjects.tank;

public class Turret {

    private float rotation, barrelLength;
    private Tank tank;

    public Turret(Tank tank, float rotation) {
        this.tank = tank;
        this.rotation = rotation;
        this.barrelLength = 40;
    }

    public void update() {

    }

    public float getRotation() {
        return rotation;
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }

    public float getMuzzleX() {
        return (float) (tank.getX() - Math.sin(Math.toRadians(-rotation)) * barrelLength);
    }

    public float getMuzzleY() {
        return (float) (tank.getY() - Math.cos(Math.toRadians(-rotation)) * barrelLength);
    }
}
