package ch.framework;

import ch.match.Player;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Start extends Application {

    public static final int WIDTH = 960, HEIGHT = 640;

    @Override
    public void start(Stage primaryStage) {

        Framework framework = new Framework(WIDTH, HEIGHT);
        Scene scene = new Scene(framework, WIDTH, HEIGHT);

        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();

        //Match match = new Match(GameMode.FREEFORALL);

        Player player = new Player("My Username");
        player.setColor(Color.valueOf("#5cb0cc"));
//        match.addPlayer(new Player("Player 1"));
//        match.addPlayer(player);

        framework.setPlayer(player);
        framework.start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
