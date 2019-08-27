package gspd.ispd.fxgui;

import gspd.ispd.ISPD;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Pair;
import javafx.util.StringConverter;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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

    public void init() {
        initLanguages();
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
        ///////
    }

    @FXML
    private void handleOK() {
        handleApply();
    }

    @FXML
    private void handleApply() {
        Locale locale = localeChoiceBox.getValue().getValue();
        ISPD.setLocale(locale);
        // fire an event to update strings
    }
}
