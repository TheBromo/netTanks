package ch.framework;

import java.util.Timer;
import java.util.TimerTask;

public class Loop extends TimerTask {

    private Timer timer;
    private boolean running;
    private EventHandler event;

    public Loop(EventHandler event) {
        timer = new Timer();
        timer.schedule(this, 0, 1000/60);
        this.event = event;
        running = true;
    }

    @Override
    public void run() {
        if (event != null) {
            event.handle();
        }

        if (!running) {
            timer.cancel();
        }
    }

    public void stop() {
        if (running) {
            timer.cancel();
        }
    }

    public boolean isRunning() {
        return running;
    }

    public interface EventHandler {
        void handle();
    }
}
