package gspd.ispd.fxgui.custom;

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
        setOnMouseClicked(this::handleMouseClicked);
        this.textField.setOnKeyReleased(this::handleKeys);
        // this.textField.setOnMousePressed(this::handleMousePressed);
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

    private void handleMouseClicked(MouseEvent event) {
        if (event.getClickCount() == 2) {
            startEdit();
            event.consume();
        }
    }

    private void startEdit() {
        textField.setText(text.getText());
        textField.setLayoutX(text.getBoundsInLocal().getMinX());
        textField.setLayoutY(text.getBoundsInLocal().getMinY());
        // exchange the text and the textfield visibility
        text.setVisible(false);
        textField.setVisible(true);
        textField.requestFocus();
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
        text.requestFocus();
        // exchange the text and the textfield visibility
        textField.setVisible(false);
        text.setVisible(true);
    }
}
