package gspd.ispd.fxgui.util;

import javafx.scene.Node;
import javafx.scene.layout.Pane;

public class DrawerUtil<E extends Node> {

    public void makeDrawable(Pane canvas) {
        canvas.setOnMouseClicked(event -> {
            // TODO: understand what it should be made here
        });
    }
}
