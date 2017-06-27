package model.com;

import model.eventsystem.Event;
import model.eventsystem.EventListener;
import org.json.simple.JSONObject;

/**
 * Created by Kojy on 26.06.2017.
 */
public interface Transceiver<T extends Event> {
    void send(JSONObject object);

    void receive(JSONObject jsonObject);

    void addReceiveListener(EventListener<T> eventListener);

    void removeReceiveListener(EventListener<T> eventListener);

    void start();

    void stop();

}
