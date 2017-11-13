package ch.tanks;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class Map {

    private int width, height;
    private ArrayList<Block> blocks;

    public Map(int width, int height) {
        this.width = width;
        this.height = height;
        blocks = new ArrayList<>();

        //Create Map
        blocks.add(new Block(4, 3, BlockType.STANDARD));
        blocks.add(new Block(4, 4, BlockType.CORK));
        blocks.add(new Block(4, 5, BlockType.HOLE));

    }

    public void update(GraphicsContext gc) {
        gc.setFill(Color.BEIGE);
        gc.fillRect(0, 0, width * 64, height * 64);

        for (Block b : blocks) {
            b.update(gc);
        }
    }

    public ArrayList<Block> getBlocks() {
        return blocks;
    }

    public Block getBlockByIndex(int indexX, int indexY) {
        for (Block b : blocks) {
            if (b.getIndexX() == indexX && b.getIndexY() == indexY) {
                return b;
            }
        }
        return null;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}

class Block {

    private int indexX, indexY;
    private BlockType type;

    public Block(int indexX, int indexY, BlockType type) {
        this.indexX = indexX;
        this.indexY = indexY;
        this.type = type;
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
}

enum BlockType {

    STANDARD(false, false, false, false), CORK(false, false, true, false), HOLE(false, true, false, false);

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