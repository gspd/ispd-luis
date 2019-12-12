package gspd.ispd.fxgui.util;

import javafx.scene.Node;

public class FXUtil {
    static private DragUtil dragger = new DragUtil();
    static public void makeDraggable(Node node) {
        dragger.makeDraggable(node);
    }
}
