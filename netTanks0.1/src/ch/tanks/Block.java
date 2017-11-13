package ch.tanks;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class Block {

    private int indexX, indexY;
    private ArrayList<Segment> segments;
    private BlockType type;

    public Block(int indexX, int indexY, BlockType type) {
        this.indexX = indexX;
        this.indexY = indexY;
        this.type = type;
        segments = new ArrayList<>();

        segments.add(new Segment(getX(), getY(), getX() + 64, getY(), true));
        segments.add(new Segment(getX() + 64, getY(), +getX() + 64, getY() + 64, false));
        segments.add(new Segment(getX() + 64, getY() + 64, getX(), getY() + 64, true));
        segments.add(new Segment(getX(), getY() + 64, getX(), getY(), false));
    }

    public void update(GraphicsContext gc) {
        type.render(indexX, indexY, gc);
    }

    public void destroy() {
        if (type.isExplosionDamage()) {
            //TODO
        }
    }

    public int getIndexX() {
        return indexX;
    }

    public int getIndexY() {
        return indexY;
    }

    public int getX() {
        return indexX * 64;
    }

    public int getY() {
        return indexY * 64;
    }

    public BlockType getType() {
        return type;
    }

    public ArrayList<Segment> getSegments() {
        return segments;
    }
}

enum BlockType {

    STANDARD(false, false, false, false),
    CORK(false, false, true, false),
    HOLE(false, true, false, false);

    private final boolean passable;
    private final boolean shootable;
    private final boolean explosionDamage;
    private final boolean bulletDamage;

    BlockType(boolean passable, boolean shootable, boolean explosionDamage, boolean bulletDamage) {
        this.passable = passable;
        this.shootable = shootable;
        this.explosionDamage = explosionDamage;
        this.bulletDamage = bulletDamage;
    }

    public void render(int indexX, int indexY, GraphicsContext gc) {

        if (this == STANDARD) {
            gc.setFill(Color.valueOf("#debf89"));
            gc.fillRoundRect(indexX * 64, indexY * 64, 64, 64, 10, 10);
        }

        if (this == CORK) {
            gc.setFill(Color.valueOf("#debf89").brighter().brighter());
            gc.fillRoundRect(indexX * 64, indexY * 64, 64, 64, 10, 10);
        }

        if (this == HOLE) {
            gc.setFill(Color.GRAY.brighter());
            gc.fillOval(indexX * 64, indexY * 64, 64, 64);
        }
    }

    public boolean isPassable() {
        return passable;
    }

    public boolean isShootable() {
        return shootable;
    }

    public boolean isExplosionDamage() {
        return explosionDamage;
    }

    public boolean isBulletDamage() {
        return bulletDamage;
    }
}

class Segment {

    private float ax, ay, bx, by;
    private boolean horizontal;

    public Segment(float ax, float ay, float bx, float by, boolean horizontal) {
        this.ax = ax;
        this.ay = ay;
        this.bx = bx;
        this.by = by;
        this.horizontal = horizontal;
    }

    public float getAx() {
        return ax;
    }

    public void setAx(float ax) {
        this.ax = ax;
    }

    public float getAy() {
        return ay;
    }

    public void setAy(float ay) {
        this.ay = ay;
    }

    public float getBx() {
        return bx;
    }

    public void setBx(float bx) {
        this.bx = bx;
    }

    public float getBy() {
        return by;
    }

    public void setBy(float by) {
        this.by = by;
    }

    public boolean isHorizontal() {
        return horizontal;
    }
}