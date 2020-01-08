package gspd.ispd.fxgui;

import gspd.ispd.ISPD;
import gspd.ispd.MainApp;
import gspd.ispd.model.ISPDModel;
import gspd.ispd.model.User;
import gspd.ispd.model.VM;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainWindow implements Initializable {
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
            loader = main.getLoader();
            loader.setLocation(GUI.class.getResource("MainWindow.fxml"));
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
    private void handleAddVmClicked() {
        // create a new window to insert VM
        VM vm = createNewVM();
        // if VM is returned
        if (vm != null) {
            // then adds in the model
            vmTable.getItems().add(vm);
        }
    }

    @FXML
    private void handleRemoveVmClicked() {
        vmTable.getItems().remove(vmTable.getSelectionModel().getSelectedIndex());
        vmTable.getSelectionModel().clearSelection();
    }

    @FXML
    private void handleDuplicateVmClicked() {
        vmTable.getItems().add(vmTable.getSelectionModel().getSelectedItem());
    }

    @FXML
    private void handleAddUserClicked() {
        User user = createNewUser();
        if (user != null) {
            userTable.getItems().add(user);
        }
    }

    @FXML
    private void handleRemoveUser() {
        userTable.getItems().remove(userTable.getSelectionModel().getSelectedIndex());
        userTable.getSelectionModel().clearSelection();
    }

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
        userTable.setItems(main.getModel().getUsers());
    }

    private void initVMTable() {
        idVMColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        userVMColumn.setCellValueFactory(new PropertyValueFactory<>("ownerName"));
        hypervisorVMColumn.setCellValueFactory(new PropertyValueFactory<>("hypervisor"));
        coresVMColumn.setCellValueFactory(new PropertyValueFactory<>("cores"));
        memoryVMColumn.setCellValueFactory(new PropertyValueFactory<>("memory"));
        storageVMColumn.setCellValueFactory(new PropertyValueFactory<>("storage"));
        osVMColumn.setCellValueFactory(new PropertyValueFactory<>("os"));
        vmTable.setItems(main.getModel().getVms());
        vmTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    private VM createNewVM() {
        VM vm;
        VMDialog controller;
        FXMLLoader loader;
        Scene scene;
        Stage dialog;
        Parent root;
        try {
            loader = new FXMLLoader();
            loader.setLocation(VMDialog.class.getResource("VMDialog.fxml"));
            loader.setResources(ISPD.getStrings());
            root = loader.load();
            scene = new Scene(root);
            dialog = new Stage();
            controller = loader.getController();
            controller.setWindow(dialog);
            controller.setMain(main);
            controller.init();
            dialog.initOwner(window);
            dialog.setTitle("Edit VM");
            dialog.initModality(Modality.WINDOW_MODAL);
            dialog.setScene(scene);
            dialog.showAndWait();
            vm = controller.getVm();
        } catch (IOException e) {
            System.out.println("Error loading FXML");
            vm = null;
        } catch (RuntimeException e) {
            e.printStackTrace();
            vm = null;
        }
        return vm;
    }

    private User createNewUser() {
        User user = null;
        FXMLLoader loader;
        Scene scene;
        Stage dialog;
        UserDialog controller;
        Parent root;
        try {
            loader = new FXMLLoader();
            loader.setLocation(UserDialog.class.getResource("UserDialog.fxml"));
            loader.setResources(ISPD.getStrings());
            root = loader.load();
            scene = new Scene(root);
            dialog = new Stage();
            controller = loader.getController();
            controller.setWindow(dialog);
            controller.setMain(main);
            controller.init();
            dialog.initOwner(window);
            dialog.setTitle("Edit User");
            dialog.initModality(Modality.WINDOW_MODAL);
            dialog.setScene(scene);
            dialog.showAndWait();
            user = controller.getUser();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
}
