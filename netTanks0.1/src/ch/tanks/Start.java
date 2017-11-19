package ch.tanks;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Start extends Application {

    public static int WIDTH = 960, HEIGHT = 640;

    @Override
    public void start(Stage primaryStage) {
        Pane root = new Pane();
        Framework framework = new Framework(WIDTH, HEIGHT);
        root.getChildren().add(framework);

        Scene scene = new Scene(root, WIDTH, HEIGHT);

        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();

        framework.start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
