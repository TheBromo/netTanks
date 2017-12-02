package ch.framework;

import ch.framework.gameobjects.tank.Tank;
import ch.framework.map.Map;
import ch.match.GameMode;
import ch.match.Player;

import java.util.ArrayList;
import java.util.HashMap;

public class Match {

    private GameMode gameMode;

    private ArrayList<Player> players;
    private Handler handler;

    public Match(GameMode gameMode, Player... players) {
        this.gameMode = gameMode;
        this.players = new ArrayList<>();

        for (Player player : players) {
            addPlayer(player);
        }
    }

    public void start() {
        handler = new Handler();
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
