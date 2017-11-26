package ch.network;

public class PlayerState {

    private float x, y, rotation;
    private int mouseX, mouseY;
    private int frame;

    public PlayerState(float x, float y, float rotation, int mouseX, int mouseY) {
        this.x = x;
        this.y = y;
        this.rotation = rotation;
        this.mouseX = mouseX;
        this.mouseY = mouseY;
    }

    public PlayerState() {

    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getRotation() {
        return rotation;
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }

    public int getMouseX() {
        return mouseX;
    }

    public void setMouseX(int mouseX) {
        this.mouseX = mouseX;
    }

    public int getMouseY() {
        return mouseY;
    }

    public void setMouseY(int mouseY) {
        this.mouseY = mouseY;
    }
}
