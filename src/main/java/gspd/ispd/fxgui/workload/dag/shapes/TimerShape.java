package gspd.ispd.fxgui.workload.dag.shapes;

import gspd.ispd.fxgui.commons.NodeShape;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;

public class TimerShape extends NodeShape {

    public TimerShape() {
        createContent();
    }

    private void createContent() {
        Circle circle = new Circle(10.0);
        circle.fillProperty().bind(fillProperty());
        circle.strokeProperty().bind(strokeProperty());
        circle.setCenterX(0.0);
        circle.setCenterY(0.0);
        Path pointers = new Path(
                new MoveTo(0.0, 0.0),
                new LineTo(0.0, -5.0),
                new MoveTo(0.0, 0.0),
                new LineTo(6.0, -2.0)
        );
        pointers.relocate(0.0, -5.0);
        pointers.strokeProperty().bind(strokeProperty());
        setFill(Color.LIGHTYELLOW);
        setStroke(Color.BLACK);
        super.getChildren().setAll(circle, pointers);
    }
}
