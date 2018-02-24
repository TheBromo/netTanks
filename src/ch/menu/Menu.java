package ch.menu;

import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class Menu extends Pane {

    private StackPane center;
    private VBox container;
    private ArrayList<Button> buttons;

    public Menu() {
        this.buttons = new ArrayList<>();

        this.container = new VBox();
        this.center = new StackPane(container);
        this.getChildren().add(center);

        this.getStylesheets().add("ch/menu/styles.css");
    }

    public void addButton(Button button) {
        buttons.add(button);
        container.getChildren().add(button);
    }

    public void removeButton(Button button) {
        buttons.remove(button);
        container.getChildren().remove(button);
    }

}
