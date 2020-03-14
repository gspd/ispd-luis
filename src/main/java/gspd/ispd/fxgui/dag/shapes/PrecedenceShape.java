package gspd.ispd.fxgui.dag.shapes;

import gspd.ispd.fxgui.commons.EdgeShape;
import gspd.ispd.fxgui.commons.PointedEdgeShape;
import javafx.scene.shape.Line;

public class PrecedenceShape extends EdgeShape {

    public PrecedenceShape() {
        super(new PointedEdgeShape(new Line()));
    }
}
