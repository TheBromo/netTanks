package ch.network;

import ch.network.packets.*;

public class PacketHandler {

    private PacketListener packetListener;
    private int packetsReceived;

    public PacketHandler(PacketListener packetListener) {
        this.packetListener = packetListener;
    }

    public void handlePacket(Object object) {

        if (object instanceof JoinPacket) {
            packetListener.handleJoin((JoinPacket) object);
        }

        if (object instanceof LeavePacket) {
            packetListener.handleLeave((LeavePacket) object);
        }

        if (object instanceof LobbyPacket) {
            packetListener.handleLobby((LobbyPacket) object);
        }

        if (object instanceof CorrectionPacket) {
            packetListener.handleMove((CorrectionPacket) object);
        }

        if (object instanceof HitPacket) {
            packetListener.handleHit((HitPacket) object);
        }

        if (object instanceof PickUpPacket) {
            packetListener.handlePickUp((PickUpPacket) object);
        }

        if (object instanceof PlacePacket) {
            packetListener.handlePlace((PlacePacket) object);
        }

        if (object instanceof ShootPacket) {
            packetListener.handleShoot((ShootPacket) object);
        }

        if (object instanceof SpawnPacket) {
            packetListener.handleSpawn((SpawnPacket) object);
        }

        if (object instanceof DestroyPacket) {
            packetListener.handleDestroy((DestroyPacket) object);
        }

        if (object instanceof VelocityPacket) {
            packetListener.handleVelocity((VelocityPacket) object);
        }

        if (object instanceof MousePacket) {
            packetListener.handleMouse((MousePacket) object);
        }

        packetsReceived++;

    }

    public int getPacketsReceived() {
        return packetsReceived;
    }

    public void setPacketListener(PacketListener packetListener) {
        this.packetListener = packetListener;
    }
}
