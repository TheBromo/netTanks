package ch.framework.map.block;

import ch.framework.collision.Rectangle;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Block {

    private int indexX, indexY;
    private BlockType type;
    private Rectangle bounds;

    public Block(int indexX, int indexY, BlockType type) {
        this.indexX = indexX;
        this.indexY = indexY;
        this.type = type;

        bounds = new Rectangle(getX() + 32, getY() + 32, 64, 64, 0);
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

    public Rectangle getBounds() {
        return bounds;
    }
}

