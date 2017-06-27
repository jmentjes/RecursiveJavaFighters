package controller.online;

import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;
import model.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Kojy on 26.06.2017.
 */
public class OnlineController implements Controller {
    private static Logger logger = LoggerFactory.getLogger(OnlineController.class);

    @FXML
    StackPane userList;

    @FXML
    StackPane lobbyList;

    @FXML
    StackPane lobbyPreview;

    @FXML
    StackPane chat;

    public void initialize() {

    }

    public void initializeStyleSheets() {

    }
}
