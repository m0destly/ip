package gui;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import darwin.Darwin;

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
            fxmlLoader.<MainWindow>getController().setDarwin(darwin);  // inject the Darwin instance
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

