package ch.network.host;

import ch.framework.ActionListener;
import ch.framework.Player;
import ch.framework.gameobjects.Bullet;
import ch.framework.gameobjects.GameObject;
import ch.framework.gameobjects.Mine;
import ch.framework.gameobjects.tank.Tank;
import ch.framework.map.Block;
import ch.network.Connection;
import ch.network.Packet;
import ch.network.PacketListener;
import ch.network.PacketManager;
import ch.network.SocketListener;
import ch.network.packets.*;

import java.util.ArrayList;

public class Host implements SocketListener, PacketListener, ActionListener {

    private PacketManager packetManager;
    private ArrayList<Connection> connections;

    private HostSession session;

    public Host(int port) {
        packetManager = new PacketManager(this);
        connections = new ArrayList<>();


    }

    private void sendTo(Connection con, Packet ... packets) {
        for (Packet packet : packets) {
            con.send(packet);
        }
    }

    private void broadcast(Packet ... packets) {
        for (Connection c : connections) {
            for (Packet packet : packets) {
                c.send(packet);
            }
        }
    }

    public void startSession() {
        session = new HostSession();
        session.start();
    }

    // PACKETS ////////////////////////////////////////////////////////////////////


    @Override
    public void handleWelcome(WelcomePacket packet, Connection con) {

    }

    @Override
    public void handleJoin(JoinPacket packet, Connection connection) {
//        System.out.println("User: " + packet.username + " Color: " + packet.color + " ID: " + connection.getId() + " connected!");
//
//        if (connections.contains(connection)) {
//            Player player = new Player(packet.username, packet.color, connection.getId());
//
//            ArrayList<Object> objects = new ArrayList<>();
//            for (Player p : players) {
//
//                // Send User
//                JoinPacket jp = new JoinPacket(p.getUsername(), p.getColor());
//                jp.id = p.getId();
//                //objects.add(jp);
//                System.out.println(connection.getUdpPort());
//                connection.sendUdp(jp);
//
//                // Send Tank
//                Tank tank = handler.getTank(p.getTank());
//                if (tank != null) {
//                    SpawnPacket sp = new SpawnPacket(tank.getId(), tank.getX(), tank.getY(), tank.getRotation());
//                    sp.id = p.getId();
//                    //objects.add(sp);
//                    connection.sendUdp(sp);
//                }
//
//                // Send Bullets
//                for (Bullet bullet : handler.getBullets(p.getBullets())) {
//                    ShootPacket sp = new ShootPacket(bullet.getX(), bullet.getY(), bullet.getRotation(), bullet.getId(), bullet.getType());
//                    sp.id = p.getId();
//                    //objects.add(sp);
//                    connection.sendUdp(sp);
//                }
//
//                for (Mine mine : handler.getMines(p.getMines())) {
//                    // TODO send mine packets
//                }
//            }
//            sendTo(connection, objects);
//
//            players.add(player);
//
//            // Inform the other players in the Session:
//            JoinPacket joinPacket = new JoinPacket(player.getUsername(), player.getColor());
//            joinPacket.id = player.getId();
//            broadcast(joinPacket);
//        }

        Player player = new Player(packet.username, packet.color);
        player.setId(connection.getId());
        session.addPlayer(player);

        WelcomePacket wp = new WelcomePacket(player.getId());
        sendTo(connection, wp);

        System.out.println("Player joined: " + player.getUsername() + " ID: " + player.getId() + " Color:" + player.getColor() + " Port:" + connection.getUdpPort());
        packet.id = connection.getId();
        broadcast(packet);
    }

    @Override
    public void handleLeave(LeavePacket packet, Connection connection) {
        Player player = session.getPlayer(connection.getId());
        if (player != null) {
            System.out.println("Player left: " + player.getUsername() + " ID: " + player.getId() + " Color:" + player.getColor());
            session.removePlayer(player);
        }
    }

    @Override
    public void handleLobby(LobbyPacket packet, Connection connection) {
        //redirect(packet);
    }

    @Override
    public void handleMove(CorrectionPacket packet, Connection connection) {
        //redirect(packet);
    }

    @Override
    public void handleHit(HitPacket packet, Connection connection) {
        redirect(packet);
    }

    @Override
    public void handlePickUp(PickUpPacket packet, Connection connection) {
        redirect(packet);
    }

    @Override
    public void handlePlace(PlacePacket packet, Connection connection) {
        redirect(packet);
    }

    @Override
    public void handleShoot(ShootPacket packet, Connection connection) {
        redirect(packet);
    }

    @Override
    public void handleSpawn(SpawnPacket packet, Connection connection) {
//        Player player = session.getPlayer(connection.getId());
//        Tank tank = new Tank(packet.tank, packet.x, packet.y, packet.rot);
//        player.handleSpawn(tank);
    }

    @Override
    public void handleDestroy(DestroyPacket packet, Connection connection) {
        //redirect(packet);
    }

    @Override
    public void handleVelocity(VelocityPacket packet, Connection connection) {
        redirect(packet);
    }

    @Override
    public void handleMouse(MousePacket packet, Connection connection) {
        redirect(packet);
    }

    // CONNECTION /////////////////////////////////////////////////////////////////

    @Override
    public void connected(Connection con) {
        System.out.println("Session connected. ID:" + con.getId() + " IP:" + con.getAddress() + ":" + con.getUdpPort());
        connections.add(con);
    }

    @Override
    public void disconnected(Connection con) {
        System.out.println("Session disconnected. ID:" + con.getId() + " IP:" + con.getAddress() + ":" + con.getUdpPort());

        LeavePacket lp = new LeavePacket(con.getId());
        handleLeave(lp, con);
        broadcast(lp);

        connections.remove(con);
    }

    @Override
    public void received(Connection con, Object object) {
        packetManager.handlePacket(object, con);
    }

    public static void main(String[] args) {
        Host host = new Host(13013);
        host.startSession();
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
