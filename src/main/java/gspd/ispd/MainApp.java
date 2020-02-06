package gspd.ispd;

import gspd.ispd.fxgui.GUI;
import gspd.ispd.fxgui.MainWindow;
import gspd.ispd.model.ISPDModel;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;

import java.util.Locale;

//    PROTOTYPE VERSION yet
public class MainApp extends Application {

    private Stage mainWindow;
    private ISPDModel model;
    private FXMLLoader loader;

    /**
     * Define what to do as iSPD starts.
     *
     * @param primaryStage the window
     * @throws Exception something goes wrong
     */
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
        loader.setResources(ISPD.strings);
        MainWindow.create(mainWindow, this);
    }

    /**
     * Defines whats iSPD does when the application is normally closed
     *
     * @throws Exception something goes wrong
     */
    @Override
    public void stop() throws Exception {
        super.stop();
    }

    /**
     * Defines what iSPD does when initializing
     *
     * @throws Exception something goes wrong
     */
    @Override
    public void init() throws Exception {
        super.init();
    }

    public void setModel(ISPDModel model) {
        this.model = model;
    }

    public ISPDModel getModel() {
        return model;
    }

    public FXMLLoader getLoader() {
        return loader;
    }

    public void close() {
        GUI.closeMainWindow(mainWindow, this);
    }
}
