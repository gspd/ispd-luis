package gspd.ispd.fxgui.workload.dag.shapes;

import gspd.ispd.fxgui.commons.NodeShape;
import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class RecursionShape extends NodeShape {

    public RecursionShape() {
        Circle circle = new Circle(8.0);
        circle.fillProperty().bind(fillProperty());
        circle.strokeProperty().bind(strokeProperty());
        Text r = new Text("R");
        r.setFont(Font.font(10.0));
        r.fillProperty().bind(strokeProperty());
        StackPane pane = new StackPane();
        StackPane.setAlignment(circle, Pos.CENTER);
        StackPane.setAlignment(r, Pos.CENTER);
        pane.getChildren().setAll(circle, r);

        setFill(Color.WHITE);
        setStroke(Color.BLACK);
        super.getChildren().setAll(pane);
    }
}
