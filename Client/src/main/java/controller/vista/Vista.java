package controller.vista;

/**
 * Created by Kojy on 26.06.2017.
 */
public enum Vista {
    MAIN("/fxml/main.fxml"), LOGIN("/fxml/login.fxml"), REGISTER("/fxml/register.fxml"), ONLINE("/fxml/online.fxml");

    String id;

    Vista(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }
}
