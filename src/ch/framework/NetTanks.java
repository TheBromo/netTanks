package ch.framework;

import ch.network.Session;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class NetTanks extends Application {

    private static NetTanks instance = new NetTanks();
    public static int WIDTH = 960, HEIGHT = 640;

    private Pane root;
    private Session session;

    @Override
    public void start(Stage primaryStage) {

        root = new Pane();
        root.setPrefWidth(WIDTH);
        root.setPrefHeight(HEIGHT);
        Scene scene = new Scene(root, WIDTH, HEIGHT);

        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
        primaryStage.setOnCloseRequest(event -> System.exit(0));

        Mainframe mainframe = new Mainframe();
        root.getChildren().add(mainframe);

        session = new Session("blubber", "#666666");
        session.connectTo("localhost:13013");

        mainframe.start(session);

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
