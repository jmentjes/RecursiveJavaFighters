package model;

import de.github.GSGJ.API.json.JSONCore;
import org.json.simple.JSONObject;

/**
 * Created by Kojy on 27.06.2017.
 */
public class User {
    private String username;
    private String id;
    private String key;
    private String email;

    public User(String username, String id, String key, String email) {
        this.username = username;
        this.id = id;
        this.key = key;
        this.email = email;
    }

    public User(String username, String id, String email) {
        this.username = username;
        this.id = id;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public static User createUser(JSONObject jsonObject){
        String username = (String) jsonObject.get(JSONCore.CORE.USERNAME.getId());
        String id = (String) jsonObject.get(JSONCore.CORE.USER_ID.getId());
        String key = (String) jsonObject.get(JSONCore.CORE.PRIVATE_KEY.getId());
        String email = (String) jsonObject.get(JSONCore.CORE_USERMANAGEMENT.EMAIL);
        return new User(username,id,key,email);

    }

    public JSONObject addToJSON(JSONObject jsonObject){
        jsonObject.put(JSONCore.CORE.USERNAME.getId(),this.username);
        jsonObject.put(JSONCore.CORE.USER_ID.getId(),this.id);
        jsonObject.put(JSONCore.CORE.PRIVATE_KEY.getId(),this.key);
        jsonObject.put(JSONCore.CORE_USERMANAGEMENT.EMAIL.getId(),this.email);
        return jsonObject;
    }
}
