package gspd.ispd.fxgui.util;

import javafx.scene.Node;
import javafx.scene.Parent;

public class DragUtil {
    private double x0;
    private double y0;

    public DragUtil() {
        x0 = 0;
        y0 = 0;
    }

    public void makeDraggable(Node node) {
        node.setOnMousePressed(event -> {
            x0 = event.getX();
            y0 = event.getY();
        });
        node.setOnMouseDragged(event -> {
            double dx, dy, x, y;
            x = node.getLayoutX();
            y = node.getLayoutY();
            dx = event.getX() - x0;
            dy = event.getY() - y0;
            node.setLayoutX(x + dx);
            node.setLayoutY(y + dy);
        });
        node.setOnMouseReleased(event -> {
            x0 = 0;
            y0 = 0;
        });
    }
}
