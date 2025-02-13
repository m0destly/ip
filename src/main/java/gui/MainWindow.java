package gui;

import darwin.Darwin;
import darwin.Ui;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Darwin darwin;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/user.png"));
    private Image darwinImage = new Image(this.getClass().getResourceAsStream("/images/darwin.png"));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
        dialogContainer.getChildren().add(DialogBox.getDarwinDialog(Ui.showWelcome(), darwinImage));
    }

    /** Injects the Darwin instance */
    public void setDarwin(Darwin d) {
        darwin = d;
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Duke's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String response = darwin.getResponse(input);
        assert !response.isEmpty() : "Response should not be empty";
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getDarwinDialog(response, darwinImage)
        );
        userInput.clear();
        if (response.equals(Ui.showExit())) {
            System.exit(0);
        }
    }

    @FXML
    public void handleWindowClose() {
        darwin.saveTasks();
    }
}

