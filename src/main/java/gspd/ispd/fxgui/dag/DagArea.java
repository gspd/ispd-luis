package gspd.ispd.fxgui.dag;

import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;

public class DagArea extends SplitPane {

    public DagArea() {

        Node menu = new IconMenu();

        super.getItems().setAll(menu, new Pane());
        super.setOrientation(Orientation.HORIZONTAL);
        super.setDividerPositions(0.5);
    }
}
