package gspd.ispd.fxgui;

import gspd.ispd.ISPD;
import gspd.ispd.MainApp;
import gspd.ispd.model.User;
import gspd.ispd.model.VM;
import gspd.ispd.fxgui.util.FormBuilder;
import gspd.ispd.model.data.MachineData;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class MainWindow {
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
    private Pane hardwarePane;
    @FXML
    private ScrollPane hardwareScrollPane;
    @FXML
    private ToggleGroup hardwareToolboxToggle;
    @FXML
    private ScrollPane propertiesScrollPane;
    @FXML
    private CheckMenuItem fullScreenMenuItem;

    // This ImageView is not present in FXML file. It represents
    // the image that follows cursor to help user remember what
    // he is up to add.
    private ImageView followImageView;

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
        followImageView = new ImageView();
        followImageView.setOpacity(0.6);
        hardwareToolboxToggle.selectedToggleProperty().addListener(((observable, oldValue, newValue) -> {
            if (newValue != null && newValue != linkIcon) {
                ToggleButton selected = (ToggleButton) observable.getValue();
                ImageView imageView = (ImageView) selected.getGraphic();
                Image image = imageView.getImage();
                followImageView.setImage(image);
                GUI.follow(followImageView, hardwarePane);
                hardwarePane.setOnMouseClicked(event -> {
                    if (event.getButton() == MouseButton.PRIMARY) {
                        ImageView imvw = new ImageView();
                        imvw.setImage(image);
                        imvw.setLayoutX(followImageView.getLayoutX());
                        imvw.setLayoutY(followImageView.getLayoutY());
                        GUI.makeDraggable(imvw);
                        hardwarePane.getChildren().add(imvw);
                    }
                });
            } else if (newValue == linkIcon) {
                GUI.unfollow(hardwarePane);
                hardwarePane.setOnMouseClicked(event -> {});
            } else {
                GUI.unfollow(hardwarePane);
                hardwarePane.setOnMouseClicked(event -> {});
            }
        }));
        propertiesScrollPane.setContent(FormBuilder.getInstance().makeForm(MachineData.class));
        DrawPane dPane = new DrawPane();
        Group group = new Group(dPane);
        hardwareScrollPane.setContent(group);
        Node n1 = new ImageView(new Image("/gspd/ispd/gui/images/botao_no.gif"));
        Node n2 = new ImageView(new Image("/gspd/ispd/gui/images/botao_internet.gif"));
        dPane.add(n1, 20.0, 20.0);
        dPane.add(n2, 200.0, 200.0);
        Line line = new Line();
        line.setStartX(n1.getLayoutX() + n1.getBoundsInLocal().getCenterX());
        line.setStartY(n1.getLayoutY() + n1.getBoundsInLocal().getCenterY());
        line.setEndX(n2.getLayoutX() + n2.getBoundsInLocal().getCenterX());
        line.setEndY(n2.getLayoutY() + n2.getBoundsInLocal().getCenterY());
        line.setStroke(Color.BLUE);
        line.setStrokeWidth(4.0);
        dPane.add(line);
        line.toBack();
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
        nameUserColumn.setCellValueFactory(row -> row.getValue().nameProperty());
        // any change in the user table changes directly the users list in the model
        userTable.setItems(main.getModel().getUsers());
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
                    vm = editVM(vm);
                    if (vm != null) {
                        vmTable.getItems().set(index, vm);
                    }
                }
            });
            return row;
        });
        vmTable.getSelectionModel();
        // Hardware Pane
        // the {width, height} of hardware pane (area that we draw hardware) is aways at least 1.5 times its parent scroll pane
        hardwarePane.minWidthProperty().bind(hardwareScrollPane.widthProperty().multiply(1.5));
        hardwarePane.minHeightProperty().bind(hardwareScrollPane.heightProperty().multiply(1.5));
        // the hardware scroll pane is pannable
        GUI.makePannable(hardwareScrollPane);
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
        main.getModel().duplicate(vm);
    }

    @FXML
    private void handleAddUserClicked() {
        User user = createUser();
        if (user != null) {
            main.getModel().add(user);
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
