package gspd.ispd;

import gspd.ispd.fxgui.GUI;
import gspd.ispd.fxgui.MainWindow;
import gspd.ispd.model.ISPDModel;
import javafx.application.Application;
import javafx.stage.Stage;

import java.util.Locale;
import java.util.Properties;

//    PROTOTYPE VERSION yet
public class MainApp extends Application {

    private Stage mainWindow;
    private Stage settingsWindow;
    private ISPDModel model;
    private Properties settings;

    @Override
    public void start(Stage primaryStage) throws Exception {
        mainWindow = primaryStage;
        mainWindow.setOnCloseRequest(event -> {
            event.consume();
            this.close();
        });
        model = new ISPDModel();
        ISPD.setLocale(new Locale("pt", "BR"));
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

    public void close() {
        GUI.closeMainWindow(mainWindow, this);
    }
}
