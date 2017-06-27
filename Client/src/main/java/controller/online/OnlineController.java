package controller.online;

import controller.vista.Vista;
import controller.vista.VistaNavigator;
import javafx.fxml.FXML;
import model.Controller;

/**
 * Created by Kojy on 26.06.2017.
 */
public class OnlineController implements Controller {
    public void initialize() {

    }

    public void initializeStyleSheets() {

    }

    @FXML
    public void logout() {
        VistaNavigator.loadVista(Vista.LOGIN.getId());
    }

}
