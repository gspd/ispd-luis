package gspd.ispd.fxgui;

import gspd.ispd.ISPD;
import gspd.ispd.MainApp;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Pair;
import javafx.util.StringConverter;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

public class SettingsPage {
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
    private Stage settingsStage;
    private Properties settings;
    private boolean okClicked;

    public void init() {
        settings = new Properties();
        initLanguages();
        okClicked = false;
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    public void setSettingsStage(Stage settingsStage) {
        this.settingsStage = settingsStage;
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
        settingsStage.close();
    }

    @FXML
    private void handleOK() {
        okClicked = true;
        settingsStage.close();
    }
}
