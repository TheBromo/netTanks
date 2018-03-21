package ch.network;

public interface GhostListener {
    void handleWelcome(Connection connection);
    void handleJoin();
    void handleLeave();
    void handleLobby();
    void handleMove();
    void handleHit();
    void handlePickUp();
    void handlePlace();
    void handleShoot();
    void handleSpawn();
    void handleDestroy();
    void handleVelocity();
    void handleMouse();
}
