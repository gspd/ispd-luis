package gspd.ispd.fxgui;

import gspd.ispd.fxgui.custom.EditableText;
import gspd.ispd.fxgui.util.ToggleActionCancel;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class DrawToolController implements Initializable {
    @FXML
    private AnchorPane rootPane;
    @FXML
    private Pane drawPane;
    @FXML
    private DrawPaneController drawPaneController;
    @FXML
    private ScrollPane drawPaneScroll;
    @FXML
    private CheckBox gridCheckBox;
    @FXML
    private Slider zoomSlider;
    @FXML
    private ToolBar toolBar;
    @FXML
    private ToggleButton textToggleButton;
    @FXML
    private ToggleGroup toolBarToggle;

    private Map<Toggle, ToggleActionCancel> toggleActions = new HashMap<>();

    @FXML
    private void handlePointerAction() {
        cancelActions();
    }

    private void cancelActions() {
        toolBarToggle.selectToggle(null);
    }

    private Toggle addToggleButton(Node node) {
        // create a toggle button for the new node
        ToggleButton toggle = new ToggleButton();
        toggle.setText("");
        toggle.setGraphic(node);
        // assigns it to the same toggle group of the toolbar
        toggle.setToggleGroup(toolBarToggle);
        // adds the new custom toggle in toolbar
        toolBar.getItems().add(toggle);
        return toggle;
    }

    public void setTool(Node node, ToggleActionCancel trigger) {
        Toggle toggle = addToggleButton(node);
        toggleActions.put(toggle, trigger);
    }

    public DrawPaneController getDrawPaneController() {
        return drawPaneController;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        drawPaneController.gridEnableProperty().bind(gridCheckBox.selectedProperty());
        drawPaneController.zoomProperty().bind(zoomSlider.valueProperty().divide(100));
        drawPane.minWidthProperty().bind(drawPaneScroll.widthProperty().multiply(2));
        drawPane.minHeightProperty().bind(drawPaneScroll.heightProperty().multiply(2));
        drawPaneScroll.setOnMousePressed(this::handleMousePressedScroll);
        drawPaneScroll.setOnMouseReleased(this::handleMouseReleasedScroll);
        drawPaneScroll.focusedProperty().addListener(this::handleFocusChange);
        toolBarToggle.selectedToggleProperty().addListener(this::handleToggleChange);
        // specifies how the text toggle responds to action and cancelling requests
        toggleActions.put(textToggleButton, new ToggleActionCancel() {
            @Override
            public void action(Toggle toggle) {
                EditableText newText = new EditableText("new text");
                drawPaneController.pinToAdd(newText);
            }

            @Override
            public void cancel(Toggle toggle) {
                drawPaneController.cancelPinning();
            }
        });
    }

    private void handleMousePressedScroll(MouseEvent event) {
        // enable panning when mouse pressed
        if (event.getButton() == MouseButton.MIDDLE) {
            drawPaneScroll.setPannable(true);
            event.consume();
        }
    }

    private void handleMouseReleasedScroll(MouseEvent event) {
        // disable panning when mouse released
        if (event.getButton() == MouseButton.MIDDLE) {
            drawPaneScroll.setPannable(false);
            event.consume();
        }
    }

    private void handleFocusChange(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
        // redirects focus of the draw pane scroll to its content, when requested
        if (newValue) {
            drawPaneScroll.getContent().requestFocus();
        }
    }

    private void handleToggleChange(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
        // cancel old selected toggle if any
        if (oldValue != null) {
            ToggleActionCancel trigger = toggleActions.get(oldValue);
            if (trigger != null) {
                trigger.cancel(oldValue);
            }
        }
        // make action with the current selected toggle if any
        if (newValue != null) {
            ToggleActionCancel trigger = toggleActions.get(newValue);
            if (trigger != null) {
                trigger.action(newValue);
            }
        }
    }
}
