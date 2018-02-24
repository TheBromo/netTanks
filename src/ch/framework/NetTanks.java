package ch.framework;

import ch.network.Session;
import ch.network.packet.Move;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class NetTanks extends Application {

    private static NetTanks instance = new NetTanks();
    public static int WIDTH = 960, HEIGHT = 640;
    private double mouseX, mouseY;

    private Pane root;
    private Session session;
    //private HUD hud;

    @Override
    public void start(Stage primaryStage) {

        root = new Pane();
        root.setPrefWidth(WIDTH);
        root.setPrefHeight(HEIGHT);
        root.setFocusTraversable(true);
        Scene scene = new Scene(root, WIDTH, HEIGHT);

        setKeyInput();
        setMouseInput();

        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();


        session = new Session("192.168.3.75:13013", "blubber", "#666666");
        session.startMatch(root);


        //hud = new HUD(framework);

        root.requestFocus();
    }

    private void setKeyInput() {

        root.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case W:
                    session.getPlayer().getTank().setVelocity(-1.5f);

                    break;
                case A:
                    session.getPlayer().getTank().setVelRotation(-1);

                    break;
                case S:
                    session.getPlayer().getTank().setVelocity(1.5f);

                    break;
                case D:
                    session.getPlayer().getTank().setVelRotation(1);

                    break;

                case TAB:
                    //hud.setPlayerInfoVisibility(true);
                    break;
            }


        });

        root.setOnKeyReleased(event -> {
            switch (event.getCode()) {
                case W:
                    session.getPlayer().getTank().setVelocity(0);
                    break;
                case A:
                    session.getPlayer().getTank().setVelRotation(0);

                    break;
                case S:
                    session.getPlayer().getTank().setVelocity(0);

                    break;
                case D:
                    session.getPlayer().getTank().setVelRotation(0);

                    break;
                case F1:
                    //hud.toggleOverlayVisibility();
                    break;
                case TAB:
                    //hud.setPlayerInfoVisibility(false);
                    break;
                case C:
                    //player.setColor(new Color(random.nextDouble(), random.nextDouble(), random.nextDouble(), 1));
                    break;
            }
        });
    }

    private void setMouseInput() {
        root.setOnMouseClicked(event -> {
            System.out.println("hey");
        });


        root.setOnMousePressed(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                //handler.handleShot(player.getTank());
            }
        });

        root.setOnMouseReleased(event -> {

        });

        root.setOnMouseMoved(event -> {
            //Tell the Canvas to update mouseX and mouseY every time the mouse was moved
            mouseX = event.getX();
            mouseY = event.getY();
        });

        root.setOnMouseDragged(event -> {
            //Still update mouse position even if the mouse buttons are down...
            mouseX = event.getX();
            mouseY = event.getY();
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

    public Session getSession() {
        return session;
    }

    public int getWidth() {
        return WIDTH;
    }

    public int getHeight() {
        return HEIGHT;
    }

    public double getMouseX() {
        return mouseX;
    }

    public double getMouseY() {
        return mouseY;
    }

    public Pane getRoot() {
        return root;
    }

//    public HUD getHud() {
//        return hud;
//    }


    public static void main(String[] args) {
        NetTanks netTanks = NetTanks.getInstance();
        netTanks.launch(args);
    }

    public static NetTanks getInstance() {
        return instance;
    }
}
