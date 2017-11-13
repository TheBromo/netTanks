package ch.tanks;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class Map {

    private int width, height;
    private ArrayList<Segment> boundaries;
    private ArrayList<Block> blocks;

    public Map(int width, int height) {
        this.width = width;
        this.height = height;
        blocks = new ArrayList<>();
        boundaries = new ArrayList<>();

        boundaries.add(new Segment(0, 0, 960, 0, true));
        boundaries.add(new Segment(960, 0, 960, 640, false));
        boundaries.add(new Segment(0, 640, 960, 640, true));
        boundaries.add(new Segment(0, 0, 0, 640, false));

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

    public ArrayList<Segment> getBoundaries() {
        return boundaries;
    }
}