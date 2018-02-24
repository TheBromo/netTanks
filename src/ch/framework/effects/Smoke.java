package ch.framework.effects;

import ch.framework.Mainframe;
import ch.framework.collision.Circle;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Random;

public class Smoke extends Effect {

    private Random random;
    private ArrayList<Circle> circles;
    private double rate;
    private double x, y;

    public Smoke() {
        circles = new ArrayList<>();
        rate = 0.2;
        random = new Random();
    }

    public void render(GraphicsContext gc) {

        gc.setFill(Color.LIGHTGRAY);

        if (Mainframe.getFPS() % 5 == 0) {
            circles.add(new Circle(x, y, random.nextDouble() * 6));
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

    public void setLocation(double x, double y) {
        this.x = x;
        this.y = y;
    }


}