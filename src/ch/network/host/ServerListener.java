package ch.network.host;

import ch.network.packet.Hello;
import ch.network.packet.Move;
import com.jmr.wrapper.common.Connection;
import com.jmr.wrapper.common.listener.SocketListener;

public class ServerListener implements SocketListener {

	@Override
	public void connected(Connection con) {
		System.out.println("Session connected.");
		ConnectionManager.getInstance().addConnection(con);
	}

	@Override
	public void disconnected(Connection con) {
		System.out.println("Session disconnected.");
		ConnectionManager.getInstance().removeConnection(con);
	}

	@Override
	public void received(Connection con, Object object) {

		if (object instanceof Move) {
			Move move = (Move) object;
			System.out.println("x: " + move.x + " y: " + move.y + " rot: " + move.rot);
			for (Connection c : ConnectionManager.getInstance().getConnections()) {
				c.sendUdp(move);
			}
		}

		if (object instanceof Hello) {
			Hello hello = (Hello) object;
			System.out.println("User: " + hello.username + " Color: " + hello.color + " ID: " + hello.id);
			for (Connection c : ConnectionManager.getInstance().getConnections()) {
				c.sendUdp(hello);
			}
		}
	}

}
