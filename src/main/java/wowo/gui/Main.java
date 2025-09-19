package wowo.gui;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import wowo.Wowo;

/**
 * Start the application after launch
 */
public class Main extends Application {
    private final Wowo bot = new Wowo();

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));

            AnchorPane root = fxmlLoader.load();
            assert root != null : "MainWindow.fxml did not load a valid root node";

            // inject backend into controller *before* showing
            MainWindow controller = fxmlLoader.getController();
            assert controller != null : "Controller was not injected from FXML";

            controller.init(bot);

            assert stage != null : "Stage must not be null";
            stage.setTitle("Wowo");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException("Failed to load MainWindow.fxml", e);
        }
    }
}
