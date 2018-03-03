package ch.framework;

import ch.framework.session.OnlineSession;
import ch.framework.session.Session;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class Mainframe extends Pane {

    private static int FPS;
    private Timeline gameloop;
    private Camera camera;
    private Session session;
    private Player player;

    public Mainframe(int width, int height) {

        this.setWidth(width);
        this.setHeight(height);

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

    public void startOnlineSession(String host, String username, String color) {
        player = new Player(username, color);
        OnlineSession session = new OnlineSession(player);
        session.connectTo(host);

        this.session = session;
        camera = new Camera(this, session.getHandler());
        camera.setLocation( (float) - this.getWidth() / 2 - 32, (float) - this.getHeight() / 2 - 32);
        gameloop.play();
    }

    public void clear() {

    }

    public void stop() {
        gameloop.stop();
    }

    /**
     * Gets called 60 times per second by the gameloop.
     * Used to tick and camera objects etc.
     */
    private void update() {

        FPS++;

        //Update all the things!
        camera.render();
        session.tick();

    }

    private void setKeyInput() {

        this.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case W:
                    player.changeVelocity(-1.5f);
                    break;
                case A:
                    player.changeRotation(-1.2f);
                    break;
                case S:
                    player.changeVelocity(1.5f);
                    break;
                case D:
                    player.changeRotation(1.2f);
                    break;
                case TAB:
                    //hud.setPlayerInfoVisibility(true);
                    break;
            }

        });

        this.setOnKeyReleased(event -> {
            switch (event.getCode()) {
                case W:
                    player.changeVelocity(0);
                    break;
                case A:
                    player.changeRotation(0);
                    break;
                case S:
                    player.changeVelocity(0);
                    break;
                case D:
                    player.changeRotation(0);
                    break;
                case F1:
                    //hud.toggleOverlayVisibility();
                    break;
                case TAB:
                    //hud.setPlayerInfoVisibility(false);
                    break;
                case C:
                    //player.setColor(new Color(random.nextDouble(), random.nextDouble(), random.nextDouble(), 1));
                    player.spawn();
                    break;
                case SPACE:
                    player.place();
                    break;
            }
        });
    }

    private void setMouseInput() {
        this.setOnMouseClicked(event -> {
            player.shoot();
        });


        this.setOnMousePressed(event -> {

        });

        this.setOnMouseReleased(event -> {

        });

        this.setOnMouseMoved(event -> {
            float mx = (float) event.getX();
            float my = (float) event.getY();
            player.changeTurretRotation(mx, my);
        });

        this.setOnMouseDragged(event -> {
            //Still tick mouse position even if the mouse buttons are down...
            float mx = (float) event.getX();
            float my = (float) event.getY();
            player.changeTurretRotation(mx, my);
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

    public Camera getCamera() {
        return camera;
    }

    public Session getSession() {
        return session;
    }
}