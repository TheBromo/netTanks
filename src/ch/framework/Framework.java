package ch.framework;

import ch.network.Session;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class Framework {

    private static int FRAME;

    private Timeline gameloop;
    private Handler handler;
    private Render render;
    private Session session;

    public Framework(Session session) {
        handler = new Handler();
        render = new Render(NetTanks.WIDTH, NetTanks.HEIGHT);
        this.session = session;
    }

    public void start() {

        //Create Game Loop
        gameloop = new Timeline(new KeyFrame(
                Duration.millis(16.666666666667),
                ae -> update()));
        gameloop.setCycleCount(Timeline.INDEFINITE);
        gameloop.play();
    }

    public void stop() {
        gameloop.stop();
    }

    /**
     * Gets called 60 times per second by the gameloop.
     * Used to update and render objects etc.
     */
    private void update() {
        FRAME++;

        //Update all the things!
        handler.update();

        render.render(this);


        session.handleChange();
    }

    public static int getFRAME() {
        return FRAME;
    }

    public Handler getHandler() {
        return handler;
    }

    public Render getRender() {
        return render;
    }
}