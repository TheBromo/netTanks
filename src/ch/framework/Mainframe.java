package ch.framework;

import ch.framework.gameobjects.tank.Tank;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class Mainframe extends Pane {

    private static int FPS;
    private float mouseX, mouseY;

    private Timeline gameloop;
    private Camera camera;
    private boolean follow;
    private Session session;

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

    public void start(Session session) {
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
     * Used to update and camera objects etc.
     */
    private void update() {
        FPS++;

        //Update all the things!
        camera.render();
        session.tick();

//        if (follow) {
//            float rot = ((float) Math.toDegrees(Math.atan2((-mouseY + camera.getCx()), (-mouseX + camera.getCx()))) + 90);
//            if (rot < 0) {
//                rot += 360;
//            }
//            session.mouseMoved(rot);
//        } else {
//            Tank tank = session.getPlayer().getTank();
//            if (tank != null) {
//                float rot = ((float) Math.toDegrees(Math.atan2(((mouseY - this.getHeight()/2) - camera.getCy() - tank.getY()), ((mouseX - this.getWidth()/2) - camera.getCx() - tank.getX()))) + 90);
//                if (rot < 0) {
//                    rot += 360;
//                }
//                session.mouseMoved(rot);
//            }
//        }

//        Tank tank = session.getPlayer().getTank();
//        if (tank != null) {
//            camera.setTarget(tank);
//        }
    }

    private void setKeyInput() {

        this.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case W:
                    session.setVelocity(-1.5f);
                    break;
                case A:
                    session.setVelRotation(-1.2f);
                    break;
                case S:
                    session.setVelocity(1.5f);
                    break;
                case D:
                    session.setVelRotation(1.2f);
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
                    session.spawn();
                    break;
                case SPACE:
                    session.place();
                    break;
            }
        });
    }

    private void setMouseInput() {
        this.setOnMouseClicked(event -> {
            session.shoot();
        });


        this.setOnMousePressed(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                //handler.handleShot(player.getTank());
            }
        });

        this.setOnMouseReleased(event -> {

        });

        this.setOnMouseMoved(event -> {
            mouseX = (float) event.getX();
            mouseY = (float) event.getY();
        });

        this.setOnMouseDragged(event -> {
            //Still update mouse position even if the mouse buttons are down...
            mouseX = (float) event.getX();
            mouseY = (float) event.getY();
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