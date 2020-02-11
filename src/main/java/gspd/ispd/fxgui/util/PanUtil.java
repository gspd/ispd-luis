package gspd.ispd.fxgui.util;

import javafx.scene.Cursor;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseButton;

public class PanUtil {

    private Cursor lastCursor;

    public void makePannable(ScrollPane pane) {
        pane.setOnMousePressed(event -> {
            if (event.getButton() == MouseButton.MIDDLE) {
                lastCursor = pane.getCursor();
                pane.setCursor(Cursor.HAND);
                pane.setPannable(true);
                event.consume();
            }
        });
        pane.setOnMouseReleased(event -> {
            if (event.getButton() == MouseButton.MIDDLE) {
                pane.setPannable(false);
                pane.setCursor(lastCursor);
                event.consume();
            }
        });
    }
}
