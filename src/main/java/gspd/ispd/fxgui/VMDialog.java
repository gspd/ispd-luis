package gspd.ispd.fxgui;

import gspd.ispd.model.User;
import gspd.ispd.model.VM;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class VMDialog {
    @FXML
    private ChoiceBox<User> userField;
    @FXML
    private TextField memoryField;
    @FXML
    private TextField storageField;
    @FXML
    private TextField coresField;
    @FXML
    private ChoiceBox<String> hypervisorField;
    @FXML
    private ChoiceBox<String> osField;

    private VM vm;
    private boolean okClicked;
    private Stage window;

    public void init() {
    }

    @FXML
    private void handleOK() {
        okClicked = true;
    }

    @FXML
    private void handleCancel() {
        okClicked = false;
    }

    public void setWindow(Stage window) {
        this.window = window;
    }
}
