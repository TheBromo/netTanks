package ch.network;

import ch.framework.Framework;
import ch.framework.NetTanks;
import ch.framework.Render;
import ch.framework.gameobjects.tank.Tank;
import ch.framework.gameobjects.tank.Turret;
import ch.network.packet.Move;
import ch.framework.Player;
import ch.network.packet.Hello;
import ch.network.packet.Spawn;
import com.jmr.wrapper.common.Connection;
import com.jmr.wrapper.common.listener.SocketListener;
import com.jmr.wrapper.client.Client;
import javafx.scene.layout.Pane;

import java.util.ArrayList;

public class Session implements SocketListener {

    private Player player;
    private ArrayList<Player> players;
    private Framework framework;

    private Client client;

    public Session(String host, String username, String color) {
        players = new ArrayList<>();
        this.player = new Player(username, color);
        connectTo(host);
    }

    public void startMatch(Pane root) {
        framework = new Framework(this);
        root.getChildren().add(framework.getRender());
        framework.getHandler().spawnPlayer(player);
        framework.start();
    }

    public void connectTo(String host) {

        String address = host.split(":")[0];
        int port = Integer.parseInt(host.split(":")[1]);

        client = new Client(address, port, port);

        client.setListener(this);
        client.connect();

        if (client.isConnected()) {
            Hello hello = new Hello(player.getUsername(), player.getColor(), player.getId());
            client.getServerConnection().sendTcp(hello);
        }
    }

    public void handleMove(Move move, boolean notify) {

        if (!notify) {
            for (Player player : players) {
                if (player.getId().compareTo(move.id) == 0) {
                    //IDs match
                    Tank tank = player.getTank();
                    tank.setX(move.x);
                    tank.setY(move.y);
                    tank.setRotation(move.rot);

                    Turret turret = tank.getTurret();

                    float rot = ((float) Math.toDegrees(Math.atan2((move.my - tank.getY()), (move.mx - tank.getX()))) + 90);
                    if (rot < 0) {
                        rot += 360;
                    }

                    turret.setRotation(rot);

                }
            }
        } else {
            client.getServerConnection().sendUdp(move);
        }
    }

    public void handleChange() {
        System.out.println("Notified");
        handleMove(new Move(player.getId(), player.getTank().getX(), player.getTank().getY(), player.getTank().getRotation(), (float) NetTanks.getInstance().getMouseX(), (float) NetTanks.getInstance().getMouseY()), true);
    }

    @Override
    public void connected(Connection con) {
        System.out.println("Connected to Host.");
    }

    @Override
    public void disconnected(Connection con) {
        System.out.println("Disconnected.");
    }

    @Override
    public void received(Connection con, Object object) {

        if (object instanceof Move) {
            Move move = (Move) object;
            handleMove(move, false);
            System.out.println("x: " + move.x + " y: " + move.y + " rot: " + move.rot);
        }

        if (object instanceof Hello) {
            Hello hello = (Hello) object;

            // Check whether it's us
            if (hello.id.compareTo(player.getId()) != 0) {
                //If not -> add Player:
                Player player = new Player(hello.username, hello.color);
                player.setId(hello.id);
                framework.getHandler().spawnPlayer(player);
                players.add(player);
            }
        }

        if (object instanceof Spawn) {
            Spawn spawn = (Spawn) object;


        }
    }

    public Player getPlayer() {
        return player;
    }

    public Framework getFramework() {
        return framework;
    }

}