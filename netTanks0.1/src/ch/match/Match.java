package ch.match;

import ch.framework.gameobjects.tank.Tank;

import java.util.ArrayList;
import java.util.HashMap;

public class Match {

    private GameMode gameMode;

    private ArrayList<Player> players;
    private HashMap<Player, Tank> tanks;

    public Match(GameMode gameMode, Player... players) {
        this.gameMode = gameMode;
        this.players = new ArrayList<>();
        tanks = new HashMap<>();

        for (Player player : players) {
            addPlayer(player);
        }
    }

    public void addPlayer(Player player) {
        if (!players.contains(player)) {
            players.add(player);
        }
    }

    public void addTank(Player player) {

    }

    public ArrayList<Player> getPlayers() {
        return players;
    }
}
