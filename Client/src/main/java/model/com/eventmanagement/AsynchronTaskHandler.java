package model.com.eventmanagement;

import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import model.com.Transceiver;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by timo on 26.05.16.
 * <p>
 * test to use several threads in JavaFX
 */
public class AsynchronTaskHandler implements Runnable {
    private static Logger logger = LoggerFactory.getLogger(AsynchronTaskHandler.class);
    private static List<JSONObject> queue = new LinkedList<>();
    private Transceiver transceiver;

    public AsynchronTaskHandler(Transceiver transceiver){
        this.transceiver = transceiver;
    }

    /**
     * @param message
     */
    public void processData(JSONObject message) {
        synchronized (queue) {
            queue.add(message);
            queue.notify();
        }
    }

    @Override
    public void run() {

        while (true) {
            Task<JSONObject> task = new Task<JSONObject>() {
                @Override
                public JSONObject call() {
                    return waitForMessage();
                }
            };

            task.setOnSucceeded(event -> {
                if (event.getEventType() == WorkerStateEvent.WORKER_STATE_SUCCEEDED) {
                    // task was successful
                    // get next message
                    JSONObject msg = ((Task<JSONObject>) event.getTarget()).getValue();
                    transceiver.receive(msg);
                }
            });
            task.run();
        }
    }

    /**
     * @return
     */
    private JSONObject waitForMessage() {
        synchronized (queue) {
            while (queue.isEmpty()) {
                try {
                    queue.wait();
                } catch (InterruptedException e) {
                    logger.error(e.getMessage(), e);
                }
            }
            return queue.remove(0);
        }
    }
}
