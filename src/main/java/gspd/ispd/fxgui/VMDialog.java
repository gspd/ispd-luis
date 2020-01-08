package gspd.ispd.fxgui;

import gspd.ispd.MainApp;
import gspd.ispd.model.User;
import gspd.ispd.model.VM;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ChoiceBox;
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

    private boolean okClicked;
    private VM vm;
    private Stage window;
    private MainApp main;

    public void init() {
        userField.setItems(main.getModel().getUsers());
        osField.getItems().addAll("Linux", "Windows");
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("VMDialog.fxml"));
    }

    @FXML
    private void handleOK() {
        if (createVM()) {
            // close the window
        }
    }

    @FXML
    private void handleCancel() {
        vm = null;
        // close the window
    }

    public boolean createVM() {
        try {
            vm = new VM();
            vm.setOwner(userField.getValue());
            vm.setCores(Integer.parseInt(coresField.getText()));
            vm.setMemory(Double.parseDouble(memoryField.getText()));
            vm.setStorage(Double.parseDouble(storageField.getText()));
            vm.setHypervisor(hypervisorField.getValue());
            vm.setOs(osField.getValue());
            return true;
        } catch (NumberFormatException e) {
            // open an alert saying that data was not correctly inserted
        } finally {
            vm = null;
            return false;
        }
    }

    public VM getVm() {
        return vm;
    }

    private boolean validate() {
        return false;
    }

    public boolean isOkClicked() {
        return okClicked;
    }
}
