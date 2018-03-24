package ch.network;

import ch.framework.ID;

public interface PacketListener {

    void handleJoin(Connection connection, String username, String color);
    //void handleLeave(Connection connection);
    //void handleLobby();
    //void handleCorrection(Connection connection);
    //void handleHit(Connection connection);
    //void handlePickUp(PickUpPacket packet, Connection con);
    //void handlePlace(PlacePacket packet, Connection con);
    //void handleShoot(Connection connection, float x, float y, float rot, ID id);
    //void handleSpawn(Connection connection, float x, float y, ID id);
    //void handleDestroy(DestroyPacket packet, Connection con);
    //void handleVelocity(VelocityPacket packet, Connection con);
    //void handleMouse(MousePacket packet, Connection con);

}