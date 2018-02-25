package ch.network;

import ch.framework.ActionListener;
import ch.framework.Handler;
import ch.framework.gameobjects.Bullet;
import ch.framework.gameobjects.Mine;
import ch.framework.gameobjects.tank.Tank;
import ch.framework.gameobjects.tank.Turret;
import ch.network.packets.*;
import ch.framework.Player;
import com.jmr.wrapper.common.Connection;
import com.jmr.wrapper.common.listener.SocketListener;
import com.jmr.wrapper.client.Client;

import java.util.ArrayList;
import java.util.UUID;

public class Session implements SocketListener, ActionListener, PacketHandler.PackageListener {

    private double mouseX, mouseY;
    private Player player;
    private ArrayList<Player> players;

    private Handler handler;
    private PacketHandler packetHandler;
    private Client client;

    public Session(String username, String color) {
        players = new ArrayList<>();
        this.player = new Player(username, color, UUID.randomUUID());
        players.add(player);

        handler = new Handler();
        handler.setActionListener(this);

        packetHandler = new PacketHandler(this);

        System.out.println(player.getId());
    }

    public void connectTo(String host) {
        String address = host.split(":")[0];
        int port = Integer.parseInt(host.split(":")[1]);

        client = new Client(address, port, port);

        client.setListener(this);
        client.connect();

        if (client.isConnected()) {
            JoinPacket joinPacket = new JoinPacket(player.getId(), player.getUsername(), player.getColor());
            client.getServerConnection().sendTcp(joinPacket);
        }
    }

    public void tick() {
        handler.update();

        if (player.getTank() != null) {
            Tank tank = player.getTank();
            MovePacket movePacket = new MovePacket(player.getId(), tank.getX(), tank.getY(), tank.getRotation(), (float) mouseX, (float) mouseY);
            client.getServerConnection().sendUdp(movePacket);
        }
    }


    // PLAYER MOVEMENT /////////////////////////////////////////////////////

    public void spawn() {
        SpawnPacket spawnPacket = new SpawnPacket(player.getId(), 100, 100, 0);
        client.getServerConnection().sendUdp(spawnPacket);
    }

    public void setVelocity(float velocity) {
        if (player.getTank() != null) {
            player.getTank().setVelocity(velocity);
        }
//        System.out.println("Notified");
//        handleMoveEvent(new MovePacket(player.getId(), player.getTank().getX(), player.getTank().getY(), player.getTank().getRotation(), (float) mainframe.getMouseX(), (float) mainframe.getMouseY()));
    }

    public void setVelRotation(float velocity) {
        if (player.getTank() != null) {
            player.getTank().setVelRotation(velocity);
        }
    }

    public void shoot() {
        Tank tank = player.getTank();
        ShootPacket shootPacket = new ShootPacket(player.getId(), tank.getTurret().getMuzzleX(), tank.getTurret().getMuzzleY(), tank.getTurret().getRotation());
        client.getServerConnection().sendUdp(shootPacket);
    }

    public void place() {

    }

    public void pickUp() {

    }

    // HANDLER ACTIONS //////////////////////////////////////////////////////////

    @Override
    public void onHit(Tank tank, Bullet bullet) {
        System.out.println("Muahahaha");
    }

    @Override
    public void onExplosion(Tank tank, Mine mine) {

    }

    // NETWORK PACKETS //////////////////////////////////////////////////////////

    @Override
    public void handleJoin(JoinPacket packet) {
        // Check whether it's us
        if (packet.id.compareTo(player.getId()) != 0) {
            //If not -> add Player:
            Player player = new Player(packet.username, packet.color, packet.id);
            players.add(player);
        }
    }

    @Override
    public void handleLeave(LeavePacket packet) {
        players.remove(getPlayer(packet.id));
        //TODO
    }

    @Override
    public void handleLobby(LobbyPacket packet) {
        JoinPacket[] joinPackets = packet.joinPackets;

        for (JoinPacket joinPacket : joinPackets) {
            if (joinPacket.id.compareTo(player.getId()) != 0) {
                Player player = new Player(joinPacket.username, joinPacket.color, joinPacket.id);
                players.add(player);
            }
        }
    }

    @Override
    public void handleMove(MovePacket packet) {
        //System.out.println("x: " + packet.x + " y: " + packet.y + " rot: " + packet.rot + " mx: " + packet.mx + " my: " + packet.my);
        Player player = getPlayer(packet.id);

        // MovePacket Tank
        Tank tank = player.getTank();
        tank.setX(packet.x);
        tank.setY(packet.y);
        tank.setRotation(packet.rot);

        // Rotate Turret
        Turret turret = tank.getTurret();
        float rot = ((float) Math.toDegrees(Math.atan2((packet.my - tank.getY()), (packet.mx - tank.getX()))) + 90);
        if (rot < 0) {
            rot += 360;
        }
        turret.setRotation(rot);
    }

    @Override
    public void handleHit(HitPacket packet) {
        Player player = getPlayer(packet.id);
        handler.handleHit(getBullet(packet.oid), player.getTank());
    }

    @Override
    public void handlePickUp(PickUpPacket packet) {

    }

    @Override
    public void handlePlace(PlacePacket packet) {

    }

    @Override
    public void handleShoot(ShootPacket packet) {
        Player player = getPlayer(packet.id);
        handler.handleShot(player.getTank());
    }

    @Override
    public void handleSpawn(SpawnPacket packet) {
        Player player = getPlayer(packet.id);
        Tank tank = handler.spawnTank(packet.x, packet.y, packet.rot);
        player.setTank(tank);
    }


    // NETWORK ACTIONS //////////////////////////////////////////////////////////

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
        packetHandler.handlePacket(object);
    }

    // GETTERS & SETTERS ////////////////////////////////////////////////////////

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

    public Bullet getBullet(UUID id) {
        for (Bullet bullet : handler.getBullets()) {
            if (id.compareTo(bullet.getId()) == 0) {
                return bullet;
            }
        }

        return null;
    }

    public Tank getTank(UUID id) {
        return getPlayer(id).getTank();
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