package ch.network;

import ch.network.packets.*;

public class PacketHandler {

    public interface PackageListener {

        void handleJoin(JoinPacket packet);
        void handleLeave(LeavePacket packet);
        void handleLobby(LobbyPacket packet);
        void handleMove(MovePacket packet);
        void handleHit(HitPacket packet);
        void handlePickUp(PickUpPacket packet);
        void handlePlace(PlacePacket packet);
        void handleShoot(ShootPacket packet);
        void handleSpawn(SpawnPacket packet);
    }

    private PackageListener packageListener;
    private int packetsReceived;

    public PacketHandler(PackageListener packageListener) {
        this.packageListener = packageListener;
    }

    public void handlePacket(Object object) {

        if (object instanceof JoinPacket) {
            packageListener.handleJoin((JoinPacket) object);
        }

        if (object instanceof LeavePacket) {
            packageListener.handleLeave((LeavePacket) object);
        }

        if (object instanceof LobbyPacket) {
            packageListener.handleLobby((LobbyPacket) object);
        }

        if (object instanceof MovePacket) {
            packageListener.handleMove((MovePacket) object);
        }

        if (object instanceof HitPacket) {
            packageListener.handleHit((HitPacket) object);
        }

        if (object instanceof PickUpPacket) {
            packageListener.handlePickUp((PickUpPacket) object);
        }

        if (object instanceof PlacePacket) {
            packageListener.handlePlace((PlacePacket) object);
        }

        if (object instanceof ShootPacket) {
            packageListener.handleShoot((ShootPacket) object);
        }

        if (object instanceof SpawnPacket) {
            packageListener.handleSpawn((SpawnPacket) object);
            System.out.println("Packet received");
        }

        packetsReceived++;

    }

    public int getPacketsReceived() {
        return packetsReceived;
    }

    public void setPackageListener(PackageListener packageListener) {
        this.packageListener = packageListener;
    }
}
