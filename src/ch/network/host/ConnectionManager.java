package ch.network.host;

import com.jmr.wrapper.common.Connection;

import java.util.ArrayList;

public class ConnectionManager {

	private static ConnectionManager instance = new ConnectionManager();
	
	private ArrayList<Connection> connections = new ArrayList<Connection>();
	
	private ConnectionManager() {
	}
	
	public void addConnection(Connection con) {
		connections.add(con);
	}
	
	public void removeConnection(Connection con) {
		connections.remove(con);
	}
	
	public ArrayList<Connection> getConnections() {
		return connections;
	}
	
	public static ConnectionManager getInstance() {
		return instance;
	}
	
}
