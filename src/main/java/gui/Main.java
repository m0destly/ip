package gui;

import java.io.IOException;

import darwin.Darwin;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * A GUI for Darwin using FXML.
 */
public class Main extends Application {

    private Darwin darwin = new Darwin("data/darwin.tmp");

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            MainWindow controller = fxmlLoader.getController();
            // inject the Darwin instance
            controller.setDarwin(darwin);
            stage.setOnCloseRequest(event -> {
                controller.handleWindowClose();
            });
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

