package gspd.ispd.fxgui;

import gspd.ispd.MainApp;
import gspd.ispd.model.User;
import gspd.ispd.model.VM;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class MainPage {
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
        main.openSettingsPage();
    }

    @FXML
    private void handleCloseProgram() {
        main.close();
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
        // TODO: this is just some users tests, remove it when it is no longer needed
        userTable.getItems().add(new User("default"));
        userTable.getItems().add(new User("Luís"));
        userTable.getItems().add(new User("João"));
    }

    private void initVMTable() {
        idVMColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        userVMColumn.setCellValueFactory(new PropertyValueFactory<>("ownerName"));
        hypervisorVMColumn.setCellValueFactory(new PropertyValueFactory<>("hypervisor"));
        coresVMColumn.setCellValueFactory(new PropertyValueFactory<>("cores"));
        memoryVMColumn.setCellValueFactory(new PropertyValueFactory<>("memory"));
        storageVMColumn.setCellValueFactory(new PropertyValueFactory<>("storage"));
        osVMColumn.setCellValueFactory(new PropertyValueFactory<>("os"));
        // TODO: this is just some vm tests, remove it when it is no longer needed
        User owner = userTable.getItems().get(0);
        vmTable.getItems().add(new VM(owner, "VB", 2, 2.0, 10.0, "Linux"));
        vmTable.getItems().add(new VM(owner, "VB", 2, 2.0, 10.0, "Linux"));
        vmTable.getItems().add(new VM(owner, "VW", 2, 2.0, 10.0, "Linux"));
        vmTable.getItems().add(new VM(owner, "VW", 2, 2.0, 10.0, "Linux"));
    }
}
