package gspd.ispd.fxgui.simples;

import gspd.ispd.model.data.Machine;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import org.controlsfx.control.PropertySheet;
import org.controlsfx.control.StatusBar;
import org.controlsfx.property.BeanPropertyUtils;

import java.net.URL;
import java.util.ResourceBundle;

public class DefaultWindowController implements Initializable {

    @FXML
    private UsersPaneController usersPaneController;
    @FXML
    private TerminalPaneController terminalPaneController;
    @FXML
    private StatusBar statusBar;
    @FXML
    private PropertySheet propertySheet;

    public DefaultWindowController() {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
}
