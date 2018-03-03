package ch.network;

import ch.network.packets.*;
import com.jmr.wrapper.common.Connection;

public class PacketManager {

    private PacketListener packetListener;
    private int packetsReceived;

    public PacketManager(PacketListener packetListener) {
        this.packetListener = packetListener;
    }

    public void handlePacket(Object object, Connection con) {

        if (object instanceof WelcomePacket) {
            packetListener.handleWelcome((WelcomePacket) object, con);
        }

        else if (object instanceof JoinPacket) {
            packetListener.handleJoin((JoinPacket) object, con);
        }

        else if (object instanceof LeavePacket) {
            packetListener.handleLeave((LeavePacket) object, con);
        }

        else if (object instanceof LobbyPacket) {
            packetListener.handleLobby((LobbyPacket) object, con);
        }

        else if (object instanceof CorrectionPacket) {
            packetListener.handleMove((CorrectionPacket) object, con);
        }

        else if (object instanceof HitPacket) {
            packetListener.handleHit((HitPacket) object, con);
        }

        else if (object instanceof PickUpPacket) {
            packetListener.handlePickUp((PickUpPacket) object, con);
        }

        else if (object instanceof PlacePacket) {
            packetListener.handlePlace((PlacePacket) object, con);
        }

        else if (object instanceof ShootPacket) {
            packetListener.handleShoot((ShootPacket) object, con);
        }

        else if (object instanceof SpawnPacket) {
            packetListener.handleSpawn((SpawnPacket) object, con);
        }

        else if (object instanceof DestroyPacket) {
            packetListener.handleDestroy((DestroyPacket) object, con);
        }

        else if (object instanceof VelocityPacket) {
            packetListener.handleVelocity((VelocityPacket) object, con);
        }

        else if (object instanceof MousePacket) {
            packetListener.handleMouse((MousePacket) object, con);
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
