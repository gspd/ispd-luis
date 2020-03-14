package gspd.ispd.fxgui.dag.shapes;

import gspd.ispd.fxgui.commons.EdgeShape;
import gspd.ispd.fxgui.commons.PointedEdgeShape;
import javafx.scene.shape.Line;

public class PrefixShape extends EdgeShape {

    public PrefixShape() {
        super(new PointedEdgeShape(new Line() {
            {this.getStrokeDashArray().setAll(8.0, 5.0);}
        }));
    }
}
