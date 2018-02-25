package ch.network.host;

import ch.network.PacketHandler;
import ch.network.packets.*;
import com.jmr.wrapper.common.Connection;
import com.jmr.wrapper.common.exceptions.NNCantStartServer;
import com.jmr.wrapper.common.listener.SocketListener;
import com.jmr.wrapper.server.Server;

import java.util.ArrayList;

public class Host implements SocketListener, PacketHandler.PackageListener {

    private Server server;
    private ArrayList<JoinPacket> joinPackets;
    private PacketHandler packetHandler;

    public Host(int port) {
        joinPackets = new ArrayList<>();
        packetHandler = new PacketHandler(this);

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

    // PACKETS ////////////////////////////////////////////////////////////////////

    @Override
    public void handleJoin(JoinPacket packet) {
        joinPackets.add(packet);
        System.out.println("User: " + packet.username + " Color: " + packet.color + " ID: " + packet.id + " connected!");
        JoinPacket[] temp = joinPackets.toArray(new JoinPacket[joinPackets.size()]);
        LobbyPacket lobbyPacket = new LobbyPacket(temp);
        for (Connection c : ConnectionManager.getInstance().getConnections()) {
            c.sendUdp(lobbyPacket);
        }
    }

    @Override
    public void handleLeave(LeavePacket packet) {
        redirect(packet);
    }

    @Override
    public void handleLobby(LobbyPacket packet) {
        redirect(packet);
    }

    @Override
    public void handleMove(MovePacket packet) {
        redirect(packet);
    }

    @Override
    public void handleHit(HitPacket packet) {
        redirect(packet);
    }

    @Override
    public void handlePickUp(PickUpPacket packet) {
        redirect(packet);
    }

    @Override
    public void handlePlace(PlacePacket packet) {
        redirect(packet);
    }

    @Override
    public void handleShoot(ShootPacket packet) {
        redirect(packet);
    }

    @Override
    public void handleSpawn(SpawnPacket packet) {
        redirect(packet);
    }

    // CONNECTION /////////////////////////////////////////////////////////////////

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
        packetHandler.handlePacket(object);
    }

    public static void main(String[] args) {
        new Host(13013);
    }
}
