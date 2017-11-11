package ch.tanks;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

import java.util.ArrayList;

public class Framework extends Canvas {

    public static double MOUSEX, MOUSEY;

    private GraphicsContext gc;
    private Timeline gameloop;

    private Map map;
    private Tank player;
    private ArrayList<Tank> players;

    public Framework(int width, int height) {
        gc = this.getGraphicsContext2D();
        this.setWidth(width);
        this.setHeight(height);

        //Create Game Loop
        gameloop = new Timeline(new KeyFrame(
                Duration.millis(16.666666666667),
                ae -> update()));
        gameloop.setCycleCount(Timeline.INDEFINITE);

        //Tell the Canvas to update MOUSEX and MOUSEY every time the mouse was moved
        this.setOnMouseMoved(me -> {
            MOUSEX = me.getX();
            MOUSEY = me.getY();
        });

        //Make the Canvas register keystrokes
        this.addEventFilter(MouseEvent.ANY, (e) -> this.requestFocus());

        //Set Inputs
        setKeyInput();
        setMouseInput();

    }

    public void start() {
        gameloop.play();

        map = new Map(512, 512);
        player = new Tank(100, 100, 0);
    }

    public void stop() {
        gameloop.stop();
    }

    /**
     * Gets called 60 times per second by the gameloop.
     * Used to update and render objects etc.
     */
    public void update() {
        //Clear Canvas (Stops "smearing" effect)
        gc.clearRect(0, 0, this.getWidth(), this.getHeight());

        //Update all the things!
        map.update(gc);
        player.update(gc);
        //for (Tank tank: players) {
        //    tank.update(gc);
        //}
    }

    public void setKeyInput() {
        this.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case W:
                        player.setVelocity(-1);
                        break;
                    case A:
                        player.setRotation(-1);
                        break;
                    case S:
                        player.setVelocity(1);
                        break;
                    case D:
                        player.setRotation(1);
                        break;
                }
            }
        });

        this.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case W:
                        player.setVelocity(0);
                        break;
                    case A:
                        player.setRotation(0);
                        break;
                    case S:
                        player.setVelocity(0);
                        break;
                    case D:
                        player.setRotation(0);
                        break;
                }
            }
        });
    }

    public void setMouseInput() {
        this.setOnMousePressed((new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

            }
        }));

        this.setOnMouseReleased((new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

            }
        }));
    }
}
