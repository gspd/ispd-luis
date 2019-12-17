package gspd.ispd;

import gspd.ispd.fxgui.MainPage;
import gspd.ispd.fxgui.SettingsPage;
import gspd.ispd.fxgui.GUI;
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
        GUI.setSettingsWindow(window, new Stage(), this);
    }

    public void openMainPage() {
        GUI.setMainWindow(window, this);
    }

    public void close() {
        GUI.closeMainWindow(window, this);
    }
}
