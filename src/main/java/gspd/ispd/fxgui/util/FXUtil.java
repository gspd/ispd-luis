package gspd.ispd.fxgui.util;

import javafx.scene.Node;

/**
 * Implements some util methods to work with JavaFX in iSPD.
 */
public class FXUtil {
    static private DragUtil dragger = new DragUtil();

    /**
     * Adds the needed behaviour to make a node draggable
     *
     * @param node the node to become draggable
     */
    static public void makeDraggable(Node node) {
        dragger.makeDraggable(node);
    }
}
