package model;

import model.com.Transceiver;

/**
 * Created by Kojy on 26.06.2017.
 */
public class Settings {
    public static Controller currentController;
    public static Transceiver transceiver;

    public static Controller getCurrentController() {
        return currentController;
    }

    public static void setCurrentController(Controller currentController) {
        Settings.currentController = currentController;
    }

    public static Transceiver getTransceiver() {
        return transceiver;
    }

    public static void setTransceiver(Transceiver transceiver) {
        Settings.transceiver = transceiver;
    }
}
