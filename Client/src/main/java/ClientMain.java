/**
 * Created by Kojy on 26.06.2017.
 */

import controller.MainController;
import controller.vista.Vista;
import controller.vista.VistaNavigator;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.Settings;
import model.com.Transceiver;
import model.com.impl.TransceiverImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetAddress;

public class ClientMain extends Application {
    private static Logger logger = LoggerFactory.getLogger(ClientMain.class);

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Transceiver transceiver = new TransceiverImpl(true, InetAddress.getByName("localhost"), 8081);
        Settings.setTransceiver(transceiver);
        stage.setTitle("Vista Viewer");

        stage.setScene(
                createScene(
                        loadMainPane()
                )
        );
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                System.exit(0);
            }
        });
        stage.show();
    }

    /**
     * Loads the main fxml layout.
     * Sets up the vista switching VistaNavigator.
     * Loads the first vista into the fxml layout.
     *
     * @return the loaded pane.
     * @throws IOException if the pane could not be loaded.
     */
    private Pane loadMainPane() throws IOException {
        FXMLLoader loader = new FXMLLoader();

        Pane mainPane = (Pane) loader.load(
                getClass().getResourceAsStream(
                        Vista.MAIN.getId()
                )
        );

        MainController mainController = loader.getController();

        VistaNavigator.setMainController(mainController);
        VistaNavigator.loadVista(Vista.LOGIN.getId());

        return mainPane;
    }

    /**
     * Creates the main application scene.
     *
     * @param mainPane the main application layout.
     * @return the created scene.
     */
    private Scene createScene(Pane mainPane) {
        Scene scene = new Scene(
                mainPane
        );

        scene.getStylesheets().setAll(
                getClass().getResource("/css/vista.css").toExternalForm()
        );

        return scene;
    }
}
