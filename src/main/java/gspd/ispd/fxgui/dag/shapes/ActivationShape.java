package gspd.ispd.fxgui.dag.shapes;

import gspd.ispd.fxgui.commons.NodeShape;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class ActivationShape extends NodeShape {

    public ActivationShape() {
        Circle circle = new Circle(8.0);
        circle.fillProperty().bind(fillProperty());
        circle.strokeProperty().bind(strokeProperty());
        setFill(Color.WHITE);
        setStroke(Color.BLACK);
        super.getChildren().setAll(circle);
    }
}
