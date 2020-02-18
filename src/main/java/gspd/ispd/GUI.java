package gspd.ispd;

import gspd.ispd.fxgui.GUIUtil;
import gspd.ispd.fxgui.MainWindowController;
import gspd.ispd.fxgui.simples.DefaultWindowController;
import gspd.ispd.model.ISPDModel;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.Properties;
import java.util.Stack;

//    PROTOTYPE VERSION yet
public class GUI extends Application {

    private Stage mainStage;
    private ISPDModel model;
    private FXMLLoader loader;
    private Properties settings = new Properties();
    /**
     * Stack of the stages (windows) of the iSPD, making easier to
     * return to previous windows
     */
    private Stack<Stage> stageStack = new Stack<>();

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Define what to do as iSPD starts.
     *
     * @param primaryStage the window
     * @throws Exception something goes wrong
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        stageStack.push(primaryStage);
        mainStage = primaryStage;
        mainStage.setOnCloseRequest(event -> {
            event.consume();
            this.close();
        });
        model = new ISPDModel();
        loader = new FXMLLoader();
        loader.setLocation(DefaultWindowController.class.getResource("DefaultWindow.fxml"));
        loader.setResources(ISPD.strings);
        Pane root = loader.load();
        Scene scene = new Scene(root);
        mainStage.setScene(scene);
        mainStage.show();
        loader = new FXMLLoader();
        loader.setResources(ISPD.strings);
        MainWindowController.create(mainStage, this);
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
        loadSettings();
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
        GUIUtil.closeMainWindow(mainStage, this);
    }

    private void loadSettings() {
        //
    }

    private void pushFXML(String filename) {

    }

    private void pushStage(Stage stage) {

    }
}
