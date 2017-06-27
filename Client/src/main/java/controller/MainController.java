package controller;

import controller.vista.Vista;
import controller.vista.VistaNavigator;
import de.github.GSGJ.API.json.JSONCore;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.layout.StackPane;
import model.Controller;
import model.Settings;
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
public class MainController implements Controller {
    private static Logger logger = LoggerFactory.getLogger(MainController.class);
    private EventListener<UsermanagementEvent> eventEventListener;

    /**
     * Holder of a switchable vista.
     */
    @FXML
    private StackPane vistaHolder;

    public void initialize() {

    }

    public void initializeStyleSheets() {

    }

    /**
     * Replaces the vista displayed in the vista holder with a new vista.
     *
     * @param node the vista node to be swapped in.
     */
    public void setVista(Node node) {
        vistaHolder.getChildren().setAll(node);
    }

    @FXML
    public void logout(ActionEvent event){
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
                        }else if(event.getJsonObject().get(JSONCore.CORE.SUCCESS.getId()).equals("false") &&
                                event.getJsonObject().get(JSONCore.CORE.SUBJECT.getId()).equals("logout")){
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Error");
                            alert.setHeaderText("Can't logout");
                            alert.setContentText((String) event.getJsonObject().get(JSONCore.CORE.ERROR_MESSAGE.getId()));
                            alert.showAndWait();
                            Settings.setCurrentUser(null);
                            VistaNavigator.loadVista(Vista.LOGIN.getId());
                        }

                        transceiver.removeReceiveListener(eventEventListener);
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
