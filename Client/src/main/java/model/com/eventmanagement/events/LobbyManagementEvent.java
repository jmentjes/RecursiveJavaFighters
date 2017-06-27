package model.com.eventmanagement.events;

import model.com.impl.AbstractJSONEvent;
import org.json.simple.JSONObject;

/**
 * Created by Kojy on 27.06.2017.
 */
public class LobbyManagementEvent extends AbstractJSONEvent {
    public LobbyManagementEvent(JSONObject jsonObject) {
        super(jsonObject);
    }
}
