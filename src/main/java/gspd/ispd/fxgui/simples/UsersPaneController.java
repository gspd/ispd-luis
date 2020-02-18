package gspd.ispd.fxgui.simples;

import gspd.ispd.model.ISPDModel;
import gspd.ispd.model.ModelService;
import gspd.ispd.model.data.User;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.awt.event.ActionEvent;
import java.net.URL;
import java.util.ResourceBundle;


public class UsersPaneController implements Initializable {

    @FXML
    private ListView<User> listView;

    private static final ModelService service = ModelService.getInstance();

    public void setModel(ISPDModel model) {
        if (listView.itemsProperty().isBound()) {
            listView.itemsProperty().unbind();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        listView.setCellFactory((lView -> new UserCell()));
        listView.getItems().addAll(new User("luis"), new User("joao"));
        listView.setOnKeyPressed(this::handleKey);
    }

    private void handleKey(KeyEvent event) {
        if (event.getCode() == KeyCode.DELETE) {
            removeUser();
        }
    }

    @FXML
    private void handleAddButton() {
        addUser();
    }

    @FXML
    private void handleRemoveButton() {
        removeUser();
    }

    private void addUser() {
        User newUser = new User(service.generateUsername());
        service.add(newUser);
        listView.getItems().add(newUser);
    }

    private void removeUser() {
        User selectedUser = listView.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            service.removeUser(selectedUser);
        }
    }
}
