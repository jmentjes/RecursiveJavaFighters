package model.entities;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Kojy on 28.06.2017.
 */
public class Lobby {
    private int lobbyID;
    private List<User> userList;
    private User owner;
    private int gameID;

    public Lobby(int lobbyID, List<User> userList, User owner, int gameID) {
        this.lobbyID = lobbyID;
        if(userList == null){
            userList = new LinkedList<>();
        }
        this.userList = userList;
        this.owner = owner;
        this.gameID = gameID;
    }

    public Lobby(int lobbyID, User owner) {
        //TODO use gameId
        this(lobbyID,null,owner,-1);
    }

    public Lobby(int lobbyID) {
        this(lobbyID,null);
    }

    public int getLobbyID() {
        return lobbyID;
    }

    public void setLobbyID(int lobbyID) {
        this.lobbyID = lobbyID;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public int getGameID() {
        return gameID;
    }

    public void setGameID(int gameID) {
        this.gameID = gameID;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    @Override
    public boolean equals(Object obj){
        if(obj instanceof Lobby){
            Lobby other = (Lobby) obj;
            return other.getLobbyID() == this.getLobbyID();
        }
        return super.equals(obj);
    }

    @Override
    public int hashCode(){
        return lobbyID;
    }
}
