package ch.framework.session;

import ch.framework.Player;
import ch.framework.PlayerActionListener;
import ch.framework.gameobjects.Bullet;
import ch.framework.gameobjects.GameObject;
import ch.framework.gameobjects.Mine;
import ch.framework.gameobjects.tank.Tank;
import ch.framework.gameobjects.tank.Turret;
import ch.framework.map.Block;
import ch.network.PacketListener;
import ch.network.PacketManager;
import ch.network.packets.*;
import com.jmr.wrapper.client.Client;
import com.jmr.wrapper.common.Connection;
import com.jmr.wrapper.common.listener.SocketListener;

import java.util.UUID;

public class OnlineSession extends Session implements SocketListener, PacketListener, PlayerActionListener {

    private PacketManager packetManager;
    private Client client;

    private Player player;

    public OnlineSession(Player player) {
        packetManager = new PacketManager(this);
        this.player = player;
        player.setListener(this);
        addPlayer(player);
    }

    public void connectTo(String host) {
        String address = host.split(":")[0];
        int port = Integer.parseInt(host.split(":")[1]);

        client = new Client(address, port, port);
        client.setListener(this);
        client.connect();

    }

    public void update() {
        if (ticks >= 60) { // Once per second. Send Player's position for correction.
            if (player.getTank() != null) {
                Tank tank = player.getTank();
                CorrectionPacket correctionPacket = new CorrectionPacket(
                        tank.getX(),
                        tank.getY(),
                        tank.getRotation(),
                        tank.getTurret().getRotation());
                client.getServerConnection().sendUdp(correctionPacket);
            }

            ticks = 0;
        }
    }

    // PLAYER ACTIONS //////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void handleVelocityChanged(float vel, Player player) {
        Tank tank = player.getTank();

        if (tank != null) {
            tank.setVelocity(vel);

            VelocityPacket velocityPacket = new VelocityPacket(tank.getVelocity(), tank.getVelRotation());
            client.getServerConnection().sendUdp(velocityPacket);
        }
    }

    @Override
    public void handleRotationChanged(float vel, Player player) {
        Tank tank = player.getTank();
        if (tank != null) {
            tank.setVelRotation(vel);

            VelocityPacket velocityPacket = new VelocityPacket(tank.getVelocity(), tank.getVelRotation());
            client.getServerConnection().sendUdp(velocityPacket);
        }
    }

    @Override
    public void handleTurretRotationChanged(float rot, Player player) {
        Tank tank = player.getTank();
        if (tank != null) {
            Turret turret = tank.getTurret();
            turret.setRotation(rot);

            MousePacket mousePacket = new MousePacket(turret.getRotation());
            client.getServerConnection().sendUdp(mousePacket);
        }
    }

    @Override
    public void handleShot(Player player) {
        Tank tank = player.getTank();
        if (tank != null) {
            Bullet bullet = new Bullet(
                    UUID.randomUUID(),
                    tank.getTurret().getMuzzleX(),
                    tank.getTurret().getMuzzleY(),
                    tank.getTurret().getRotation(),
                    tank.getBulletType());

            ShootPacket shootPacket = new ShootPacket(
                    tank.getTurret().getMuzzleX(),
                    tank.getTurret().getMuzzleY(),
                    tank.getTurret().getRotation(),
                    bullet.getId(),
                    bullet.getType());
            client.getServerConnection().sendUdp(shootPacket);
        }
    }

    @Override
    public void handlePlace(Player player) {
        Tank tank = player.getTank();
        if (tank != null) {
            Mine mine = new Mine(tank.getX(), tank.getY());

            PlacePacket placePacket = new PlacePacket(mine.getX(), mine.getY(), mine.getId());
            client.getServerConnection().sendUdp(placePacket);
        }
    }

    @Override
    public void handleSpawn(Player player) {
        SpawnPacket spawnPacket = new SpawnPacket(UUID.randomUUID(),100, 100, 0);
        client.getServerConnection().sendUdp(spawnPacket);
    }

    // HANDLER ACTIONS /////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void onExplosion(GameObject trigger, Mine mine) {

    }

    @Override
    public void onBulletBreak(Bullet b1) {

    }

    @Override
    public void onKill(GameObject trigger, Tank tank) {

    }

    @Override
    public void onBlockDestroyed(GameObject trigger, Block block) {

    }

    // NETWORK PACKETS /////////////////////////////////////////////////////////////////////////////////////////////////


    @Override
    public void handleWelcome(WelcomePacket packet, Connection con) {
        player.setId(packet.id);
    }

    @Override
    public void handleJoin(JoinPacket packet, Connection connection) {
        System.out.println("Join Packet received. " + packet.id + ", " + packet.username);
        // Check whether it's us
        if (packet.id != player.getId()) {
            //If not -> add Player:
            Player player = new Player(packet.username, packet.color);
            player.setId(packet.id);
            players.add(player);

        }
    }

    @Override
    public void handleLeave(LeavePacket packet, Connection connection) {
        players.remove(getPlayer(packet.id));
        //TODO
    }

    @Override
    public void handleLobby(LobbyPacket packet, Connection connection) {
        JoinPacket[] joinPackets = packet.joinPackets;

        for (JoinPacket joinPacket : joinPackets) {
            if (joinPacket.id != player.getId()) {
                Player player = new Player(joinPacket.username, joinPacket.color);
                player.setId(joinPacket.id);
                players.add(player);
            }
        }
    }

    @Override
    public void handleMove(CorrectionPacket packet, Connection connection) {
        if (packet.id != player.getId()) {
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
    public void handleHit(HitPacket packet, Connection connection) {
        Player player = getPlayer(packet.id);
        handler.removeTank(player.getTank());
    }

    @Override
    public void handlePickUp(PickUpPacket packet, Connection connection) {

    }

    @Override
    public void handlePlace(PlacePacket packet, Connection connection) {
        Player player = getPlayer(packet.id);
        if (player.getTank() != null) {
            Mine mine = new Mine(packet.x, packet.y);
            handler.handleMinePlaced(player.getTank(), mine);
        }
    }

    @Override
    public void handleShoot(ShootPacket packet, Connection connection) {
        Player player = getPlayer(packet.id);
        if (player.getTank() != null) {
            Bullet bullet = new Bullet(packet.bullet, packet.x, packet.y, packet.rot, packet.type);
            handler.handleShot(player.getTank(), bullet);
        }
    }

    @Override
    public void handleSpawn(SpawnPacket packet, Connection connection) {
        Player player = getPlayer(packet.id);

        // If player for some reason already has a spawned Tank, remove it:
        if (player.getTank() != null) {
            handler.removeTank(player.getTank());
        }

        Tank tank = new Tank(packet.tank, packet.x, packet.y, packet.rot);
        player.setTank(tank);
        handler.addTank(tank);

        System.out.println("Spawn Packet received. " + packet.id);
    }

    @Override
    public void handleDestroy(DestroyPacket packet, Connection connection) {
        //Bullet bullet = getBullet(packet.id);
        //if (bullet != null) {
        //    handler.removeBullet(bullet);
        //}

        // TODO: BLOCK, MINE
    }

    @Override
    public void handleVelocity(VelocityPacket packet, Connection connection) {
        Player player = getPlayer(packet.id);
        Tank tank = player.getTank();
        if (tank != null) {
            tank.setVelocity(packet.vel);
            tank.setVelRotation(packet.velRot);
        }
    }

    @Override
    public void handleMouse(MousePacket packet, Connection connection) {
        Player player = getPlayer(packet.id);
        Tank tank = player.getTank();
        if (tank != null) {
            tank.getTurret().setRotation(packet.rot);
        }
    }

    // NETWORK ACTIONS //////////////////////////////////////////////////////////

    @Override
    public void connected(Connection con) {
        System.out.println("Connected to Host: " + con.getAddress() + ":" + con.getUdpPort());
        JoinPacket jp = new JoinPacket(player.getUsername(), player.getColor());
        client.getServerConnection().sendUdp(jp);
    }

    @Override
    public void disconnected(Connection con) {
        System.out.println("Disconnected.");
    }

    @Override
    public void received(Connection con, Object object) {
        packetManager.handlePacket(object, con);
    }
}
