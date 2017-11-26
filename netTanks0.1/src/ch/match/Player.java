package ch.match;

import ch.tanks.Tank;
import javafx.scene.paint.Color;

public class Player {

    private Tank tank;
    private String username;
    private Color color;
//    private IPwhatever ipAdress; //TODO

    public Player(Tank tank) {
        this.tank = tank;
    }

}
