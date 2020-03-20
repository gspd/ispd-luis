package gspd.ispd.fxgui.workload.dag.shapes;

import gspd.ispd.fxgui.commons.EdgeShape;
import gspd.ispd.fxgui.commons.PointedEdgeShape;
import gspd.ispd.fxgui.commons.XedEdgeShape;
import javafx.scene.shape.Line;

public class FailShape extends EdgeShape {

    public FailShape() {
        super(new XedEdgeShape(new PointedEdgeShape(new Line())));
    }
}
