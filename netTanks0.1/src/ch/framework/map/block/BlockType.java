package ch.framework.map.block;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public enum BlockType {

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
