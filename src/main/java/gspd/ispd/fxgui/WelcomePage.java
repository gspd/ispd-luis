package gspd.ispd.fxgui;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.util.Pair;

import java.net.URL;

public class WelcomePage {
    @FXML
    private ListView<Pair<String, URL>> recentList;

    public void init() {
        initRecentList();
    }

    private void initRecentList() {
        //
    }
}
