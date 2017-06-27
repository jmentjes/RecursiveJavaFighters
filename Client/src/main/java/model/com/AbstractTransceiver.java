package model.com;

import model.eventsystem.Event;
import model.eventsystem.EventListener;
import org.json.simple.JSONObject;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by Kojy on 26.06.2017.
 */
public abstract class AbstractTransceiver<T extends Event> implements Transceiver<T> {
    protected Map<Type, List<EventListener<T>>> eventListenerMap = new HashMap<Type, List<EventListener<T>>>();

    public abstract void send(JSONObject object);

    public abstract void receive(JSONObject jsonObject);

    public void addReceiveListener(EventListener<T> eventListener) {
        List<EventListener<T>> listeners = eventListenerMap.get(getType(eventListener));
        if (listeners == null) {
            listeners = new LinkedList<EventListener<T>>();
        }

        listeners.add(eventListener);
        eventListenerMap.put(getType(eventListener), listeners);
    }

    public void removeReceiveListener(EventListener<T> eventListener) {
        List<EventListener<T>> listeners = eventListenerMap.get(getType(eventListener));
        if (listeners != null) {
            listeners.remove(eventListener);
            if (listeners.size() == 0) {
                eventListenerMap.remove(getType(eventListener));
            } else {
                eventListenerMap.put(getType(eventListener), listeners);
            }
        }
    }

    protected void notifyListeners(T event) {
        for (List<EventListener<T>> eventListeners : eventListenerMap.values()) {
            for (EventListener<T> eventListener : eventListeners) {
                if (getType(eventListener).getTypeName().equals(event.getClass().getTypeName())) {
                    eventListener.handle(event);
                }
            }
        }
    }

    protected Type getType(EventListener<?> eventListener) {
        Type mySuperclass = eventListener.getClass().getGenericSuperclass();
        return ((ParameterizedType) mySuperclass).getActualTypeArguments()[0];
    }
}
