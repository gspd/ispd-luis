package gspd.ispd.fxgui;

import gspd.ispd.ISPD;
import gspd.ispd.MainApp;
import gspd.ispd.model.User;
import gspd.ispd.model.VM;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;

public class MainWindow {
    @FXML
    private TextArea terminalOutputArea;
    @FXML
    private TextField terminalInputField;
    @FXML
    private TextArea helpArea;
    @FXML
    private Hyperlink machineIcon;
    @FXML
    private Hyperlink linkIcon;
    @FXML
    private Hyperlink clusterIcon;
    @FXML
    private Hyperlink switchIcon;
    @FXML
    private Hyperlink taskIcon;
    @FXML
    private Hyperlink dependencyIcon;
    @FXML
    private Hyperlink messageIcon;
    @FXML
    private Hyperlink synchronizationIcon;
    @FXML
    private Hyperlink hardwareMousePointerIcon;
    @FXML
    private Hyperlink workloadMousePointerIcon;
    @FXML
    private TableView<VM> vmTable;
    @FXML
    private TableColumn<VM, Integer> idVMColumn;
    @FXML
    private TableColumn<VM, String> userVMColumn;
    @FXML
    private TableColumn<VM, String> hypervisorVMColumn;
    @FXML
    private TableColumn<VM, Integer> coresVMColumn;
    @FXML
    private TableColumn<VM, Double> memoryVMColumn;
    @FXML
    private TableColumn<VM, Double> storageVMColumn;
    @FXML
    private TableColumn<VM, String> osVMColumn;
    @FXML
    private TableView<User> userTable;
    @FXML
    private TableColumn<User, Integer> idUserColumn;
    @FXML
    private TableColumn<User, String> nameUserColumn;
    @FXML
    private Button addUserButton;
    @FXML
    private Button removeUserButton;
    @FXML
    private Button addVMButton;
    @FXML
    private Button duplicateVMButton;
    @FXML
    private Button removeVMButton;
    @FXML
    private MenuItem exitMenuItem;
    // TODO: this 'drag' is temporary (don't forget to delete soon)
    @FXML
    private ProgressBar drag;

    private MainApp main;
    private Stage window;

    public static void create(Stage window, MainApp main) {
        try {
            FXMLLoader loader;
            MainWindow controller;
            Scene scene;
            loader = new FXMLLoader();
            loader.setLocation(GUI.class.getResource("MainWindow.fxml"));
            loader.setResources(ISPD.getStrings());
            scene = new Scene(loader.load());
            window.setScene(scene);
            controller = loader.getController();
            controller.setMain(main);
            controller.init();
            controller.setWindow(window);
            window.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void init() {
        initButtons();
        initUsers();
        initVMTable();
        GUI.makeDraggable(drag);
    }

    public void setMain(MainApp main) {
        this.main = main;
    }

    @FXML
    private void handleEditSettings() {
        Stage settingsWindow = new Stage();
        SettingsWindow.create(window, settingsWindow, this.main);
    }

    public Stage getWindow() {
        return window;
    }

    public void setWindow(Stage window) {
        this.window = window;
    }

    @FXML
    private void handleCloseProgram() {
        main.close();
    }

    @FXML
    private void handleAddVM() {}

    private void initButtons() {
        // disable remove button oly if there is no selected item in user table
        removeUserButton.disableProperty().bind(userTable.getSelectionModel().selectedItemProperty().isNull());
        // disable {remote,duplicate} button only if there is no selected item in the VM table
        removeVMButton.disableProperty().bind(vmTable.getSelectionModel().selectedItemProperty().isNull());
        duplicateVMButton.disableProperty().bind(vmTable.getSelectionModel().selectedItemProperty().isNull());
    }

    private void initUsers() {
        idUserColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameUserColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        updateUsers();
    }

    private void initVMTable() {
        idVMColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        userVMColumn.setCellValueFactory(new PropertyValueFactory<>("ownerName"));
        hypervisorVMColumn.setCellValueFactory(new PropertyValueFactory<>("hypervisor"));
        coresVMColumn.setCellValueFactory(new PropertyValueFactory<>("cores"));
        memoryVMColumn.setCellValueFactory(new PropertyValueFactory<>("memory"));
        storageVMColumn.setCellValueFactory(new PropertyValueFactory<>("storage"));
        osVMColumn.setCellValueFactory(new PropertyValueFactory<>("os"));
        updateVMTable();
    }

    private void updateVMTable() {
        vmTable.getItems().setAll(main.getModel().getVms());
    }

    private void updateUsers() {
        userTable.getItems().setAll(main.getModel().getUsers());
    }
}