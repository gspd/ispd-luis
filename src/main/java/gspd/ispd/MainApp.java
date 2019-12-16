package gspd.ispd;

import gspd.ispd.fxgui.MainPage;
import gspd.ispd.fxgui.SettingsPage;
import gspd.ispd.fxgui.util.FXUtil;
import gspd.ispd.model.ISPDModel;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Locale;
import java.util.Properties;

//    PROTOTYPE VERSION yet
public class MainApp extends Application {

    private Stage window;
    private ISPDModel model;
    private Properties settings;

    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
        window.setOnCloseRequest(event -> {
            event.consume();
            this.close();
        });
        model = new ISPDModel();
        ISPD.setLocale(new Locale("pt", "BR"));
        openMainPage();
    }

    public Properties getSettings() {
        return settings;
    }

    public void setModel(ISPDModel model) {
        this.model = model;
    }

    public ISPDModel getModel() {
        return model;
    }

    public void openSettingsPage() {
        try {
            // Loads FXML file (GUI description)
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("fxgui/SettingsPage.fxml"));
            loader.setResources(ISPD.getStrings());
            // starts the content of the main window
            Scene scene = new Scene(loader.load());
            // creates the window itself
            Stage settingsStage = new Stage();
            settingsStage.setTitle(ISPD.getStrings().getString("settings.title"));
            // define the settings window as a child of the stage that called it
            settingsStage.initOwner(window);
            settingsStage.initModality(Modality.APPLICATION_MODAL);
            settingsStage.setScene(scene);
            // get the controller of the settings page
            SettingsPage controller = loader.getController();
            // initialize the settings page
            controller.init();
            controller.setSettingsStage(settingsStage);
            // finally show the settings page and wait the user
            settingsStage.showAndWait();
            // if the OK button was clicked, then apply new settings
            if (controller.isOkClicked()) {
                // apply new configurations
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openMainPage() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("fxgui/MainPage.fxml"));
            loader.setResources(ISPD.getStrings());
            Scene scene = new Scene(loader.load());
            window = new Stage();
            window.initModality(Modality.APPLICATION_MODAL);
            window.setScene(scene);
            window.setOnCloseRequest(event -> {
                event.consume();
                close();
            });
            MainPage controller = loader.getController();
            controller.setMain(this);
            controller.init();
            window.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setLocale(Locale locale) {
        settings.setProperty("locale.language", locale.getLanguage());
        settings.setProperty("locale.country", locale.getCountry());
    }

    public Locale getLocale() {
        try {
            String language = settings.getProperty("locale.language", "en");
            String country = settings.getProperty("locale.country", "US");
            return new Locale(language, country);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void close() {
        FXUtil.AnswerType answer;
        Alert alert;
        if (model.isNotSaved()) {
            answer = FXUtil.checkClosingWithoutSaving();
            if (answer == FXUtil.AnswerType.SAVE_AND_EXIT) {
                // TODO: check out this file
                model.saveToFile("test_file.imsx");
                window.close();
            } else if (answer == FXUtil.AnswerType.EXIT_WITHOUT_SAVE) {
                window.close();
            }
        } else {
            window.close();
        }
    }
}
