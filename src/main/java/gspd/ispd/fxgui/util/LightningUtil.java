package gspd.ispd.fxgui.util;

import javafx.scene.Node;

public class LightningUtil {
    public void highlight(Node node) {
        node.setOpacity(0.6);
    }

    public void lowlight(Node node) {
        node.setOpacity(1);
    }

    public void makeHoverable(Node node) {
        node.setOnMouseEntered(event -> highlight(node));
        node.setOnMouseExited(event -> lowlight(node));
    }
}
