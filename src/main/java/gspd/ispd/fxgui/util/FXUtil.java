package gspd.ispd.fxgui.util;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;

/**
 * Implements some util methods to work with JavaFX in iSPD.
 */
public class FXUtil {
    static private DragUtil dragger = new DragUtil();
    static private DrawerUtil drawer = new DrawerUtil();
    /**
     * Adds the needed behaviour to make a node draggable
     *
     * @param node the node to become draggable
     */
    static public void makeDraggable(Node node) {
        dragger.makeDraggable(node);
    }

    static public void putNodeInContext(Node node, Pane context) {
        context.getChildren().add(node);
    }
}
