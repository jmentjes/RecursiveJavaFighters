package model.com.eventmanagement.events;

import model.com.impl.AbstractJSONEvent;
import org.json.simple.JSONObject;

/**
 * Created by Kojy on 27.06.2017.
 */
public class GameManagementEvent extends AbstractJSONEvent {
    public GameManagementEvent(JSONObject jsonObject) {
        super(jsonObject);
    }
}
