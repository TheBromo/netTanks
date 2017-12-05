package ch.network;

import ch.match.Player;

import java.util.ArrayList;

public class Connection {

    private ArrayList<Player> players;

    public Connection() {
        players = new ArrayList<>();
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }
}
