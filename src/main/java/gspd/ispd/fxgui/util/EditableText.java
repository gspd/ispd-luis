package gspd.ispd.fxgui.util;

import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.scene.Group;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class EditableText extends Group {
    private TextField textField;
    private Text text;

    public EditableText() {
        this("");
    }

    public EditableText(String text) {
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
    }
}
