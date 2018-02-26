package ch.framework;

import ch.framework.gameobjects.Bullet;
import ch.framework.gameobjects.Mine;
import ch.framework.gameobjects.tank.Tank;
import ch.framework.gameobjects.tank.Turret;
import ch.network.PacketListener;
import ch.network.PacketManager;
import ch.network.packets.*;
import com.jmr.wrapper.client.Client;
import com.jmr.wrapper.common.Connection;
import com.jmr.wrapper.common.listener.SocketListener;

public class OnlineSession extends Session implements SocketListener, PacketListener {

    private PacketManager packetManager;
    private Client client;

    public OnlineSession(String username, String color) {
        super(username, color);
        packetManager = new PacketManager(this);
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

    @Override
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

    @Override
    public void spawn() {
        SpawnPacket spawnPacket = new SpawnPacket(player.getId(), 100, 100, 0);
        client.getServerConnection().sendUdp(spawnPacket);
    }

    @Override
    public void setVelocity(float velocity) {
        Tank tank = player.getTank();

        if (tank != null) {
            tank.setVelocity(velocity);
            VelocityPacket velocityPacket = new VelocityPacket(player.getId(), tank.getVelocity(), tank.getVelRotation());
            client.getServerConnection().sendUdp(velocityPacket);
        }
    }

    @Override
    public void setVelRotation(float velocity) {
        Tank tank = player.getTank();

        if (tank != null) {
            tank.setVelRotation(velocity);
            VelocityPacket velocityPacket = new VelocityPacket(player.getId(), tank.getVelocity(), tank.getVelRotation());
            client.getServerConnection().sendUdp(velocityPacket);
        }
    }

    @Override
    public void mouseMoved(float rot) {
        if (player.getTank() != null) {
            Tank tank = player.getTank();
            Turret turret = tank.getTurret();
            turret.setRotation(rot);

            MousePacket mousePacket = new MousePacket(player.getId(), turret.getRotation());
            client.getServerConnection().sendUdp(mousePacket);
        }
    }

    @Override
    public void shoot() {
        Tank tank = player.getTank();
        if (tank != null) {
            Bullet bullet = new Bullet(tank.getTurret().getMuzzleX(), tank.getTurret().getMuzzleY(), tank.getTurret().getRotation(), tank.getBulletType());
            ShootPacket shootPacket = new ShootPacket(player.getId(), tank.getTurret().getMuzzleX(), tank.getTurret().getMuzzleY(), tank.getTurret().getRotation(), bullet.getId(), bullet.getType());
            client.getServerConnection().sendUdp(shootPacket);
        }
    }

    @Override
    public void place() {
        Tank tank = player.getTank();
        if (tank != null) {
            Mine mine = new Mine(tank.getX(), tank.getY());
            PlacePacket placePacket = new PlacePacket(player.getId(), mine.getX(), mine.getY(), mine.getId());
            client.getServerConnection().sendUdp(placePacket);
        }
    }

    @Override
    public void pickUp() {

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
        Player player = getPlayer(packet.id);
        if (player.getTank() != null) {
            Mine mine = new Mine(packet.x, packet.y);
            handler.handleMinePlaced(player.getTank(), mine);
        }
    }

    @Override
    public void handleShoot(ShootPacket packet) {
        Player player = getPlayer(packet.id);
        if (player.getTank() != null) {
            Bullet bullet = new Bullet(packet.bullet, packet.x, packet.y, packet.rot, packet.type);
            handler.handleShot(player.getTank(), bullet);
        }
    }

    @Override
    public void handleSpawn(SpawnPacket packet) {
        Player player = getPlayer(packet.id);

        // If player for some reason already has a spawned Tank:
        if (player.getTank() != null) {
            handler.removeTank(player.getTank());
        }

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
        if (tank != null) {
            tank.setVelocity(packet.vel);
            tank.setVelRotation(packet.velRot);
        }
    }

    @Override
    public void handleMouse(MousePacket packet) {
        Tank tank = getTank(packet.id);
        if (tank != null) {
            tank.getTurret().setRotation(packet.rot);
        }
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
        packetManager.handlePacket(object);
    }
}
