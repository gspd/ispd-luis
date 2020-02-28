package gspd.ispd.fxgui.simples;

import gspd.ispd.fxgui.DrawToolController;
import gspd.ispd.fxgui.GUIUtil;
import gspd.ispd.fxgui.util.ToggleActionCancel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Toggle;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.controlsfx.control.PropertySheet;
import org.controlsfx.control.StatusBar;

import java.net.URL;
import java.util.ResourceBundle;

public class DefaultWindowController implements Initializable {

    @FXML
    private UsersPaneController usersPaneController;
    @FXML
    private TerminalPaneController terminalPaneController;
    @FXML
    private DrawToolController drawToolController;
    @FXML
    private StatusBar statusBar;
    @FXML
    private PropertySheet propertySheet;

    public DefaultWindowController() {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Image img1 = new Image(GUIUtil.class.getResource("images/botao_no.gif").toExternalForm());
        Image img2 = new Image(GUIUtil.class.getResource("images/botao_cluster.gif").toExternalForm());
        ImageView iv1 = new ImageView(img1);
        ImageView iv2 = new ImageView(img2);
        drawToolController.setTool(iv1, new ToggleActionCancel() {
            @Override
            public void action(Toggle toggle) {
                drawToolController.getDrawPaneController().pinToAdd(new ImageView(img1));
            }

            @Override
            public void cancel(Toggle toggle) {
                drawToolController.getDrawPaneController().cancelPinning();
            }
        });
        drawToolController.setTool(iv2, new ToggleActionCancel() {
            @Override
            public void action(Toggle toggle) {
                drawToolController.getDrawPaneController().pinToAdd(new ImageView(img2));
            }

            @Override
            public void cancel(Toggle toggle) {
                drawToolController.getDrawPaneController().cancelPinning();
            }
        });
    }
}
