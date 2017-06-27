package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.layout.StackPane;
import model.Controller;

import java.util.Optional;

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

    public void logout(ActionEvent event){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Look, a Confirmation Dialog");
        alert.setContentText("Are you ok with this?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            
        } else {
            // ... user chose CANCEL or closed the dialog
        }

    }
}
