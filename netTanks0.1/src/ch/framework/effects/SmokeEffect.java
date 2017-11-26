package ch.framework.effects;

import ch.framework.Framework;
import ch.framework.collision.Circle;
import ch.framework.gameobjects.bullet.Bullet;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Random;

public class SmokeEffect {

    private Random random;
    private ArrayList<Circle> circles;
    private double rate;

    public SmokeEffect() {
        circles = new ArrayList<>();
        rate = 0.2;
        random = new Random();
    }

    public void render(GraphicsContext gc, Bullet bullet) {

        gc.setFill(Color.LIGHTGRAY);

        if (Framework.getFRAME() % 5 == 0) {
            circles.add(new Circle(bullet.getX(), bullet.getY(), random.nextDouble() * 6));
        }

        ArrayList<Circle> removedCircles = new ArrayList<>();
        for (Circle circle : circles) {
            if (circle.getRadius() >= 0) {
                circle.setRadius(circle.getRadius() - rate);
            } else {
                removedCircles.add(circle);
            }

            gc.fillOval(circle.getCenterX() - circle.getRadius(), circle.getCenterY() - circle.getRadius(), circle.getRadius() * 2, circle.getRadius() * 2);
        }
        circles.remove(removedCircles);

    }
}
