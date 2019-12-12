package gspd.ispd;

import gspd.ispd.fxgui.MainPage;
import gspd.ispd.fxgui.SettingsPage;
import gspd.ispd.model.ISPDModel;
import javafx.application.Application;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Locale;
import java.util.Properties;

//    PROTOTYPE VERSION yet
public class MainApp extends Application {

    private Stage primaryStage;
    private ObjectProperty<ISPDModel> model = new SimpleObjectProperty<>();
    private Properties settings;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        setModel(new ISPDModel());
        ISPD.setLocale(new Locale("pt", "BR"));
        openMainPage();
    }

    public Properties getSettings() {
        return settings;
    }

    public void setModel(ISPDModel model) {
        this.model.set(model);
    }

    public ISPDModel getModel() {
        return model.get();
    }

    public ObjectProperty<ISPDModel> modelProperty() {
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
            settingsStage.initOwner(primaryStage);
            settingsStage.initModality(Modality.WINDOW_MODAL);
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
            primaryStage = new Stage();
            primaryStage.initModality(Modality.WINDOW_MODAL);
            primaryStage.setScene(scene);
            MainPage controller = loader.getController();
            controller.setMainApp(this);
            controller.init();
            primaryStage.show();
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
}
