package gspd.ispd.fxgui.util;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.Pane;

import java.util.Optional;

/**
 * Implements some util methods to work with JavaFX in iSPD.
 */
public class FXUtil {
    static private DragUtil dragger = new DragUtil();
    static private DrawerUtil drawer = new DrawerUtil();

    public enum AnswerType {
        CANCEL,
        SAVE_AND_EXIT,
        EXIT_WITHOUT_SAVE
    }
    /**
     * Adds the needed behaviour to make a node draggable
     *
     * @param node the node to become draggable
     */
    static public void makeDraggable(Node node) {
        dragger.makeDraggable(node);
    }

    static public void putNodeInContext(Node node, Pane context) {
        context.getChildren().add(node);
    }

    static public AnswerType checkClosingWithoutSaving() {
        // the alert that will be display
        Alert alert;
        // the answer given by the user that will be returned
        AnswerType answer;
        // the specific button that the user clicked (if any)
        ButtonType clicked;
        // the possible result given by the alert (one of the buttons, if any)
        Optional<ButtonType> result;

        alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit without saving");
        alert.setHeaderText("Are you sure exit?");
        alert.setContentText("Hey, it looks like you didn't save your work. Are you really want to exit?");
        ButtonType saveAndExit = new ButtonType("Save and Exit");
        ButtonType exitWithoutSave = new ButtonType("Exit");
        ButtonType cancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(saveAndExit, exitWithoutSave, cancel);
        result = alert.showAndWait();
        clicked = result.get();
        if (clicked == saveAndExit) {
            answer = AnswerType.SAVE_AND_EXIT;
        } else if (clicked == exitWithoutSave) {
            answer = AnswerType.EXIT_WITHOUT_SAVE;
        } else {
            answer = AnswerType.CANCEL;
        }
        return answer;
    }
}
