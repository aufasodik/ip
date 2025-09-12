package wowo.gui;

import java.util.Objects;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import wowo.Wowo;

/**
 * Contains the main user interface window
 */
public class MainWindow {
    @FXML private ScrollPane scrollPane;
    @FXML private VBox dialogContainer;
    @FXML private TextField userInput;
    @FXML private Button sendButton;

    private final Image botImage = loadImage("/images/DaWowo.png");
    private final Image userImage = loadImage("/images/DaUser.png");

    private Wowo bot;

    private static Image loadImage(String path) {
        var in = Objects.requireNonNull(
                MainWindow.class.getResourceAsStream(path),
                "Missing resource: " + path);
        return new Image(in);
    }

    /** Called by FXMLLoader after @FXML fields are injected. */
    @FXML
    public void initialize() {
        // auto-scroll when new dialogs are added
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /** Inject the backend instance. Called from Main after loading FXML. */
    public void init(Wowo bot) {
        this.bot = bot;
        // optional greeting
        dialogContainer.getChildren().add(
                DialogBox.getBotDialog("Hello! I'm Wowo.\nHow can I help?", botImage)
        );
    }

    /** Send button and Enter key handler. */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        if (input == null || input.isBlank()) {
            return;
        }

        String response = bot.getResponse(input);

        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getBotDialog(response, botImage)
        );

        userInput.clear();
    }
}
