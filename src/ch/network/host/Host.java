package ch.network.host;

import ch.network.packets.*;
import com.jmr.wrapper.common.Connection;
import com.jmr.wrapper.common.exceptions.NNCantStartServer;
import com.jmr.wrapper.common.listener.SocketListener;
import com.jmr.wrapper.server.Server;

import java.util.ArrayList;

public class Host implements SocketListener {

    private Server server;
    private ArrayList<Connect> connects;

    public Host(int port) {
        connects = new ArrayList<>();
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

    private void redirect(Object object) {
        for (Connection c : ConnectionManager.getInstance().getConnections()) {
            c.sendUdp(object);
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

    }

    @Override
    public void received(Connection con, Object object) {

        if (object instanceof Connect) {
            Connect connect = (Connect) object;
            connects.add(connect);
            System.out.println("User: " + connect.username + " Color: " + connect.color + " ID: " + connect.id + " connected!");
            Connect[] temp = connects.toArray(new Connect[connects.size()]);
            Lobby lobby = new Lobby(temp);
            for (Connection c : ConnectionManager.getInstance().getConnections()) {
                c.sendUdp(lobby);
            }
        }

        if (object instanceof Disconnect) {

        }

        if (object instanceof Move ||
                object instanceof Shoot ||
                object instanceof PickUp ||
                object instanceof Place ||
                object instanceof Spawn) {

            redirect(object);
        }

    }

    public static void main(String[] args) {
        new Host(13013);
    }
}
