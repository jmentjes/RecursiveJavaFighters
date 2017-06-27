package controller.login;

import controller.vista.Vista;
import controller.vista.VistaNavigator;
import de.github.GSGJ.API.json.JSONCore;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import model.Controller;
import model.Settings;
import model.entities.User;
import model.com.Transceiver;
import model.com.eventmanagement.events.UsermanagementEvent;
import model.eventsystem.EventListener;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Kojy on 26.06.2017.
 */
public class LoginController implements Controller {
    private static Logger logger = LoggerFactory.getLogger(LoginController.class);

    @FXML
    TextField usernameField;
    @FXML
    PasswordField passwordField;
    @FXML
    Label status;

    private EventListener<UsermanagementEvent> eventEventListener;

    public void initialize() {

    }

    public void initializeStyleSheets() {

    }

    /**
     * Event handler fired when the user requests a new vista.
     *
     * @param event the event that triggered the handler.
     */
    @FXML
    public void register(ActionEvent event) {
        switchTo(Vista.REGISTER);
    }

    @FXML
    public void goOnline(ActionEvent event) {
        Transceiver transceiver = Settings.getTransceiver();

        if(eventEventListener == null){
            eventEventListener = new EventListener<UsermanagementEvent>() {
                @Override
                public void handle(UsermanagementEvent event) {
                    logger.info(event.getJsonObject().toJSONString());
                    if (event.getJsonObject().get(JSONCore.CORE.SUCCESS.getId()).equals("true") &&
                            event.getJsonObject().get(JSONCore.CORE.SUBJECT.getId()).equals("login")) {
                        Settings.setCurrentUser(User.createUser(event.getJsonObject()));
                        switchTo(Vista.ONLINE);
                    }else {
                        status.setText((String) event.getJsonObject().get(JSONCore.CORE.ERROR_MESSAGE.getId()));
                    }
                    transceiver.removeReceiveListener(eventEventListener);
                }
            };
            transceiver.addReceiveListener(eventEventListener);
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(JSONCore.CORE.SERVICE.getId(), "de.github.GSGJ.services.usermanagement.UserManagementService");
        jsonObject.put(JSONCore.CORE.SUBJECT.getId(), "login");
        jsonObject.put(JSONCore.CORE.USERNAME.getId(), usernameField.getText());
        jsonObject.put(JSONCore.CORE_USERMANAGEMENT.PASSWORD.getId(), passwordField.getText());
        transceiver.send(jsonObject);
    }

    @FXML
    public void switchTo(Vista vista) {
        VistaNavigator.loadVista(vista.getId());
    }
}
