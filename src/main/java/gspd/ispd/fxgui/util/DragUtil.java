package gspd.ispd.fxgui.util;

import javafx.scene.Node;

public class DragUtil {
    private double x0;
    private double y0;
    private boolean stopped;

    public DragUtil() {
        x0 = 0;
        y0 = 0;
        stopped = true;
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
    }
}
