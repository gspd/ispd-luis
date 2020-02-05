package gspd.ispd.fxgui.util;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


public class StrokeBox extends Rectangle {

    private Node node;

    public StrokeBox(Node node, Color color) {
        super();
        setStroke(color);
        this.node = node;
    }



}
