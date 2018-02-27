package ch.network;

import ch.network.packets.*;
import com.jmr.wrapper.common.Connection;

public interface PacketListener {

    void handleJoin(JoinPacket packet, Connection con);
    void handleLeave(LeavePacket packet);
    void handleLobby(LobbyPacket packet);
    void handleMove(CorrectionPacket packet);
    void handleHit(HitPacket packet);
    void handlePickUp(PickUpPacket packet);
    void handlePlace(PlacePacket packet);
    void handleShoot(ShootPacket packet);
    void handleSpawn(SpawnPacket packet);
    void handleDestroy(DestroyPacket packet);
    void handleVelocity(VelocityPacket packet);
    void handleMouse(MousePacket packet);

}