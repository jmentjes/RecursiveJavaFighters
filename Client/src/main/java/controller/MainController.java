package controller;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import model.Controller;

/**
 * Created by Kojy on 26.06.2017.
 */
public class MainController implements Controller {
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
}
