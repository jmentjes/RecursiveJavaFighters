package controller.online;

import model.entities.Lobby;

import java.util.List;

/**
 * Created by Kojy on 28.06.2017.
 */
public interface LobbyList {
    void addLobby(Lobby lobby);
    void removeLobby(Lobby lobby);
    void getLobby(Lobby lobby);
    void reset();
    void showLobbies(List<Lobby> lobbyList);
}
