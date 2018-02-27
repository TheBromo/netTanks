package ch.network.host;

import ch.framework.ActionListener;
import ch.framework.Handler;
import ch.framework.Player;
import ch.framework.Session;
import ch.framework.gameobjects.Bullet;
import ch.framework.gameobjects.GameObject;
import ch.framework.gameobjects.Mine;
import ch.framework.gameobjects.tank.Tank;
import ch.framework.map.Block;
import ch.network.PacketManager;
import ch.network.PacketListener;
import ch.network.packets.*;
import com.jmr.wrapper.common.Connection;
import com.jmr.wrapper.common.exceptions.NNCantStartServer;
import com.jmr.wrapper.common.listener.SocketListener;
import com.jmr.wrapper.server.Server;

import java.util.ArrayList;
import java.util.HashMap;

public class Host implements SocketListener, PacketListener, ActionListener {

    private Server server;
    private PacketManager packetManager;

    private Handler handler;
    private ArrayList<Player> players;
    private HashMap<Player, Connection> playerConnetions;
    private ArrayList<Connection> connections;

    public Host(int port) {
        packetManager = new PacketManager(this);
        connections = new HashMap<>();

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
        for (Connection c : connections) {
            c.sendUdp(object);
        }
    }

    public void startSession() {
        players.clear();
        handler = new Handler();
    }

    // PACKETS ////////////////////////////////////////////////////////////////////

    @Override
    public void handleJoin(JoinPacket packet) {
        System.out.println("User: " + packet.username + " Color: " + packet.color + " ID: " + packet.id + " connected!");

        LobbyPacket lobbyPacket = new LobbyPacket(temp);
        for (Connection c : connections.values()) {
            c.sendUdp(lobbyPacket);
        }
    }

    @Override
    public void handleLeave(LeavePacket packet) {
        //redirect(packet);
    }

    @Override
    public void handleLobby(LobbyPacket packet) {
        //redirect(packet);
    }

    @Override
    public void handleMove(CorrectionPacket packet) {
        //redirect(packet);
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

    @Override
    public void handleDestroy(DestroyPacket packet) {
        redirect(packet);
    }

    @Override
    public void handleVelocity(VelocityPacket packet) {
        redirect(packet);
    }

    @Override
    public void handleMouse(MousePacket packet) {
        redirect(packet);
    }

    // CONNECTION /////////////////////////////////////////////////////////////////

    @Override
    public void connected(Connection con) {
        System.out.println("Session connected.");
        Player pLayer = new Player()
    }

    @Override
    public void disconnected(Connection con) {
        System.out.println("Session disconnected.");
        ConnectionManager.getInstance().removeConnection(con);
        this.
    }

    @Override
    public void received(Connection con, Object object) {
        packetManager.handlePacket(object);
    }

    public static void main(String[] args) {
        new Host(13013);
    }

    // HANDLER EVENTS /////////////////////////////////////////////////////////////


    @Override
    public void onKill(GameObject trigger, Tank tank) {

    }

    @Override
    public void onBlockDestroyed(GameObject trigger, Block block) {

    }

    @Override
    public void onBulletBreak(Bullet b1) {

    }

    @Override
    public void onExplosion(GameObject trigger, Mine mine) {

    }
}