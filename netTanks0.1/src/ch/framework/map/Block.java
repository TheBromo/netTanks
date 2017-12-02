package ch.framework.map;

import ch.framework.collision.Rectangle;

public class Block {

    private int indexX, indexY;
    private Type type;
    private Rectangle bounds;

    public Block(int indexX, int indexY, Type type) {
        this.indexX = indexX;
        this.indexY = indexY;
        this.type = type;

        bounds = new Rectangle(getX() + 32, getY() + 32, 64, 64, 0);
    }

    public void update() {

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

    public Type getType() {
        return type;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public enum Type {

        STANDARD(false, false, false, false),
        CORK(false, false, true, false),
        HOLE(false, true, false, false);

        private final boolean passable;
        private final boolean shootable;
        private final boolean explosionDamage;
        private final boolean bulletDamage;

        Type(boolean passable, boolean shootable, boolean explosionDamage, boolean bulletDamage) {
            this.passable = passable;
            this.shootable = shootable;
            this.explosionDamage = explosionDamage;
            this.bulletDamage = bulletDamage;
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
}

