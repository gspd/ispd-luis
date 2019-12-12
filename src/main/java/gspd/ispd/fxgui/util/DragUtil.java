package gspd.ispd.fxgui.util;

import javafx.scene.Node;

public class DragUtil {
    private double x;
    private double y;
    private Node node;

    public DragUtil() {
        x = 0;
        y = 0;
    }

    public void makeDraggable(Node node) {
        node.setOnMousePressed(event -> {
            x = event.getX();
            y = event.getY();
        });
        node.setOnMouseDragged(event -> {
            double dx, dy;
            dx = event.getX() - x;
            dy = event.getY() - y;
            node.setTranslateY(dx);
            node.setTranslateY(dy);
        });
    }
}
