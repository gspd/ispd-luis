package gspd.ispd;

import gspd.ispd.fxgui.GUI;
import gspd.ispd.fxgui.MainWindow;
import gspd.ispd.model.ISPDModel;
import gspd.ispd.model.User;
import gspd.ispd.model.VM;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;

import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

//    PROTOTYPE VERSION yet
public class MainApp extends Application {

    private Stage mainWindow;
    private Stage settingsWindow;
    private ISPDModel model;
    private Properties settings;
    private FXMLLoader loader;

    @Override
    public void start(Stage primaryStage) throws Exception {
        mainWindow = primaryStage;
        mainWindow.setOnCloseRequest(event -> {
            event.consume();
            this.close();
        });
        model = new ISPDModel();
        loader = new FXMLLoader();
        ISPD.setLocale(new Locale("en", "US"));
        ResourceBundle bundle = ISPD.getStrings();
        loader.setResources(bundle);
        MainWindow.create(mainWindow, this);
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
        GUI.setSettingsWindow(mainWindow, new Stage(), this);
    }

    public FXMLLoader getLoader() {
        return loader;
    }

    public void close() {
        GUI.closeMainWindow(mainWindow, this);
    }
}
