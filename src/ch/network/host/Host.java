package ch.network.host;

import ch.network.packets.Hello;
import ch.network.packets.Lobby;
import ch.network.packets.Move;
import ch.network.packets.Spawn;
import com.jmr.wrapper.common.Connection;
import com.jmr.wrapper.common.exceptions.NNCantStartServer;
import com.jmr.wrapper.common.listener.SocketListener;
import com.jmr.wrapper.server.Server;

import java.util.ArrayList;

public class Host implements SocketListener {

    private Server server;
    private ArrayList<Hello> hellos;

    public Host(int port) {
        hellos = new ArrayList<>();
        try {
            server = new Server(port, port);
            server.setListener(this);
            if (server.isConnected()) {
                System.out.println("Server has started.");
            }
        } catch (NNCantStartServer e) {
            e.printStackTrace();
        }
    }

    public void printStatus() {
        System.out.println("\b\b\b\b");
        System.out.println("State: active");
        System.out.println("Clients:");
        for (Hello hello : hellos) {
            System.out.println(hello.username + " Color: " + hello.color + " ID: " + hello.id);
        }
    }

    @Override
    public void connected(Connection con) {
        System.out.println("Session connected.");
        ConnectionManager.getInstance().addConnection(con);
    }

    @Override
    public void disconnected(Connection con) {
        System.out.println("Session disconnected.");
        ConnectionManager.getInstance().removeConnection(con);
    }

    @Override
    public void received(Connection con, Object object) {

        if (object instanceof Move) {
            Move move = (Move) object;
            //System.out.println("x: " + move.x + " y: " + move.y + " rot: " + move.rot);
            for (Connection c : ConnectionManager.getInstance().getConnections()) {
                c.sendUdp(move);
            }
        }

        if (object instanceof Hello) {
            Hello hello = (Hello) object;
            hellos.add(hello);
            //System.out.println("User: " + hello.username + " Color: " + hello.color + " ID: " + hello.id);
            Hello[] temp = hellos.toArray(new Hello[hellos.size()]);
            Lobby lobby = new Lobby(temp);
            for (Connection c : ConnectionManager.getInstance().getConnections()) {
                c.sendUdp(lobby);
            }
        }

        if (object instanceof Spawn) {
            Spawn spawn = (Spawn) object;
            System.out.println(spawn.id + " spawned!");
            for (Connection c : ConnectionManager.getInstance().getConnections()) {
                c.sendUdp(spawn);
            }
        }

        printStatus();
    }

    public static void main(String[] args) {
        new Host(13013);
    }
}
