package gspd.ispd.fxgui;

import gspd.ispd.ISPD;
import gspd.ispd.MainApp;
import gspd.ispd.model.User;
import gspd.ispd.model.VM;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
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
    @FXML
    private Pane hardwarePane;
    @FXML
    private ScrollPane hardwareScrollPane;

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
        // BUTTONS
        // disable remove button oly if there is no selected item in user table
        removeUserButton.disableProperty().bind(userTable.getSelectionModel().selectedItemProperty().isNull());
        // disable {remote,duplicate} button only if there is no selected item in the VM table
        removeVMButton.disableProperty().bind(vmTable.getSelectionModel().selectedItemProperty().isNull());
        duplicateVMButton.disableProperty().bind(vmTable.getSelectionModel().selectedItemProperty().isNull());
        // USERS TABLE
        idUserColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameUserColumn.setCellValueFactory(row -> row.getValue().nameProperty());
        userTable.setItems(main.getModel().getUsers());
        userTable.setRowFactory(tableView -> {
            TableRow<User> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    User user = userTable.getSelectionModel().getSelectedItem();
                    int index = userTable.getSelectionModel().getSelectedIndex();
                    user = createNewUser(user);
                    if (user != null) {
                        userTable.getItems().set(index, user);
                    }
                }
            });
            return row;
        });
        // VMS TABLE
        idVMColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        userVMColumn.setCellValueFactory(row -> row.getValue().getOwner().nameProperty());
        hypervisorVMColumn.setCellValueFactory(row -> row.getValue().hypervisorProperty());
        coresVMColumn.setCellValueFactory(new PropertyValueFactory<>("cores"));
        memoryVMColumn.setCellValueFactory(new PropertyValueFactory<>("memory"));
        storageVMColumn.setCellValueFactory(new PropertyValueFactory<>("storage"));
        osVMColumn.setCellValueFactory(row -> row.getValue().osProperty());
        vmTable.setItems(main.getModel().getVms());
        vmTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        vmTable.setRowFactory(tableView -> {
            TableRow<VM> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    VM vm = vmTable.getSelectionModel().getSelectedItem();
                    int index = vmTable.getSelectionModel().getSelectedIndex();
                    vm = createNewVm(vm);
                    if (vm != null) {
                        vmTable.getItems().set(index, vm);
                    }
                }
            });
            return row;
        });
        // Hardware Pane
        hardwarePane.minWidthProperty().bind(hardwareScrollPane.widthProperty().multiply(1.5));
        hardwarePane.minHeightProperty().bind(hardwareScrollPane.heightProperty().multiply(1.5));
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
        VM vm = createNewVm();
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

    private VM createNewVm(VM current) {
        VM vm = current;
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
            if (current != null) {
                controller.loadVM(current);
            }
            dialog.initOwner(window);
            dialog.setTitle("Edit VM");
            dialog.initModality(Modality.WINDOW_MODAL);
            dialog.setScene(scene);
            dialog.showAndWait();
            vm = controller.getVm();
        } catch (IOException e) {
            System.out.println("Error loading FXML");
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        return vm;
    }

    private VM createNewVm() {
        return createNewVm(null);
    }


    private User createNewUser(User current) {
        User user = current;
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
            if (current != null) {
                controller.loadUser(current);
            }
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

    private User createNewUser() {
        return createNewUser(null);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
}
