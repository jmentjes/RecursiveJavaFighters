package model.com.impl;

import model.eventsystem.Event;
import org.json.simple.JSONObject;

/**
 * Created by Kojy on 26.06.2017.
 */
public abstract class AbstractJSONEvent implements Event {
    protected JSONObject jsonObject;

    public AbstractJSONEvent(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    public JSONObject getJsonObject() {
        return this.jsonObject;
    }
}
