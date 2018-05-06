package ch.framework;

import ch.framework.gameobjects.Bullet;
import ch.framework.gameobjects.GameObject;
import ch.framework.gameobjects.Mine;
import ch.framework.gameobjects.PickUp;
import ch.framework.gameobjects.tank.Tank;
import ch.framework.map.Block;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.transform.Affine;
import javafx.scene.transform.Rotate;


public class Camera {

    private GraphicsContext gc;
    private float x, y, z, a;
    private int width, height;
    private Handler handler;

    public Camera(GraphicsContext gc, int width, int height) {
        this.gc = gc;
        x = 0;
        y = 0;
        z = 1;
        a = 0;
        this.width = width;
        this.height = height;
    }

    public Camera(GraphicsContext gc, float x, float y, float z, float a, int width, int height) {
        this.gc = gc;
        this.x = x;
        this.y = y;
        this.z = z;
        this.a = a;
        this.width = width;
        this.height = height;
    }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void setPosition(float x, float y, float a) {
        setPosition(x, y);
        this.a = a;
    }

    public void render() {
        //Clear Canvas (Prevents "smearing" effect)
        gc.clearRect(0, 0, width, height);
        gc.setFill(Color.BEIGE);
        gc.fillRect(0, 0, width, height);

        gc.save();
        gc.translate(x + (width / 2), y + (height / 2));

        // Center
        gc.setFill(Color.LIGHTGRAY);
        gc.fillOval(-1.5, -1.5, 3, 3);

        handler.getMap().getBlocks().forEach(this::renderBlock);
        handler.getMines().forEach(this::renderMine);
        handler.getPickUps().forEach(this::renderPickUp);
        handler.getTanks().forEach(this::renderTank);
        handler.getBullets().forEach(this::renderBullet);

        gc.restore();
    }

    private void renderBlock(Block block) {
        if (block.getType() == Block.Type.STANDARD) {
            gc.setFill(Color.valueOf("#debf89"));
            gc.fillRoundRect(block.getX(), block.getY(), 64, 64, 10, 10);
        } else if (block.getType() == Block.Type.CORK) {
            gc.setFill(Color.valueOf("#debf89").brighter().brighter());
            gc.fillRoundRect(block.getX(), block.getY(), 64, 64, 10, 10);
        } else if (block.getType() == Block.Type.HOLE) {
            gc.setFill(Color.GRAY.brighter());
            gc.fillOval(block.getX(), block.getY(), 64, 64);
        }
    }

    private void renderTank(Tank tank) {
        Color color = Color.valueOf("#babbbc"); //Default color
        if (tank.getColor() != null) {
            color = Color.valueOf(tank.getColor());
        }

        gc.save();

        Affine transform = new Affine(new Rotate(tank.getRotation(), tank.getX(), tank.getY()));
        gc.transform(transform);
        gc.setFill(Color.GRAY);
        gc.fillRoundRect(tank.getX() - 24, tank.getY() - 24, 48, 48, 3, 3);
        gc.setFill(color);
        gc.fillRect(tank.getX() - 24 + 9, tank.getY() - 24, 48 - 18, 48);
        gc.setFill(Color.LIGHTGRAY);
        gc.fillRect(tank.getX() - 12, tank.getY() - 22, 24, 2);

        gc.restore();

        //TURRET
        gc.save();

        gc.transform(new Affine(new Rotate(tank.getTurret().getRotation(), tank.getX(), tank.getY())));
        gc.setFill(color.brighter());
        gc.fillRoundRect(tank.getX() - 12, tank.getY() - 12, 24, 24, 7, 7);
        gc.setFill(Color.LIGHTGRAY);
        gc.fillRect(tank.getX() - 2, tank.getY() - 30, 4, 20);

        gc.restore();
    }

    private void renderBullet(Bullet bullet) {
        gc.save();
        gc.translate(bullet.getX(), bullet.getY());
        gc.transform(new Affine(new Rotate(bullet.getRotation()))); //Rotate the gc to the angle of the bullet's path

        //TODO increase bullet size in general

        if (bullet.getType() == Bullet.Type.STANDARD) {
            gc.translate(-2, -3); //CorrectionPacket SVG to center of Bullet
            gc.setFill(Color.GRAY);
            gc.beginPath();
            gc.appendSVGPath("M 0 3 Q 0 1 2 0 Q 4 1 4 3 L 4 7 L 0 7 Z"); //SVG PATH OF BULLET
            gc.fill();
            gc.closePath();
        } else if (bullet.getType() == Bullet.Type.ROCKET) {
            //TODO create rocket SVG
            gc.setFill(Color.GRAY);
            gc.beginPath();
            gc.appendSVGPath("M 0 3 Q 0 1 2 0 Q 4 1 4 3 L 4 7 L 0 7 Z"); //SVG PATH OF BULLET
            gc.fill();
            gc.closePath();
        } else if (bullet.getType() == Bullet.Type.BOUNCY) {
            gc.setFill(Color.GRAY);
            gc.fillOval(bullet.getX() - bullet.getRadius(), bullet.getY() - bullet.getRadius(), bullet.getRadius() * 2, bullet.getRadius() * 2);
        }

        gc.restore();
    }

    private void renderMine(Mine mine) {
        gc.setFill(Color.YELLOW);
        gc.fillOval(mine.getX() - mine.getRadius(), mine.getY() - mine.getRadius(), mine.getRadius() * 2, mine.getRadius() * 2);
    }

    private void renderPickUp(PickUp pickUp) {
        gc.setFill(Color.LIGHTBLUE);
        gc.fillOval(pickUp.getX() - pickUp.getRadius(), pickUp.getY() - pickUp.getRadius(), pickUp.getRadius() * 2, pickUp.getRadius() * 2);
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    public Handler getHandler() {
        return handler;
    }

}
