package gspd.ispd.fxgui;

import gspd.ispd.GUI;
import gspd.ispd.model.data.User;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class UserDialog {

    @FXML
    private TextField nameInput;

    private GUI main;
    private Stage window;
    private User user;

    public void init() {

    }

    public User getUser() {
        return user;
    }

    public GUI getMain() {
        return main;
    }

    public void setMain(GUI main) {
        this.main = main;
    }

    public void setWindow(Stage window) {
        this.window = window;
    }

    public void loadUser(User user) {
        try {
            this.user = user;
            nameInput.setText(user.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleOkButton() {
        if (createUser()) {
            window.close();
        } else {
            System.out.println("Something went wrong while creating user");
        }
    }

    @FXML
    private void handleCancelButton() {
        user = null;
        window.close();
    }

    private boolean createUser() {
        try {
            if (user == null) {
                user = new User();
            }
            user.setName(nameInput.getText());
            return true;
        } catch (Exception e) {
            System.out.println("Cannot create user");
            e.printStackTrace();
            user = null;
            return false;
        }
    }
}
