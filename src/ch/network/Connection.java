package ch.network;

public class Connection {

    private Socket socket;

    public Connection() {

    }

    public interface Listener {

        void connected();
        void disconnected();
        void received();
        void discovered();

    }

}