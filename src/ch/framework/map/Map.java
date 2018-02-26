package ch.framework.map;

import ch.framework.collision.Rectangle;

import java.util.ArrayList;

public class Map {

    //private Rectangle bounds;
    private ArrayList<Block> blocks;

    public Map(Map.Maps map) {
        blocks = new ArrayList<>();

        //bounds = new Rectangle(960 / 2, 640 / 2, 960, 640, 0);

        //Create Map
        blocks = map.loadBlocks();
    }

    public void update() {
        for (Block b : blocks) {
            b.update();
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

    public boolean isFree(int indexX, int indexY) {
        for (Block block : blocks) {
            if (block.getIndexX() == indexX && block.getIndexY() == indexY) {
                return false;
            }
        }
        return true;
    }

//    public Rectangle getBounds() {
//        return bounds;
//    }

    public enum Maps {

        MAP0(new int[][]{
                new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        }),

        MAP1(new int[][]{
                new int[]{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                new int[]{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                new int[]{1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1},
                new int[]{1, 0, 3, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1},
                new int[]{1, 0, 3, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 1},
                new int[]{1, 0, 3, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 1},
                new int[]{1, 0, 3, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1},
                new int[]{1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1},
                new int[]{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                new int[]{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                new int[]{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                new int[]{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                new int[]{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                new int[]{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                new int[]{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                new int[]{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                new int[]{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                new int[]{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                new int[]{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                new int[]{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                new int[]{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
        });

        private final int[][] field;

        Maps(int[][] field) {
            this.field = field;
        }

        public ArrayList<Block> loadBlocks() {
            ArrayList<Block> blocks = new ArrayList<>();
            for (int i = 0; i < field.length; i++) {
                for (int j = 0; j < field[i].length; j++) {

                    int block = field[i][j];

                    if (block == 0) {

                    } else if (block == 1) {
                        blocks.add(new Block(i, j, Block.Type.STANDARD));
                    } else if (block == 2) {
                        blocks.add(new Block(i, j, Block.Type.CORK));
                    } else if (block == 3) {
                        blocks.add(new Block(i, j, Block.Type.HOLE));
                    }
                }
            }
            return blocks;
        }
    }
}