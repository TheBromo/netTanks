package ch.network.host;

import ch.framework.ActionListener;
import ch.framework.Handler;
import ch.framework.Player;
import ch.framework.gameobjects.Bullet;
import ch.framework.gameobjects.GameObject;
import ch.framework.gameobjects.Mine;
import ch.framework.gameobjects.tank.Tank;
import ch.framework.map.Block;
import ch.network.PacketListener;
import ch.network.PacketManager;
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
    private HashMap<Player, Connection> playerConnections;
    private ArrayList<Connection> connections;

    public Host(int port) {
        packetManager = new PacketManager(this);
        connections = new ArrayList<>();
        playerConnections = new HashMap<>();
        players = new ArrayList<>();

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
    public void handleJoin(JoinPacket packet, Connection connection) {
        System.out.println("User: " + packet.username + " Color: " + packet.color + " ID: " + packet.id + " connected!");

//        SessionPacket sessionPacket = new SessionPacket();
//        for (Player player : players) {
//
//            ArrayList<Bullet> b = handler.getBulletHashMap().get(player.getTank());
//            Bullet[] bullets = b.toArray(new Bullet[b.size()]);
//
//            ArrayList<Mine> m = handler.getMineHashMap().get(player.getTank());
//            Mine[] mines = m.toArray(new Mine[m.size()]);
//
//            sessionPacket.addPlayer(player, bullets, mines);
//        }



//        Player player = new Player(packet.username, packet.color, packet.id);
//        players.add(player);
        //playerConnections.put(player, con);

//        LobbyPacket lobbyPacket = new LobbyPacket(temp);
//        for (Connection c : connections) {
//            c.sendUdp(lobbyPacket);
//        }


        if (connections.contains(connection)) {
            Player player = new Player(packet.username, packet.color, connection.getId());
            players.add(player);

            JoinPacket joinPacket = new JoinPacket(player.getUsername(), player.getColor());
            joinPacket.id = player.getId();

            for (Connection c : connections) {
                if (c != connection) {
                    c.sendUdp(joinPacket);
                }
            }

            // TODO
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
        System.out.println("Session connected. ID:" + con.getId());
        connections.add(con);
    }

    @Override
    public void disconnected(Connection con) {
        System.out.println("Session disconnected. ID:" + con.getId());
        connections.remove(con);
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
