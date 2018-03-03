package ch.framework;

public interface PlayerActionListener {

    void changeVelocity(float vel, Player player);
    void changeRotation(float vel, Player player);
    void changeTurretRotation(float rot, Player player);
    void shoot(Player player);
    void place(Player player);
    void spawn(Player player);

}
