package gspd.ispd.fxgui;

import gspd.ispd.ISPD;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.util.Pair;

import java.net.URL;
import java.util.List;

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
