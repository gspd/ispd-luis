package gspd.ispd.fxgui.commons;

import javafx.scene.shape.Line;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;

public class XedEdgeShape extends EdgeShape {

    ///////////////////////////////////////////////
    ///////////////// CONSTRUCTOR /////////////////
    ///////////////////////////////////////////////

    private XedEdgeShape(Line baseLine, EdgeShape edge) {
        super(baseLine, edge);
        Path x = new Path(
                new MoveTo(-4, -4),
                new LineTo(4, 4),
                new MoveTo(-4, 4),
                new LineTo(4, -4)
        );
        x.layoutXProperty().bind(lengthProperty().multiply(0.5));
        x.strokeProperty().bind(fillProperty());
        getChildren().add(x);
    }

    public XedEdgeShape(EdgeShape edge) {
        this(null, edge);
    }

    public XedEdgeShape(Line baseLine) {
        this(baseLine, null);
    }
}
