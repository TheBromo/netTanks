package ch.framework;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Window extends Stage {

    private Pane root;
    private int width, height;

    public Window(int width, int height) {
        this.width = width;
        this.height = height;

        root = new Pane();
        root.setPrefWidth(width);
        root.setPrefHeight(height);
        Scene scene = new Scene(root, width - 10, height - 10);

        this.setScene(scene);
        this.setResizable(false);
        this.show();
        this.setOnCloseRequest(event -> System.exit(0));
    }

    public void setView(Node node) {
        root.getChildren().clear();
        root.getChildren().add(node);
    }
}
