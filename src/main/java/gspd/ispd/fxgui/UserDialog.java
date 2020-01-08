package gspd.ispd.fxgui;

import gspd.ispd.MainApp;
import gspd.ispd.model.User;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class UserDialog {

    @FXML
    private TextField nameInput;

    private MainApp main;
    private Stage window;
    private User user;

    public void init() {

    }

    public User getUser() {
        return user;
    }

    public MainApp getMain() {
        return main;
    }

    public void setMain(MainApp main) {
        this.main = main;
    }

    public Stage getWindow() {
        return window;
    }

    public void setWindow(Stage window) {
        this.window = window;
    }

    public void loadUser(User user) {
        try {
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
            System.out.println("Something wen wrong while creating user");
        }
    }

    @FXML
    private void handleCancelButton() {
        user = null;
        window.close();
    }

    private boolean createUser() {
        try {
            user = new User();
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
