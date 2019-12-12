package gspd.ispd.fxgui;

import gspd.ispd.MainApp;
import gspd.ispd.model.User;
import gspd.ispd.model.VM;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.ResourceBundle;

import gspd.ispd.ISPD;
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

    private MainApp mainApp;

    public void init() {
        setCompactView();
        initTooltips();
        initButtons();
        initUsers();
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

    public void setCompactView() {
        setIconsTypeAsCompact();
    }

    public void setFullView() {
        setIconsTypeAsFull();
    }

    public void initTooltips() {
        setHardwareIconsTooltip();
        setWorkloadIconsTooltip();
        setMousePointerTooltip();
    }

    private void setIconsTypeAsCompact() {
        setHardwareIconsAsCompact();
        setWorkloadIconsAsCompact();
    }

    private void setHardwareIconsAsCompact() {
        machineIcon.setText("");
        linkIcon.setText("");
        clusterIcon.setText("");
        switchIcon.setText("");
    }

    private void setWorkloadIconsAsCompact() {
        taskIcon.setText("");
        dependencyIcon.setText("");
        messageIcon.setText("");
        synchronizationIcon.setText("");
        dataIcon.setText("");
        iterationIcon.setText("");
        parallelIcon.setText("");
    }

    private void setIconsTypeAsFull() {
        setHardwareIconsAsFull();
        setWorkloadIconsAsFull();
    }

    private void setHardwareIconsAsFull() {
        ResourceBundle strings = ISPD.getStrings();
        machineIcon.setText(strings.getString("main.hardware.machine"));
        linkIcon.setText(strings.getString("main.hardware.link"));
        clusterIcon.setText(strings.getString("main.hardware.cluster"));
        switchIcon.setText(strings.getString("main.hardware.switch"));
    }

    private void setWorkloadIconsAsFull() {
        ResourceBundle strings = ISPD.getStrings();
        taskIcon.setText(strings.getString("main.workload.task"));
        dependencyIcon.setText(strings.getString("main.workload.dependency"));
        messageIcon.setText(strings.getString("main.workload.message"));
        synchronizationIcon.setText(strings.getString("main.workload.synchronization"));
        dataIcon.setText(strings.getString("main.workload.data"));
        iterationIcon.setText(strings.getString("main.workload.iteration"));
        parallelIcon.setText(strings.getString("main.workload.parallel"));
    }

    private void setHardwareIconsTooltip() {
        ResourceBundle strings = ISPD.getStrings();
        machineIcon.setTooltip(new Tooltip(strings.getString("tooltip.main.hardware.machine")));
        linkIcon.setTooltip(new Tooltip(strings.getString("tooltip.main.hardware.link")));
        clusterIcon.setTooltip(new Tooltip(strings.getString("tooltip.main.hardware.cluster")));
        switchIcon.setTooltip(new Tooltip(strings.getString("tooltip.main.hardware.switch")));
    }

    private void setWorkloadIconsTooltip() {
        ResourceBundle strings = ISPD.getStrings();
        taskIcon.setTooltip(new Tooltip(strings.getString("tooltip.main.workload.task")));
        dependencyIcon.setTooltip(new Tooltip(strings.getString("tooltip.main.workload.dependency")));
        messageIcon.setTooltip(new Tooltip(strings.getString("tooltip.main.workload.message")));
        synchronizationIcon.setTooltip(new Tooltip(strings.getString("tooltip.main.workload.synchronization")));
        dataIcon.setTooltip(new Tooltip(strings.getString("tooltip.main.workload.data")));
        iterationIcon.setTooltip(new Tooltip(strings.getString("tooltip.main.workload.iteration")));
        parallelIcon.setTooltip(new Tooltip(strings.getString("tooltip.main.workload.parallel")));
    }

    private void setMousePointerTooltip() {
        Tooltip tooltip = new Tooltip(ISPD.getStrings().getString("tooltip.main.mouse"));
        hardwareMousePointerIcon.setTooltip(tooltip);
        workloadMousePointerIcon.setTooltip(tooltip);
    }
}
