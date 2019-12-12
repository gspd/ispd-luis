package gspd.ispd.fxgui;

import gspd.ispd.MainApp;
import gspd.ispd.fxgui.util.FXUtil;
import gspd.ispd.model.User;
import gspd.ispd.model.VM;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.*;

import java.util.ResourceBundle;

import gspd.ispd.ISPD;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

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
    private Hyperlink dataIcon;
    @FXML
    private Hyperlink iterationIcon;
    @FXML
    private Hyperlink parallelIcon;
    @FXML
    private Hyperlink hardwareMousePointerIcon;
    @FXML
    private Hyperlink workloadMousePointerIcon;
    @FXML
    private TableView<VM> vmTableView;
    @FXML
    private TableView<User> userTableView;
    @FXML
    private TableColumn<User, Integer> idUserTableColumn;
    @FXML
    private TableColumn<User, String> nameUserTableColumn;
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
    private Label dragme;

    private MainApp mainApp;

    public void init() {
        initButtons();
        initUsers();
        FXUtil.makeDraggable(dragme);
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    @FXML
    private void handleEditSettings() {
        mainApp.openSettingsPage();
    }

    private void initUsers() {
        userTableView.itemsProperty().bind(mainApp.getModel().usersProperty());
        idUserTableColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameUserTableColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
    }

    private void initButtons() {
        // disable remove button oly if there is no selected item in user table
        removeUserButton.disableProperty().bind(userTableView.getSelectionModel().selectedItemProperty().isNull());
        // disable {remote,duplicate} button only if there is no selected item in the VM table
        removeVMButton.disableProperty().bind(vmTableView.getSelectionModel().selectedItemProperty().isNull());
        duplicateVMButton.disableProperty().bind(vmTableView.getSelectionModel().selectedItemProperty().isNull());
    }
}
