package gspd.ispd;

import gspd.ispd.fxgui.*;
import gspd.ispd.fxgui.dag.DagArea;
import gspd.ispd.fxgui.simples.DefaultWindowController;
import gspd.ispd.model.ISPDModel;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
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
        loadTest(primaryStage);
    }

    private void loadDraw() throws IOException {
        Pane root = FXMLLoader.load(DrawToolController.class.getResource("DrawTool.fxml"), ISPD.strings);
        Scene scene = new Scene(root);
        mainStage.setScene(scene);
        mainStage.show();
    }

    private void loadDefault() throws IOException {
        loader = new FXMLLoader();
        loader.setLocation(DefaultWindowController.class.getResource("DefaultWindow.fxml"));
        loader.setResources(ISPD.strings);
        Pane root = loader.load();
        Scene scene = new Scene(root);
        mainStage.setScene(scene);
        mainStage.show();
    }

    private void loadMain() throws IOException {
        loader = new FXMLLoader();
        loader.setLocation(MainWindowController.class.getResource("MainWindow.fxml"));
        loader.setResources(ISPD.strings);
        Pane root = loader.load();
        MainWindowController controller = loader.getController();
        Scene scene = new Scene(root);
        mainStage.setScene(scene);
        controller.init();
        mainStage.show();
    }

    private void loadTest(Stage primaryStage) {

        primaryStage.setScene(new Scene(new DagArea(), 300, 300));
        primaryStage.show();
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
