package gspd.ispd.fxgui;

import gspd.ispd.ISPD;
import gspd.ispd.MainApp;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Pair;
import javafx.util.StringConverter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

public class SettingsWindow {
    @FXML
    private ChoiceBox<Pair<String, Locale>> localeChoiceBox;
    @FXML
    private Spinner<Integer> numberSimulationsSpinner;
    @FXML
    private ToggleGroup iconTypeToggleGroup;
    @FXML
    private ToggleGroup modelTypeToggleGroup;
    @FXML
    private CheckBox computingPowerChartCheckBox;
    @FXML
    private CheckBox processingChartCheckBox;
    @FXML
    private CheckBox communicationChartCheckBox;
    @FXML
    private CheckBox resourcesChartCheckBox;

    private MainApp mainApp;
    private Stage window;
    private Properties settings;
    private boolean okClicked;

    public static void create(Stage owner, Stage window, MainApp main) {
        try {
            // Loads FXML file (GUI description)
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(GUI.class.getResource("SettingsWindow.fxml"));
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
            SettingsWindow controller = loader.getController();
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

    public void init() {
        settings = new Properties();
        initLanguages();
        okClicked = false;
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    public void setWindow(Stage window) {
        this.window = window;
    }

    public boolean isOkClicked() {
        return okClicked;
    }

    public Properties getSettings() {
        return settings;
    }

    public void setSettings(Properties settings) {
        this.settings = settings;
    }

    private void initLanguages() {
        List<Pair<String, Locale>> allLocales = new ArrayList<>();
        allLocales.add(new Pair<>("English", new Locale("en", "US")));
        allLocales.add(new Pair<>("Português", new Locale("pt", "BR")));
        localeChoiceBox.setConverter(new StringConverter<>() {
            @Override
            public String toString(Pair<String, Locale> pair) {
                return pair.getKey();
            }

            @Override
            public Pair<String, Locale> fromString(String string) {
                return null;
            }
        });
        localeChoiceBox.getItems().addAll(allLocales);
        localeChoiceBox.setValue(allLocales.get(0));
    }

    @FXML
    private void handleCancel() {
        okClicked = false;
        window.close();
    }

    @FXML
    private void handleOK() {
        okClicked = true;
        window.close();
    }

    public Properties showAndWait() {
        return null;
    }
}
