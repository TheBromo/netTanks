package ch.framework;

public interface PlayerActionListener {

    void handleVelocityChanged(float vel, Player player);
    void handleRotationChanged(float vel, Player player);
    void handleTurretRotationChanged(float rot, Player player);
    void handleShot(Player player);
    void handlePlace(Player player);
    void handleSpawn(Player player);

}
