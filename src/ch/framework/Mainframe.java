package ch.framework;

import ch.network.Session;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class Mainframe extends Pane {

    private static int FPS;

    private Timeline gameloop;
    private Render render;
    private Session session;

    public Mainframe() {

        NetTanks nt = NetTanks.getInstance();

        this.setWidth(nt.getWidth());
        this.setHeight(nt.getHeight());

        //Create Game Loop
        gameloop = new Timeline(new KeyFrame(
                Duration.millis(16.666666666667),
                ae -> update()));
        gameloop.setCycleCount(Timeline.INDEFINITE);

        // Set up Key and Mouse Events
        setKeyInput();
        setMouseInput();

        this.addEventFilter(MouseEvent.ANY, (e) -> this.requestFocus());
    }

    public void start(Session session) {
        this.session = session;
        render = new Render(this, session.getHandler());

        gameloop.play();
    }

    public void clear() {

    }

    public void stop() {
        gameloop.stop();
    }

    /**
     * Gets called 60 times per second by the gameloop.
     * Used to update and render objects etc.
     */
    private void update() {
        FPS++;

        //Update all the things!
        render.render();
        session.tick();
    }

    private void setKeyInput() {

        this.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case W:
                    session.setVelocity(-1.5f);
                    break;
                case A:
                    session.setVelRotation(-1);
                    break;
                case S:
                    session.setVelocity(1.5f);
                    break;
                case D:
                    session.setVelRotation(1);
                    break;

                case TAB:
                    //hud.setPlayerInfoVisibility(true);
                    break;
            }

        });

        this.setOnKeyReleased(event -> {
            switch (event.getCode()) {
                case W:
                    session.setVelocity(0);
                    break;
                case A:
                    session.setVelRotation(0);
                    break;
                case S:
                    session.setVelocity(0);
                    break;
                case D:
                    session.setVelRotation(0);
                    break;
                case F1:
                    //hud.toggleOverlayVisibility();
                    break;
                case TAB:
                    //hud.setPlayerInfoVisibility(false);
                    break;
                case C:
                    //player.setColor(new Color(random.nextDouble(), random.nextDouble(), random.nextDouble(), 1));
                    session.test();
                    break;
            }
        });
    }

    private void setMouseInput() {
        this.setOnMouseClicked(event -> {

        });


        this.setOnMousePressed(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                //handler.handleShot(player.getTank());
            }
        });

        this.setOnMouseReleased(event -> {

        });

        this.setOnMouseMoved(event -> {
            //Tell the Canvas to update mouseX and mouseY every time the mouse was moved
            session.setMouseX(event.getX());
            session.setMouseY(event.getY());
        });

        this.setOnMouseDragged(event -> {
            //Still update mouse position even if the mouse buttons are down...
            session.setMouseX(event.getX());
            session.setMouseY(event.getY());
        });

//        this.setOnScroll(se -> {
//
//            double maxSCALE = 3, minSCALE = 0.5;
//            double zoom = se.getDeltaY() / 320;
//
//            if (scale + zoom > maxSCALE) {
//                scale = maxSCALE;
//                gc.getTransform().setMxx(scale);
//                gc.getTransform().setMyy(scale);
//            } else if (scale + zoom < minSCALE) {
//                scale = minSCALE;
//                gc.getTransform().setMxx(scale);
//                gc.getTransform().setMyy(scale);
//            } else {
//                scale += zoom;
//                gc.getTransform().setMxx(scale);
//                gc.getTransform().setMyy(scale);
//            }
//        });
    }

    public static int getFPS() {
        return FPS;
    }

    public Render getRender() {
        return render;
    }

    public Session getSession() {
        return session;
    }
}