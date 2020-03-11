package gspd.ispd.fxgui;

import gspd.ispd.GUI;
import gspd.ispd.fxgui.util.*;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;

import java.util.Optional;

/**
 * Implements some util methods to work with JavaFX in iSPD.
 */
public class GUIUtil {
    static private DragUtil dragger = new DragUtil();
    static private DrawerUtil drawer = new DrawerUtil();
    static private LightningUtil lighter = new LightningUtil();
    static private FollowUtil follower = new FollowUtil();
    static private PanUtil panner = new PanUtil();

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

    static public void follow(Node node, Pane pane) {
        follower.makeFollow(node, pane);
    }

    static public void unfollow(Pane pane) {
        follower.lockFollow(pane);
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

        alert = new Alert(Alert.AlertType.WARNING);
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

    @Deprecated
    static public void setMainWindow(Stage window, GUI main) {
        throw new UnsupportedOperationException("[DEPRECATED]");
    }

    static public void closeMainWindow(Stage window, GUI main) {
        AnswerType answer;
        if (/* main.getModel().isNotSaved() */ false) {
            answer = checkClosingWithoutSaving();
            if (answer == AnswerType.SAVE_AND_EXIT) {
                // TODO: check out this file
                // main.getModel().saveToFile("test_file.imsx");
                window.close();
            } else if (answer == AnswerType.EXIT_WITHOUT_SAVE) {
                window.close();
            }
        } else {
            window.close();
        }
    }

    static public void makeHoverable(Node node) {
        lighter.makeHoverable(node);
    }

    static public void makePannable(ScrollPane pane) {
        panner.makePannable(pane);
    }

    static public Shape copyShape(Shape shape) {
        return Shape.union(shape, shape);
    }
}
