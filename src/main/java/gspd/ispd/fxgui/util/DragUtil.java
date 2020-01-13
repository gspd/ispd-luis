package gspd.ispd.fxgui.util;

import javafx.scene.Node;

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
            pauseMouse(node.getParent());
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
            returnMouse(node.getParent());
            x0 = 0;
            y0 = 0;
        });
    }

    private void pauseMouse(Node node) {
        Node parent = node.getParent();
        if (parent != null) {
            parent.setMouseTransparent(true);
            pauseMouse(parent);
        }
    }

    private void returnMouse(Node node) {
        Node parent = node.getParent();
        if (parent != null) {
            parent.setMouseTransparent(false);
            returnMouse(parent);
        }
    }
}
