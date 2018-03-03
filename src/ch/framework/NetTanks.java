package ch.framework;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class NetTanks extends Application {

    private static NetTanks instance = new NetTanks();
    public static final int WIDTH = 1280, HEIGHT = 848;

    private Pane root;

    @Override
    public void start(Stage primaryStage) {

        root = new Pane();
        root.setPrefWidth(WIDTH);
        root.setPrefHeight(HEIGHT);
        Scene scene = new Scene(root, WIDTH - 10, HEIGHT - 10);

        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
        primaryStage.setOnCloseRequest(event -> System.exit(0));

        Mainframe mainframe = new Mainframe(WIDTH, HEIGHT);
        root.getChildren().add(mainframe);

        mainframe.startOnlineSession("localhost:13013", "blub", "#666666");

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

    public static NetTanks getInstance() {
        return instance;
    }

    public static void main(String[] args) {
        NetTanks netTanks = NetTanks.getInstance();
        netTanks.launch(args);
    }
}
