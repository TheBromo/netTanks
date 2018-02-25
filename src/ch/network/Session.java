package ch.network;

import ch.framework.ActionListener;
import ch.framework.Handler;
import ch.framework.Player;
import ch.framework.gameobjects.Bullet;
import ch.framework.gameobjects.GameObject;
import ch.framework.gameobjects.Mine;
import ch.framework.gameobjects.tank.Tank;
import ch.framework.gameobjects.tank.Turret;
import ch.network.packets.*;
import com.jmr.wrapper.client.Client;
import com.jmr.wrapper.common.Connection;
import com.jmr.wrapper.common.listener.SocketListener;

import java.util.ArrayList;
import java.util.UUID;

public class Session implements SocketListener, ActionListener, PacketListener {

    private float mx, my;
    private int ticks;
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

        System.out.println("ID: " + player.getId());
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

        if (ticks >= 60) { // Once per second. Send Player's position for correction.
            if (player.getTank() != null) {
                Tank tank = player.getTank();
                CorrectionPacket correctionPacket = new CorrectionPacket(player.getId(), tank.getX(), tank.getY(), tank.getRotation(), tank.getTurret().getRotation());
                client.getServerConnection().sendUdp(correctionPacket);
            }

            ticks = 0;
        }
        ticks++;
    }


    // PLAYER MOVEMENT /////////////////////////////////////////////////////

    public void spawn() {
        SpawnPacket spawnPacket = new SpawnPacket(player.getId(), 100, 100, 0);
        client.getServerConnection().sendUdp(spawnPacket);
    }

    public void setVelocity(float velocity) {
        Tank tank = player.getTank();

        if (tank != null) {
            tank.setVelocity(velocity);
            VelocityPacket velocityPacket = new VelocityPacket(player.getId(), tank.getVelocity(), tank.getVelRotation());
            client.getServerConnection().sendUdp(velocityPacket);
        }
    }

    public void setVelRotation(float velocity) {
        Tank tank = player.getTank();

        if (tank != null) {
            tank.setVelRotation(velocity);
            VelocityPacket velocityPacket = new VelocityPacket(player.getId(), tank.getVelocity(), tank.getVelRotation());
            client.getServerConnection().sendUdp(velocityPacket);
        }
    }

    public void mouseMoved(float rot) {

        if (player.getTank() != null) {
            Tank tank = player.getTank();
            Turret turret = tank.getTurret();
            turret.setRotation(rot);

            MousePacket mousePacket = new MousePacket(player.getId(), turret.getRotation());
            client.getServerConnection().sendUdp(mousePacket);
        }
    }

    public void shoot() {
        Tank tank = player.getTank();
        if (tank != null) {
            Bullet bullet = new Bullet(tank.getTurret().getMuzzleX(), tank.getTurret().getMuzzleY(), tank.getTurret().getRotation(), tank.getBulletType());
            ShootPacket shootPacket = new ShootPacket(player.getId(), tank.getTurret().getMuzzleX(), tank.getTurret().getMuzzleY(), tank.getTurret().getRotation(), bullet.getId(), bullet.getType());
            client.getServerConnection().sendUdp(shootPacket);
        }
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
    public void onExplosion(GameObject trigger, Mine mine) {
        if (trigger instanceof Bullet) {

        }

        if (trigger instanceof Tank) {

        }
    }

    @Override
    public void onBulletBreak(Bullet b1) {

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
    public void handleMove(CorrectionPacket packet) {
        //System.out.println("x: " + packet.x + " y: " + packet.y + " rot: " + packet.rot + " mx: " + packet.mx + " my: " + packet.my);
        if (packet.id.compareTo(player.getId()) != 0) {
            Player player = getPlayer(packet.id);

            // CorrectionPacket Tank
            Tank tank = player.getTank();
            tank.setX(packet.x);
            tank.setY(packet.y);
            tank.setRotation(packet.rot);

            // Rotate Turret
            Turret turret = tank.getTurret();
            turret.setRotation(packet.trot);
        }
    }

    @Override
    public void handleHit(HitPacket packet) {
        Player player = getPlayer(packet.id);
        handler.removeTank(player.getTank());
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
        handler.handleShot(player.getTank(), new Bullet(packet.bullet, packet.x, packet.y, packet.rot, packet.type));
    }

    @Override
    public void handleSpawn(SpawnPacket packet) {
        Player player = getPlayer(packet.id);
        Tank tank = handler.spawnTank(packet.x, packet.y, packet.rot);
        player.setTank(tank);
    }

    @Override
    public void handleDestroy(DestroyPacket packet) {
        Bullet bullet = getBullet(packet.id);
        if (bullet != null) {
            handler.removeBullet(bullet);
        }

        // TODO: BLOCK, MINE
    }

    @Override
    public void handleVelocity(VelocityPacket packet) {
        Tank tank = getTank(packet.id);
        tank.setVelocity(packet.vel);
        tank.setVelRotation(packet.velRot);
    }

    @Override
    public void handleMouse(MousePacket packet) {
        getTank(packet.id).getTurret().setRotation(packet.rot);
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

    public Handler getHandler() {
        return handler;
    }

}