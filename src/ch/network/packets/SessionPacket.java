package ch.network.packets;

import java.util.UUID;

public class SessionPacket extends Packet {

    public Player[] players;

    public SessionPacket() {
        players = new Player[0];
    }

    public void addPlayer(ch.framework.Player player, ch.framework.gameobjects.Bullet[] bullets, ch.framework.gameobjects.Mine[] mines) {
        Player[] temp = new Player[players.length + 1];

        for (int i = 0; i < players.length; i++) {
            temp[i] = players[i];
        }

        Tank tank = new Tank(player.getTank().getX(), player.getTank().getY(), player.getTank().getRotation(), player.getTank().getTurret().getRotation());

        Bullet[] b = new Bullet[bullets.length];
        for (int i = 0; i < b.length; i++) {
            ch.framework.gameobjects.Bullet bullet = bullets[i];
            b[i] = new Bullet(bullet.getX(), bullet.getY(), bullet.getRotation(), bullet.getId(), bullet.getType());
        }

        Mine[] m = new Mine[mines.length];
        for (int i = 0; i < m.length; i++) {
            ch.framework.gameobjects.Mine mine = mines[i];
            m[i] = new Mine(mine.getX(), mine.getY(), mine.getId(), mine.getCounter());
        }

        temp[temp.length - 1] = new Player(player.getId(), player.getUsername(), player.getColor(), tank, b, m);
    }

    private class Player {

        public UUID id;
        public String username;
        public String color;

        public Tank tank;
        public SessionPacket.Bullet[] bullets;
        public SessionPacket.Mine[] mines;

        public Player(UUID id, String username, String color, Tank tank, SessionPacket.Bullet[] bullets, SessionPacket.Mine[] mines) {
            this.id = id;
            this.username = username;
            this.color = color;

            this.tank = tank;
            this.bullets = bullets;
            this.mines = mines;
        }
    }

    private class Tank {

        public float x, y, rot, trot;

        public Tank(float x, float y, float rot, float trot) {
            this.x = x;
            this.y = y;
            this.rot = rot;
            this.trot = trot;
        }
    }

    private class Bullet {

        public float x, y, rot;
        public UUID bullet;
        public ch.framework.gameobjects.Bullet.Type type;

        public Bullet(float x, float y, float rot, UUID bullet, ch.framework.gameobjects.Bullet.Type type) {
            this.x = x;
            this.y = y;
            this.rot = rot;
            this.bullet = bullet;
            this.type = type;
        }
    }

    private class Mine {

        public float x, y;
        public UUID mine;
        public int counter;

        public Mine(float x, float y, UUID mine, int counter) {
            this.x = x;
            this.y = y;
            this.mine = mine;
            this.counter = counter;
        }
    }
}
