package gspd.ispd.fxgui;

import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class DrawToolController implements Initializable {
    @FXML
    private Pane drawPane;
    @FXML
    private ScrollPane drawPaneScroll;
    @FXML
    private DrawPaneController drawPaneController;
    @FXML
    private CheckBox gridCheckBox;
    @FXML
    private Slider zoomSlider;

    @FXML
    private void handleAddText() {
        EditableText newText = new EditableText(this, "new text");
        drawPaneController.pinToAdd(newText);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        drawPaneController.gridEnableProperty().bind(gridCheckBox.selectedProperty());
        drawPaneController.zoomProperty().bind(zoomSlider.valueProperty().divide(100));
        drawPane.minWidthProperty().bind(drawPaneScroll.widthProperty().multiply(2));
        drawPane.minHeightProperty().bind(drawPaneScroll.heightProperty().multiply(2));
        drawPaneScroll.setOnMousePressed(this::handleMousePressedScroll);
        drawPaneScroll.setOnMouseReleased(this::handleMouseReleasedScroll);
        // redirects focus of the scroll pane directly to the draw pane itself
        drawPaneScroll.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                drawPaneScroll.getContent().requestFocus();
            }
        });
    }

    private void handleMousePressedScroll(MouseEvent event) {
        if (event.getButton() == MouseButton.MIDDLE) {
            drawPaneScroll.setPannable(true);
            event.consume();
        }
    }

    private void handleMouseReleasedScroll(MouseEvent event) {
        if (event.getButton() == MouseButton.MIDDLE) {
            drawPaneScroll.setPannable(false);
            event.consume();
        }
    }

    public static class EditableText extends Group {
        private TextField textField;
        private Text text;
        /**
         * The outer class instance that created this object
         */
        private DrawToolController drawTool;

        public EditableText(DrawToolController drawTool) {
            this(drawTool, "");
        }

        public EditableText(DrawToolController drawTool, String text) {
            this.drawTool = drawTool;
            this.text = new Text(text);
            this.text.setFill(Color.GRAY);
            this.textField = new TextField();
            this.text.setOnMousePressed(this::handleMousePressed);
            this.text.setOnMouseClicked(this::handleMouseEdit);
            this.textField.setOnKeyReleased(this::handleKeys);
            this.textField.setOnMousePressed(this::handleMousePressed);
            // cancels editing aways the field loose focus
            this.textField.focusedProperty().addListener(this::focusChanged);
            this.text.setVisible(true);
            this.textField.setVisible(false);
            getChildren().addAll(this.text, this.textField);
        }

        private void focusChanged(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
            if (!newValue) {
                cancelChange();
            }
        }

        private void handleMouseEdit(MouseEvent event) {
            if (event.getClickCount() == 2) {
                textField.setText(text.getText());
                textField.setLayoutX(text.getBoundsInLocal().getMinX());
                textField.setLayoutY(text.getBoundsInLocal().getMinY());
                // exchange the text and the textfield visibility
                text.setVisible(false);
                textField.setVisible(true);
                textField.requestFocus();
                // temporary deselects the node and block its selection (until editing ends)
                drawTool.drawPaneController.getSelectionModel().clearSelection(this);
                drawTool.drawPaneController.getSelectionModel().block(this);
                event.consume();
            }
        }

        private void handleMousePressed(MouseEvent event) {
            // redirects mouse pressed event target to 'this' (Group) in order
            // to avoid event loop in events chain, which could cause
            // StackOverflow exception
            Event.fireEvent(this, event);
            event.consume();
        }

        private void handleKeys(KeyEvent event) {
            if (event.getCode() == KeyCode.ENTER) {
                changeText();
                event.consume();
            } else if (event.getCode() == KeyCode.ESCAPE) {
                cancelChange();
                event.consume();
            }
        }

        private void changeText() {
            text.setText(textField.getText());
            finalizeEdit();
        }

        private void cancelChange() {
            finalizeEdit();
        }

        private void finalizeEdit() {
            getParent().requestFocus();
            // exchange the text and the textfield visibility
            textField.setVisible(false);
            text.setVisible(true);
            // unblock the selection of this node and select it again
            drawTool.drawPaneController.getSelectionModel().unblock(this);
            drawTool.drawPaneController.getSelectionModel().select(this);
        }
    }
}
