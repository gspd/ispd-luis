package gspd.ispd;

import gspd.ispd.fxgui.MainPage;
import gspd.ispd.fxgui.SettingsPage;
import gspd.ispd.fxgui.WelcomePage;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.LoadListener;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Locale;

//    PROTOTYPE VERSION yet
public class MainApp extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        ISPD.setLocale(new Locale("pt","BR"));
        Parent root = createContent();
        if (root != null) {
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();
        } else {
            System.out.println("Problem!!");
        }
    }

    public Parent createContent() {
        try {
//            MAIN_PAGE
//            FXMLLoader loader = new FXMLLoader();
//            loader.setLocation(getClass().getResource("fxgui/MainPage.fxml"));
//            loader.setResources(ISPD.getStrings());
//            Parent root = loader.load();
//            MainPage controller = loader.getController();
//            controller.init();
//            SETTINGS_PAGE
//            FXMLLoader loader = new FXMLLoader();
//            loader.setLocation(getClass().getResource("fxgui/SettingsPage.fxml"));
//            loader.setResources(ISPD.getStrings());
//            Parent root = loader.load();
//            SettingsPage controller = loader.getController();
//            controller.init();
//            WELCOME_PAGE
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("fxgui/WelcomePage.fxml"));
            loader.setResources(ISPD.getStrings());
            Parent root = loader.load();
            WelcomePage controller = loader.getController();
            controller.init();
            return root;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
