package gspd.ispd.fxgui;

import gspd.ispd.MainApp;
import gspd.ispd.model.User;
import gspd.ispd.model.VM;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.StringConverter;

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

    public VMDialog() {

    }

    public void init() {
        userField.setItems(main.getModel().getUsers());
        userField.setConverter(new StringConverter<User>() {
            @Override
            public String toString(User object) {
                return object.getName();
            }

            @Override
            public User fromString(String string) {
                return null;
            }
        });
        osField.getItems().addAll("Linux", "Windows");
    }

    @FXML
    private void handleOK() {
        if (createVM()) {
            window.close();
        } else {
            System.out.println("could not create VM");
        }
    }

    @FXML
    private void handleCancel() {
        vm = null;
        window.close();
    }

    public void loadVM(VM vm) {
        userField.setValue(vm.getOwner());
        memoryField.setText(String.valueOf(vm.getMemory()));
        storageField.setText(String.valueOf(vm.getStorage()));
        coresField.setText(String.valueOf(vm.getCores()));
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
        } catch (Exception e) {
            e.printStackTrace();
            vm = null;
            return false;
        }
    }

    public VM getVm() {
        return vm;
    }

    public void setWindow(Stage window) {
        this.window = window;
    }

    public void setMain(MainApp main) {
        this.main = main;
    }

    private boolean validate() {
        return false;
    }

    public boolean isOkClicked() {
        return okClicked;
    }
}
