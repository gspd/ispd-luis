package gspd.ispd.fxgui.commons;

import javafx.scene.shape.Polygon;

public class PointedArrow extends BaseArrow {

    ///////////////////////////////////////////////
    ///////////////// CONSTRUCTOR /////////////////
    ///////////////////////////////////////////////

    public PointedArrow(Arrow arrow) {
        super(arrow);
        this.triangle = new Polygon(0.0, 0.0, -8.0, 3.0, -8.0, -3.0);
        arrow.getChildren().add(triangle);
    }

    ////////////////////////////////////////////////
    /////////////// EVENT HANDLERS /////////////////
    ////////////////////////////////////////////////

    private Polygon triangle;
    @Override
    protected void updateLength() {
        super.updateLength();
        triangle.setTranslateX(getLength());
    }

    @Override
    protected void updateFill() {
        super.updateFill();
        triangle.setFill(getFill());
    }
}
