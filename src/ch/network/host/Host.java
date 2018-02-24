package ch.network.host;

import com.jmr.wrapper.common.exceptions.NNCantStartServer;
import com.jmr.wrapper.server.Server;

public class Host {

    private Server server;

    public Host(int port) {
        try {
            server = new Server(port, port);
            server.setListener(new ServerListener());
            if (server.isConnected()) {
                System.out.println("Server has started.");
            }
        } catch (NNCantStartServer e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Host(13013);
    }
}
