package gspd.ispd.fxgui;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.ToggleGroup;

import java.util.Locale;

public class SettingsPage {
    @FXML
    private ChoiceBox<Locale> localeChoiceBox;
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

}
