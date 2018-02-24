package ch.network;

import ch.framework.Handler;
import ch.framework.Mainframe;
import ch.framework.gameobjects.tank.Tank;
import ch.framework.gameobjects.tank.Turret;
import ch.network.packets.Lobby;
import ch.network.packets.Move;
import ch.framework.Player;
import ch.network.packets.Hello;
import ch.network.packets.Spawn;
import com.jmr.wrapper.common.Connection;
import com.jmr.wrapper.common.listener.SocketListener;
import com.jmr.wrapper.client.Client;

import java.util.ArrayList;
import java.util.UUID;

public class Session implements SocketListener {

    private double mouseX, mouseY;
    private Player player;
    private ArrayList<Player> players;

    private Handler handler;

    private Client client;

    public Session(String host, String username, String color) {
        players = new ArrayList<>();
        this.player = new Player(username, color, UUID.randomUUID());
        players.add(player);
        handler = new Handler();

        System.out.println(player.getId());
        connectTo(host);
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

    public void tick() {
        handler.update();

        Tank tank = player.getTank();
        Move move = new Move(player.getId(), tank.getX(), tank.getY(), tank.getRotation(), (float) mouseX, (float) mouseY);
        client.getServerConnection().sendUdp(move);
    }

    public void handleMoveEvent(Move move) {
        System.out.println("x: " + move.x + " y: " + move.y + " rot: " + move.rot + " mx: " + move.mx + " my: " +move.my);
        Player player = getPlayer(move.id);

        // Move Tank
        Tank tank = player.getTank();
        tank.setX(move.x);
        tank.setY(move.y);
        tank.setRotation(move.rot);

        // Rotate Turret
        Turret turret = tank.getTurret();
        float rot = ((float) Math.toDegrees(Math.atan2((move.my - tank.getY()), (move.mx - tank.getX()))) + 90);
        if (rot < 0) {
            rot += 360;
        }
        turret.setRotation(rot);

    }
    public void test() {
        Spawn spawn = new Spawn(player.getId(), 100, 100, 0);
        client.getServerConnection().sendUdp(spawn);
    }

    public void setVelocity(float velocity) {
        if (player.getTank() != null) {
            player.getTank().setVelocity(velocity);
        }
//        System.out.println("Notified");
//        handleMoveEvent(new Move(player.getId(), player.getTank().getX(), player.getTank().getY(), player.getTank().getRotation(), (float) mainframe.getMouseX(), (float) mainframe.getMouseY()));
    }

    public void setVelRotation(float velocity) {
        if (player.getTank() != null) {
            player.getTank().setVelRotation(velocity);
        }
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
            handleMoveEvent(move);
            //System.out.println("x: " + move.x + " y: " + move.y + " rot: " + move.rot);
        }

        if (object instanceof Hello) {
            Hello hello = (Hello) object;

            // Check whether it's us
            if (hello.id.compareTo(player.getId()) != 0) {
                //If not -> add Player:
                Player player = new Player(hello.username, hello.color, hello.id);
                players.add(player);
            }
        }

        if (object instanceof Lobby) {
            Lobby lobby = (Lobby) object;
            Hello[] hellos = lobby.hellos;

            for (Hello hello : hellos) {
                if (hello.id.compareTo(player.getId()) != 0) {
                    Player player = new Player(hello.username, hello.color, hello.id);
                    players.add(player);
                }
            }
        }

        if (object instanceof Spawn) {
            Spawn spawn = (Spawn) object;

            Player player = getPlayer(spawn.id);
            Tank tank = handler.spawnTank(spawn.x, spawn.y, spawn.rot);
            player.setTank(tank);

        }
    }

    public Player getPlayer() {
        return player;
    }

    public Player getPlayer(UUID id) {
        for (Player player : players) {
            if (id.compareTo(player.getId()) == 0) {
                return player;
            }
        }

        return null;
    }

    public double getMouseX() {
        return mouseX;
    }

    public void setMouseX(double mouseX) {
        this.mouseX = mouseX;
    }

    public double getMouseY() {
        return mouseY;
    }

    public void setMouseY(double mouseY) {
        this.mouseY = mouseY;
    }

    public Handler getHandler() {
        return handler;
    }
}