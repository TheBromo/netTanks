package ch.network;

import ch.network.packets.*;

public interface PacketListener {

    void handleJoin(JoinPacket packet);
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