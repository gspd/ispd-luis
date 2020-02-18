package gspd.ispd.fxgui.simples;

import gspd.ispd.model.ModelService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;


public class TerminalPaneController {

    @FXML
    private TextArea textArea;
    @FXML
    private TextField inputCommand;

    @FXML
    private void handleOK(ActionEvent event) {
        send(inputCommand.getText());
        event.consume();
    }

    @FXML
    private void handleKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            send(inputCommand.getText());
            event.consume();
        }
    }

    public void send(String text) {
        if (!text.equals("")) {
            textArea.appendText(text + "\n");
            inputCommand.setText("");
            ModelService.getInstance().executeCommand(text);
        }
    }

    public void notifyException(Exception exception) {
        textArea.appendText("EXCEPTION: " + exception.getMessage() + "\n");
        textArea.appendText("CAUSE: " + exception.getCause() + "\n");
    }
}
