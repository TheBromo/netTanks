package ch.framework;

import ch.framework.gameobjects.Bullet;
import ch.framework.gameobjects.Mine;
import ch.framework.gameobjects.PickUp;
import ch.framework.gameobjects.tank.Tank;
import ch.framework.map.Block;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.transform.Affine;
import javafx.scene.transform.Rotate;


public class Render {

    private Canvas primary;
    private Canvas ground;
    private GraphicsContext primaryGc;
    private GraphicsContext groundGc;

    private Handler handler;

    public Render(Mainframe mainframe, Handler handler) {
        this.handler = handler;
        ground = new Canvas(mainframe.getWidth(), mainframe.getHeight());
        groundGc = ground.getGraphicsContext2D();
        mainframe.getChildren().add(ground);
        groundGc.setFill(Color.BEIGE);
        groundGc.fillRect(0, 0, ground.getWidth(), ground.getHeight());

        primary = new Canvas(mainframe.getWidth(), mainframe.getHeight());
        primaryGc = primary.getGraphicsContext2D();
        mainframe.getChildren().add(primary);
    }

    public void render() {

        //Clear Canvas (Prevents "smearing" effect)
        primaryGc.clearRect(0, 0, primary.getWidth(), primary.getHeight());

        //Background
        if (Mainframe.getFPS() % 7 == 0) {
            for (Tank tank : handler.getTanks()) {
                groundGc.save();
                groundGc.transform(new Affine(new Rotate(tank.getRotation(), tank.getX(), tank.getY())));
                groundGc.setFill(Color.rgb(229, 229, 211, 0.8));
                groundGc.fillRect(tank.getX() - 32, tank.getY(), 16, 5);
                groundGc.fillRect(tank.getX() + 32 - 16, tank.getY(), 16, 5);
                groundGc.restore();
            }
        }

        groundGc.setStroke(Color.WHITE);
        for (Tank tank : handler.getRemovedTanks()) {
            if (tank.isAlive()) {
                groundGc.strokeRect(tank.getX() - 32, tank.getY() - 32, 64, 64);
                tank.setAlive(false);
            }
        }

        //Blocks
        for (Block block : handler.getMap().getBlocks()) {
            renderBlock(block);
        }

        //Mines
        for (Mine mine : handler.getMines()) {
            renderMine(mine);
        }

        //PickUps
        for (PickUp pickUp : handler.getPickUps()) {
            renderPickUp(pickUp);
        }

        //Tanks
        for (Tank tank : handler.getTanks()) {
            renderTank(tank);
        }

        //Bullets
        for (Bullet bullet : handler.getBullets()) {
            handler.getBulletTrails().get(bullet).render(primaryGc);
            renderBullet(bullet);
        }

        //Explosions
        for (Mine mine : handler.getMineExplosions().keySet()) {
            handler.getMineExplosions().get(mine).render(primaryGc);
        }
    }

    private void renderBlock(Block block) {
        if (block.getType() == Block.Type.STANDARD) {
            primaryGc.setFill(Color.valueOf("#debf89"));
            primaryGc.fillRoundRect(block.getX(), block.getY(), 64, 64, 10, 10);
        } else if (block.getType() == Block.Type.CORK) {
            primaryGc.setFill(Color.valueOf("#debf89").brighter().brighter());
            primaryGc.fillRoundRect(block.getX(), block.getY(), 64, 64, 10, 10);
        } else if (block.getType() == Block.Type.HOLE) {
            primaryGc.setFill(Color.GRAY.brighter());
            primaryGc.fillOval(block.getX(), block.getY(), 64, 64);
        }
    }

    private void renderTank(Tank tank) {
        Color color = Color.valueOf("#babbbc"); //Default color
        if (tank.getColor() != null) {
            color = tank.getColor();
        }

        primaryGc.save();

        Affine transform = new Affine(new Rotate(tank.getRotation(), tank.getX(), tank.getY()));
        primaryGc.transform(transform);
        primaryGc.setFill(Color.GREY);
        primaryGc.fillRoundRect(tank.getX() - 32, tank.getY() - 32, 64, 64, 3, 3);
        primaryGc.setFill(color);
        primaryGc.fillRect(tank.getX() - 32 + 12, tank.getY() - 32, 64 - 24, 64);

        primaryGc.restore();

        //TURRET
        primaryGc.save();

        primaryGc.transform(new Affine(new Rotate(tank.getTurret().getRotation(), tank.getX(), tank.getY())));
        primaryGc.setFill(color.brighter());
        primaryGc.fillRoundRect(tank.getX() - 16, tank.getY() - 16, 32, 32, 7, 7);
        primaryGc.setFill(Color.LIGHTGRAY);
        primaryGc.fillRect(tank.getX() - 2, tank.getY() - 40, 4, 25);

        primaryGc.restore();
    }

    private void renderBullet(Bullet bullet) {
        primaryGc.save();
        primaryGc.translate(bullet.getX(), bullet.getY());
        primaryGc.transform(new Affine(new Rotate(bullet.getRotation()))); //Rotate the primaryGc to the angle of the bullet's path

        //TODO increase bullet size in general

        if (bullet.getType() == Bullet.Type.STANDARD) {
            primaryGc.translate(-2, -3); //MovePacket SVG to center of Bullet
            primaryGc.setFill(Color.GRAY);
            primaryGc.beginPath();
            primaryGc.appendSVGPath("M 0 3 Q 0 1 2 0 Q 4 1 4 3 L 4 7 L 0 7 Z"); //SVG PATH OF BULLET
            primaryGc.fill();
            primaryGc.closePath();
        } else if (bullet.getType() == Bullet.Type.ROCKET) {
            //TODO create rocket SVG
            primaryGc.setFill(Color.GRAY);
            primaryGc.beginPath();
            primaryGc.appendSVGPath("M 0 3 Q 0 1 2 0 Q 4 1 4 3 L 4 7 L 0 7 Z"); //SVG PATH OF BULLET
            primaryGc.fill();
            primaryGc.closePath();
        } else if (bullet.getType() == Bullet.Type.BOUNCY) {
            primaryGc.setFill(Color.GRAY);
            primaryGc.fillOval(bullet.getX() - bullet.getRadius(), bullet.getY() - bullet.getRadius(), bullet.getRadius() * 2, bullet.getRadius() * 2);
        }

        primaryGc.restore();
    }

    private void renderMine(Mine mine) {
        primaryGc.setFill(Color.YELLOW);
        primaryGc.fillOval(mine.getX() - mine.getRadius(), mine.getY() - mine.getRadius(), mine.getRadius() * 2, mine.getRadius() * 2);
    }

    private void renderPickUp(PickUp pickUp) {
        primaryGc.setFill(Color.LIGHTBLUE);
        primaryGc.fillOval(pickUp.getX() - pickUp.getRadius(), pickUp.getY() - pickUp.getRadius(), pickUp.getRadius() * 2, pickUp.getRadius() * 2);
    }

}
