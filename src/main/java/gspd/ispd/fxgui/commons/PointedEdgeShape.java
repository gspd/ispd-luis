package gspd.ispd.fxgui.commons;

import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;

public class PointedEdgeShape extends EdgeShape {

    ///////////////////////////////////////////////
    ///////////////// CONSTRUCTOR /////////////////
    ///////////////////////////////////////////////

    private PointedEdgeShape(Line baseLine, EdgeShape edgeShape) {
        super(baseLine, edgeShape);
        Polygon triangle = new Polygon(0.0, 0.0, -8.0, 3.0, -8.0, -3.0);
        triangle.layoutXProperty().bind(lengthProperty().multiply(0.9));
        triangle.fillProperty().bind(fillProperty());
        getChildren().add(triangle);
    }

    public PointedEdgeShape(EdgeShape edge) {
        this(null, edge);
    }

    public PointedEdgeShape(Line baseLine) {
        this(baseLine, null);
    }
}
