package ch.tanks;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.transform.Affine;
import javafx.scene.transform.Rotate;

import java.util.ArrayList;

public class Map extends Canvas {

    Framework framework;

    private int updates;

    private GraphicsContext gc;

    private int width, height;
    private Rectangle bounds;
    private ArrayList<Block> blocks;

    public Map(int width, int height, Framework framework) {
        this.framework = framework;
        this.setWidth(framework.getWidth());
        this.setHeight(framework.getHeight());
        this.width = width;
        this.height = height;
        blocks = new ArrayList<>();

        gc = this.getGraphicsContext2D();

        bounds = new Rectangle(960 / 2, 640 / 2, 960, 640, 0);

        //Create Map
        blocks.add(new Block(4, 3, BlockType.STANDARD));
        blocks.add(new Block(4, 4, BlockType.CORK));
        blocks.add(new Block(4, 5, BlockType.HOLE));


        gc.setFill(Color.BEIGE);
        gc.fillRect(0, 0, this.getWidth(), this.getHeight());
    }

    public void update(GraphicsContext gc) {

        render();

        for (Block b : blocks) {
            b.update(gc);
        }

        updates++;
    }

    public void render() {
        //Tank tracks

        if (updates == 7) {
            for (Tank tank : framework.getTanks()) {
                gc.save();
                gc.transform(new Affine(new Rotate(tank.getAngle(), tank.getX(), tank.getY())));
                gc.setFill(Color.rgb(229, 229, 211, 0.8));
                gc.fillRect(tank.getX() - 32, tank.getY(), 16, 5);
                gc.fillRect(tank.getX() + 32 - 16, tank.getY(), 16, 5);
                gc.restore();
            }
            updates = 0;
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

    public int getBlockWidth() {
        return width;
    }

    public int getBlockHeight() {
        return height;
    }

    public Rectangle getBounds() {
        return bounds;
    }
}