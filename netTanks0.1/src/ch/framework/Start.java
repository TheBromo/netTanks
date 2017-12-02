package ch.framework;

import ch.match.GameMode;
import ch.match.Player;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Start extends Application {

    public static final int WIDTH = 960, HEIGHT = 640;

    @Override
    public void start(Stage primaryStage) {
        Pane root = new Pane();
        Framework framework = new Framework(WIDTH, HEIGHT);
        root.getChildren().add(framework);

        Scene scene = new Scene(root, WIDTH, HEIGHT);

        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();

        //Match match = new Match(GameMode.FREEFORALL);

        Player player = new Player("My Username");
//        match.addPlayer(new Player("Player 1"));
//        match.addPlayer(player);

        framework.setPlayer(player);
        framework.start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
