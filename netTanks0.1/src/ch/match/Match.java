package ch.match;

import ch.tanks.Tank;

import java.util.ArrayList;
import java.util.HashMap;

public class Match {

    private GameMode gameMode;

    private ArrayList<Player> players;
    private HashMap<Player, Tank> tanks;

    public Match(GameMode gameMode) {
        this.gameMode = gameMode;
        players = new ArrayList<>();
        tanks = new HashMap<>();
    }
}
