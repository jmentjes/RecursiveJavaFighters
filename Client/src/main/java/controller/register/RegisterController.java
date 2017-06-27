package controller.register;

import controller.vista.Vista;
import controller.vista.VistaNavigator;
import de.github.GSGJ.API.json.JSONCore;
import de.github.GSGJ.API.util.UsermanagementUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.Controller;
import model.Settings;
import model.com.eventmanagement.events.UsermanagementEvent;
import model.eventsystem.EventListener;
import org.json.simple.JSONObject;

/**
 * Created by Kojy on 26.06.2017.
 */
public class RegisterController implements Controller {

    @FXML
    TextField usernameField;

    @FXML
    TextField passwordField1;

    @FXML
    TextField passwordField2;

    @FXML
    TextField emailField;

    @FXML
    Label status;

    public void initialize() {

    }

    public void initializeStyleSheets() {

    }

    private EventListener<UsermanagementEvent> eventEventListener;

    @FXML
    public void register() {
        if(eventEventListener == null){
            eventEventListener = new EventListener<UsermanagementEvent>() {
                @Override
                public void handle(UsermanagementEvent event) {
                    if(event.getJsonObject().get(JSONCore.CORE.SUBJECT.getId()).equals("register")){
                        if(event.getJsonObject().get(JSONCore.CORE.SUCCESS.getId()).equals("true")){
                            status.setText("Register successful");
                        }else {
                            status.setText("Register was not successful");
                        }
                    }
                }
            };
            Settings.getTransceiver().addReceiveListener(eventEventListener);
        }


        if(!passwordField1.getText().equals(passwordField2.getText())){
            status.setText("Passwords are not the same");
            return;
        }else if(!UsermanagementUtil.checkUsername(usernameField.getText())){
            status.setText("Username is not valid");
            return;
        }else if(!UsermanagementUtil.checkEmail(emailField.getText())){
            status.setText("Email is not valid");
            return;
        }else if (!UsermanagementUtil.checkPassword(passwordField1.getText())){
            status.setText("Password is not valid");
            return;
        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.put(JSONCore.CORE.SERVICE.getId(), "de.github.GSGJ.services.usermanagement.UserManagementService");
        jsonObject.put(JSONCore.CORE.SUBJECT.getId(), "register");
        jsonObject.put(JSONCore.CORE.USERNAME.getId(), usernameField.getText());
        jsonObject.put(JSONCore.CORE_USERMANAGEMENT.EMAIL.getId(),emailField.getText());
        jsonObject.put(JSONCore.CORE_USERMANAGEMENT.PASSWORD.getId(), passwordField1.getText());

        Settings.getTransceiver().send(jsonObject);
    }

    @FXML
    public void backToLogin() {
        Settings.getTransceiver().removeReceiveListener(eventEventListener);
        VistaNavigator.loadVista(Vista.LOGIN.getId());
    }
}
