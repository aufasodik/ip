package wowo.gui;

import java.io.IOException;
import java.util.Collections;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

/**
 * Containing the dialog logic between bot and user
 */
public class DialogBox extends HBox {
    @FXML private Label dialog;
    @FXML private ImageView displayPicture;

    private DialogBox(String text, Image img) {
        assert text != null : "Dialog text must not be null";
        assert img != null : "Display image must not be null";

        try {
            FXMLLoader fxmlLoader =
                    new FXMLLoader(MainWindow.class.getResource("/view/DialogBox.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException("Failed to load DialogBox.fxml", e);
        }

        assert dialog != null : "FXML did not inject Label 'dialog'";
        assert displayPicture != null : "FXML did not inject ImageView 'displayPicture'";

        dialog.setText(text);
        displayPicture.setImage(img);
    }

    /** Flip so the avatar is on the left and text on the right (bot style). */
    private void flip() {
        assert this.getChildren() != null : "DialogBox children must not be null";

        this.setAlignment(Pos.TOP_LEFT);
        ObservableList<Node> tmp = FXCollections.observableArrayList(this.getChildren());
        Collections.reverse(tmp);
        this.getChildren().setAll(tmp);
    }

    public static DialogBox getUserDialog(String text, Image img) {
        assert text != null : "User dialog text must not be null";
        assert img != null : "User dialog image must not be null";

        return new DialogBox(text, img);
    }

    public static DialogBox getBotDialog(String text, Image img) {
        assert text != null : "Bot dialog text must not be null";
        assert img != null : "Bot dialog image must not be null";
        DialogBox db = new DialogBox(text, img);
        db.flip();
        return db;
    }
}
