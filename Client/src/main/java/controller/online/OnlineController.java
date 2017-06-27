package controller.online;

import controller.vista.Vista;
import controller.vista.VistaNavigator;
import de.github.GSGJ.API.json.JSONCore;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import model.Controller;
import model.Settings;
import model.User;
import model.com.Transceiver;
import model.com.eventmanagement.events.UsermanagementEvent;
import model.eventsystem.EventListener;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

/**
 * Created by Kojy on 26.06.2017.
 */
public class OnlineController implements Controller {
    private static Logger logger = LoggerFactory.getLogger(OnlineController.class);
    private EventListener<UsermanagementEvent> eventEventListener;

    public void initialize() {

    }

    public void initializeStyleSheets() {

    }

    @FXML
    public void logout() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Logout");
        alert.setHeaderText("Logout confirmation");
        alert.setContentText("Do you really want to logout?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            Transceiver transceiver = Settings.getTransceiver();

            if(eventEventListener == null){
                eventEventListener = new EventListener<UsermanagementEvent>() {
                    @Override
                    public void handle(UsermanagementEvent event) {
                        logger.info(event.getJsonObject().toJSONString());
                        if (event.getJsonObject().get(JSONCore.CORE.SUCCESS.getId()).equals("true") &&
                                event.getJsonObject().get(JSONCore.CORE.SUBJECT.getId()).equals("logout")) {
                            Settings.setCurrentUser(null);
                            VistaNavigator.loadVista(Vista.LOGIN.getId());
                        }else {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Error");
                            alert.setHeaderText("Can't logout");
                            alert.setContentText((String) event.getJsonObject().get(JSONCore.CORE.ERROR_MESSAGE.getId()));
                            alert.showAndWait();
                            Settings.setCurrentUser(null);
                            VistaNavigator.loadVista(Vista.LOGIN.getId());
                        }
                    }
                };
                transceiver.addReceiveListener(eventEventListener);
            }
            JSONObject jsonObject = new JSONObject();
            jsonObject.put(JSONCore.CORE.SERVICE.getId(), "de.github.GSGJ.services.usermanagement.UserManagementService");
            jsonObject.put(JSONCore.CORE.SUBJECT.getId(), "logout");

            jsonObject = Settings.getCurrentUser().addToJSON(jsonObject);

            transceiver.send(jsonObject);
        } else {
            return;
        }
    }
}
