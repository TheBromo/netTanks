package ch.network;

import ch.network.packets.*;
import com.jmr.wrapper.common.Connection;

public interface PacketListener {

    void handleWelcome(WelcomePacket packet, Connection con);
    void handleJoin(JoinPacket packet, Connection con);
    void handleLeave(LeavePacket packet, Connection con);
    void handleLobby(LobbyPacket packet, Connection con);
    void handleMove(CorrectionPacket packet, Connection con);
    void handleHit(HitPacket packet, Connection con);
    void handlePickUp(PickUpPacket packet, Connection con);
    void handlePlace(PlacePacket packet, Connection con);
    void handleShoot(ShootPacket packet, Connection con);
    void handleSpawn(SpawnPacket packet, Connection con);
    void handleDestroy(DestroyPacket packet, Connection con);
    void handleVelocity(VelocityPacket packet, Connection con);
    void handleMouse(MousePacket packet, Connection con);

}