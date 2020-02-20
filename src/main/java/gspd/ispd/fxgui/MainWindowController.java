package gspd.ispd.fxgui;

import gspd.ispd.GUI;
import gspd.ispd.ISPD;
import gspd.ispd.model.ISPDModel;
import gspd.ispd.model.data.User;
import gspd.ispd.model.data.VM;
import gspd.ispd.fxgui.util.FormBuilder;
import gspd.ispd.model.data.MachineData;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class MainWindowController {
    @FXML
    private TextArea terminalOutputArea;
    @FXML
    private TextField terminalInput;
    @FXML
    private TextArea helpArea;
    @FXML
    private ToggleButton machineIcon;
    @FXML
    private ToggleButton linkIcon;
    @FXML
    private ToggleButton clusterIcon;
    @FXML
    private ToggleButton switchIcon;
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
    private MenuItem simulateMenuItem;
    @FXML
    private MenuItem redoMenuItem;
    @FXML
    private MenuItem undoMenuItem;
    @FXML
    private ScrollPane hardwareScrollPane;
    @FXML
    private ToggleGroup hardwareToolboxToggle;
    @FXML
    private ScrollPane propertiesScrollPane;
    @FXML
    private CheckMenuItem fullScreenMenuItem;
    @FXML
    private CheckMenuItem gridMenuItem;

    @FXML
    private AnchorPane drawPane;
    @FXML
    private DrawPaneController drawPaneController;

    private GUI main;
    private Stage window;
    private ISPDModel model;

    public MainWindowController() {
        // try {
        //     FXMLLoader loader = new FXMLLoader(getClass().getResource("MainWindow.fxml"));
        //     loader.setResources(ISPD.strings);
        //     loader.setController(this);
        //     loader.setRoot(this);
        //     loader.load();
        // } catch (IOException e) {
        //     throw new RuntimeException(e);
        // }
    }

    public static void create(Stage window, GUI main) {
        try {
            FXMLLoader loader = new FXMLLoader(MainWindowController.class.getResource("MainWindow.fxml"));
            MainWindowController controller;
            Scene scene;
            loader.setResources(ISPD.strings);
            scene = new Scene(loader.load());
            window.setScene(scene);
            controller = loader.getController();
            controller.setMain(main);
            controller.setWindow(window);
            controller.init();
            window.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void init() {
        ///////////// TEST
        ContextMenu cmenu = new ContextMenu();
        MenuItem copy = new MenuItem("Copy");
        MenuItem paste = new MenuItem("Paste");
        cmenu.getItems().addAll(copy, paste);
        terminalOutputArea.setContextMenu(cmenu);
        propertiesScrollPane.setContent(FormBuilder.getInstance().makeForm(MachineData.class));
        // drawPane.setMinWidth(Double.POSITIVE_INFINITY);
        // drawPane.setMinHeight(Double.POSITIVE_INFINITY);
        Node n1 = new ImageView(new Image("/gspd/ispd/gui/images/botao_no.gif"));
        Node n2 = new ImageView(new Image("/gspd/ispd/gui/images/botao_internet.gif"));
        drawPaneController.add(n1, 20.0, 20.0);
        drawPaneController.add(n2, 200.0, 200.0);
        undoMenuItem.setOnAction(event -> drawPaneController.undo());
        drawPaneController.gridEnableProperty().bind(gridMenuItem.selectedProperty());
        Text text = new Text("This is a text");
        text.setFill(Color.GRAY);
        drawPaneController.add(text, 100, 300);
        Node n3 = new ImageView(new Image("/gspd/ispd/gui/images/botao_no.gif"));
        drawPaneController.pinToAdd(n3);
//         hardwarePane.setOnMouseClicked(event -> {
//             if (event.getButton() == MouseButton.PRIMARY && hardwareToolboxToggle.getSelectedToggle() != null) {
//                 Image image = followImageView.getImage();
//                 if (image != null) {
//                     ImageView imageView = new ImageView();
//                     imageView.setImage(image);
//                     imageView.setLayoutX(followImageView.getLayoutX());
//                     imageView.setLayoutY(followImageView.getLayoutY());
//                     GUI.makeDraggable(imageView);
//                     hardwarePane.getChildren().add(imageView);
//                 }
//             }
//         });
        ///////////// REMOVE
        // disable remove user button [-] only if there is no selected item in user table
        removeUserButton.disableProperty().bind(userTable.getSelectionModel().selectedItemProperty().isNull());
        // disable remove VM button only if there is no selected item in the VM table
        removeVMButton.disableProperty().bind(vmTable.getSelectionModel().selectedItemProperty().isNull());
        // disable duplicate VM button only if there is no selected item in the VM table
        duplicateVMButton.disableProperty().bind(vmTable.getSelectionModel().selectedItemProperty().isNull());
        // USERS TABLE
        // the UID column of user table has the 'id' property of each user
        idUserColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        // the Name column of user table has the 'name' property of each user
        // nameUserColumn.setCellValueFactory(row -> row.getValue().nameProperty());
        // any change in the user table changes directly the users list in the model
        // userTable.setItems(main.getModel().getUsers());
        // it is not possible to add VM if there is no users
        addVMButton.disableProperty().bind(Bindings.isEmpty(userTable.getItems()));
        // every time an user row is double clicked, open an user dialog to change its data
        // to accomplish that, we have to set RowFactory
        userTable.setRowFactory(tableView -> {
            TableRow<User> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                // checks if click is doubled and is indeed a row with an user
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    User user = userTable.getSelectionModel().getSelectedItem();
                    int index = userTable.getSelectionModel().getSelectedIndex();
                    // open the user dialog to change the user
                    user = editUser(user);
                    // if the user was changed, change it in table
                    if (user != null) {
                        userTable.getItems().set(index, user);
                    }
                }
            });
            // its obligated to return the row, since we are defining a RowFactory
            return row;
        });
        // VMS TABLE
        idVMColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        // userVMColumn.setCellValueFactory(row -> row.getValue().getOwner().nameProperty());
        hypervisorVMColumn.setCellValueFactory(row -> row.getValue().hypervisorProperty());
        coresVMColumn.setCellValueFactory(new PropertyValueFactory<>("cores"));
        memoryVMColumn.setCellValueFactory(new PropertyValueFactory<>("memory"));
        storageVMColumn.setCellValueFactory(new PropertyValueFactory<>("storage"));
        osVMColumn.setCellValueFactory(row -> row.getValue().osProperty());
        // vmTable.setItems(main.getModel().getVms());
        vmTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        vmTable.setRowFactory(tableView -> {
            TableRow<VM> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    VM vm = vmTable.getSelectionModel().getSelectedItem();
                    int index = vmTable.getSelectionModel().getSelectedIndex();
                    vm = editVM(vm);
                    if (vm != null) {
                        vmTable.getItems().set(index, vm);
                    }
                }
            });
            return row;
        });
        vmTable.getSelectionModel();
        // the hardware scroll pane is pannable
        GUIUtil.makePannable(hardwareScrollPane);
    }

    public void setMain(GUI main) {
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

    public void setModel(ISPDModel model) {
        this.model = model;
    }

    public ISPDModel getModel() {
        return model;
    }

    @FXML
    private void handleCloseProgram() {
        main.close();
    }

    @FXML
    private void handleFullScreenClicked() {
        if (!window.isFullScreen()) {
            window.setFullScreen(true);
        } else {
            window.setFullScreen(false);
        }
    }

    @FXML
    private void handleAddVmClicked() {
        // create a new window to insert VM
        VM vm = createVm();
        // if VM is returned
        if (vm != null) {
            // then adds in the model
            vmTable.getItems().add(vm);
        }
    }

    @FXML
    private void unselectToolbox() {
        hardwareToolboxToggle.selectToggle(null);
    }

    @FXML
    private void handleRemoveVmClicked() {
        vmTable.getItems().remove(vmTable.getSelectionModel().getSelectedIndex());
        vmTable.getSelectionModel().clearSelection();
    }

    @FXML
    private void handleDuplicateVmClicked() {
        VM vm = vmTable.getSelectionModel().getSelectedItem();
        // main.getModel().duplicate(vm);
    }

    @FXML
    private void handleAddUserClicked() {
        User user = createUser();
        if (user != null) {
            // main.getModel().getUsers().add(user);
        }
    }

    @FXML
    private void handleRemoveUser() {
        userTable.getItems().remove(userTable.getSelectionModel().getSelectedIndex());
        userTable.getSelectionModel().clearSelection();
    }

    private VM createVm() {
        VM vm = null;
        VMDialog controller;
        FXMLLoader loader;
        Scene scene;
        Stage dialog;
        Parent root;
        try {
            loader = new FXMLLoader();
            loader.setLocation(VMDialog.class.getResource("VMDialog.fxml"));
            loader.setResources(ISPD.strings);
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
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        return vm;
    }

    private VM editVM(VM vm) {
        VMDialog controller;
        FXMLLoader loader;
        Scene scene;
        Stage dialog;
        Parent root;
        try {
            loader = new FXMLLoader();
            loader.setLocation(VMDialog.class.getResource("VMDialog.fxml"));
            loader.setResources(ISPD.strings);
            root = loader.load();
            scene = new Scene(root);
            dialog = new Stage();
            controller = loader.getController();
            controller.setWindow(dialog);
            controller.setMain(main);
            controller.init();
            controller.loadVM(vm);
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

    private User createUser() {
        User user = null;
        FXMLLoader loader;
        Scene scene;
        Stage dialog;
        UserDialog controller;
        Parent root;
        try {
            loader = new FXMLLoader();
            loader.setLocation(UserDialog.class.getResource("UserDialog.fxml"));
            loader.setResources(ISPD.strings);
            root = loader.load();
            scene = new Scene(root);
            dialog = new Stage();
            controller = loader.getController();
            controller.setWindow(dialog);
            controller.setMain(main);
            controller.init();
            dialog.initOwner(window);
            dialog.setTitle("New User");
            dialog.initModality(Modality.WINDOW_MODAL);
            dialog.setScene(scene);
            dialog.showAndWait();
            user = controller.getUser();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return user;
    }

    private User editUser(User user) {
        FXMLLoader loader;
        Scene scene;
        Stage dialog;
        UserDialog controller;
        Parent root;
        try {
            loader = new FXMLLoader();
            loader.setLocation(UserDialog.class.getResource("UserDialog.fxml"));
            loader.setResources(ISPD.strings);
            root = loader.load();
            scene = new Scene(root);
            dialog = new Stage();
            controller = loader.getController();
            controller.setWindow(dialog);
            controller.setMain(main);
            controller.init();
            controller.loadUser(user);
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
}
