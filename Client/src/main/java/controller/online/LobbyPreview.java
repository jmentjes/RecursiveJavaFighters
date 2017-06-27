package controller.online;

import model.entities.Lobby;

/**
 * Created by Kojy on 28.06.2017.
 */
public interface LobbyPreview {
    void showLobby(Lobby lobby);
    void reset();
}
