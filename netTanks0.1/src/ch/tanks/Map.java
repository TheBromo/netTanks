package ch.tanks;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Map {

    private int width, height;
    private Obstacle[][] obstacles;

    public Map(int width, int height) {
        this.width = width;
        this.height = height;
        obstacles = new Obstacle[width][height];

        //Create Map
        for (int i = 0; i < 5; i++) {
            obstacles[i][0] = new Wall(i, 0);
        }
    }

    public void update(GraphicsContext gc) {
        gc.setFill(Color.BEIGE);
        gc.fillRect(0, 0, width, height);

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (obstacles[i][j] != null)
                    obstacles[i][j].update(gc);
            }
        }
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Obstacle[][] getObstacles() {
        return obstacles;
    }
}

abstract class Obstacle {

    int x, y;

    public Obstacle(int x, int y) {
        this.x = x;
        this.y = y;
    }

    abstract public void update(GraphicsContext gc);

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}

class Wall extends Obstacle {

    public Wall(int x, int y) {
        super(x, y);
    }

    @Override
    public void update(GraphicsContext gc) {
        //gc.strokeRect(x*64, y*64, x*64 + 64, y*64 + 64);
        gc.setFill(Color.valueOf("#debf89"));
        gc.fillRoundRect(x * 64, y * 64, (x * 64) + 64, (y * 64) + 64, 10, 10);

    }
}
//
//class BreakableWall extends Obstacle {
//
//}
//
//class Hole extends Obstacle {
//
//}