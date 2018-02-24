package ch.menu;

import ch.framework.Framework;
import javafx.scene.control.Button;

public class MainMenu extends Menu {

    private Button button1;
    private Button button2;

    public MainMenu(Framework framework) {
        button1 = new Button("Resume");
        button2 = new Button("Quit");

        button1.setOnAction(event -> {

        });
        this.addButton(button1);
    }
}
