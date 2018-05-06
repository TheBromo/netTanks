package ch.framework;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class NetTanks extends Application {

    private static NetTanks instance = new NetTanks();
    public static final int WIDTH = 1280, HEIGHT = 848;

    private Window window;

    @Override
    public void start(Stage primaryStage) {
        window = new Window(WIDTH, HEIGHT);

        Mainframe mainframe = new Mainframe(WIDTH, HEIGHT);
        window.setView(mainframe);
        mainframe.start();
    }

    public int getWidth() {
        return WIDTH;
    }

    public int getHeight() {
        return HEIGHT;
    }

    public static NetTanks getInstance() {
        return instance;
    }

    public static void main(String[] args) {
        NetTanks netTanks = NetTanks.getInstance();
        netTanks.launch(args);
    }
}
