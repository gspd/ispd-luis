package gspd.ispd.fxgui.dag.shapes;

import gspd.ispd.fxgui.commons.NodeShape;
import javafx.scene.paint.Color;
import javafx.scene.shape.ClosePath;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;

public class SwitchShape extends NodeShape {

    public SwitchShape() {
        Path path = new Path(
                new MoveTo(0, 0),
                new LineTo(18, -9),
                new LineTo(18, 9),
                new ClosePath()
        );
        path.strokeProperty().bind(strokeProperty());
        path.fillProperty().bind(fillProperty());
        setStroke(Color.BLACK);
        setFill(Color.BLACK);
        super.getChildren().setAll(path);
    }
}
