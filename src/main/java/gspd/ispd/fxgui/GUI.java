package gspd.ispd.fxgui;

import gspd.ispd.ISPD;
import gspd.ispd.MainApp;
import gspd.ispd.fxgui.util.DragUtil;
import gspd.ispd.fxgui.util.DrawerUtil;
import gspd.ispd.model.VM;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

/**
 * Implements some util methods to work with JavaFX in iSPD.
 */
public class GUI {
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

    static public void setMainWindow(Stage window, MainApp main) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(GUI.class.getResource("MainPage.fxml"));
            loader.setResources(ISPD.getStrings());
            Scene scene = new Scene(loader.load());
            window.setScene(scene);
            MainPage controller = loader.getController();
            controller.setMain(main);
            controller.init();
            window.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static public void setSettingsWindow(Stage owner, Stage window, MainApp main) {
        try {
            // Loads FXML file (GUI description)
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(GUI.class.getResource("SettingsPage.fxml"));
            loader.setResources(ISPD.getStrings());
            // starts the content of the main window
            Scene scene = new Scene(loader.load());
            // creates the window itself
            window.setTitle(ISPD.getStrings().getString("settings.title"));
            // define the settings window as a child of the stage that called it
            window.initOwner(owner);
            window.initModality(Modality.WINDOW_MODAL);
            window.setScene(scene);
            // get the controller of the settings page
            SettingsPage controller = loader.getController();
            // initialize the settings page
            controller.init();
            controller.setWindow(window);
            // finally show the settings page and wait the user
            window.showAndWait();
            // if the OK button was clicked, then apply new settings
            if (controller.isOkClicked()) {
                // apply new configurations
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static public void closeMainWindow(Stage window, MainApp main) {
        AnswerType answer;
        if (main.getModel().isNotSaved()) {
            answer = checkClosingWithoutSaving();
            if (answer == AnswerType.SAVE_AND_EXIT) {
                // TODO: check out this file
                main.getModel().saveToFile("test_file.imsx");
                window.close();
            } else if (answer == AnswerType.EXIT_WITHOUT_SAVE) {
                window.close();
            }
        } else {
            window.close();
        }
    }

    static public VM openVMDialog(Stage owner, Stage window) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(GUI.class.getResource("VMDialog.fxml"));
            loader.setResources(ISPD.getStrings());
            Scene scene = new Scene(loader.load());
            window.setTitle("VM Edit");
            window.initOwner(owner);
            window.initModality(Modality.WINDOW_MODAL);
            window.setScene(scene);
            VMDialog controller = loader.getController();
            controller.init();
            window.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
