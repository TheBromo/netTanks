package ch.match;

import ch.framework.gameobjects.tank.Tank;
import ch.network.PlayerState;
import javafx.scene.paint.Color;

public class Player {

    private Tank tank;
    private String username;
    private Color color;
    //    private IPwhatever ipAdress; //TODO
    private PlayerState currentPlayerState;

    public Player(String username) {
        this.username = username;
    }

    public void setCurrentPlayerState(PlayerState playerState) {
        this.currentPlayerState = playerState;

        //Turn turret to current mouse position
        float angle = ((float) Math.toDegrees(Math.atan2((playerState.getMouseY() - playerState.getY()), (playerState.getMouseX() - playerState.getX()))) + 90);
        if (angle < 0) {
            angle += 360;
        }
        tank.getTurret().setRotation(angle);

        tank.setX(playerState.getX());
        tank.setY(playerState.getY());
        tank.setRotation(playerState.getRotation());

    }

    public Tank getTank() {
        return tank;
    }

    public void setTank(Tank tank) {
        this.tank = tank;
        tank.setColor(color);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
