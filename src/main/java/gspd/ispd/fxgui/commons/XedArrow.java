package gspd.ispd.fxgui.commons;

import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;

public class XedArrow extends BaseArrow {

    ///////////////////////////////////////////////
    ///////////////// CONSTRUCTOR /////////////////
    ///////////////////////////////////////////////

    public XedArrow(Arrow arrow) {
        super(arrow);
        x = new Path(
                new MoveTo(-4,-4),
                new LineTo(4, 4),
                new MoveTo(-4, 4),
                new LineTo(4, -4)
        );
        arrow.getChildren().add(x);
    }

    ////////////////////////////////////////////////
    /////////////// EVENT HANDLERS /////////////////
    ////////////////////////////////////////////////

    private Path x;
    @Override
    protected void updateLength() {
        super.updateLength();
        x.setTranslateX(getLength() / 2);
    }

    @Override
    protected void updateFill() {
        super.updateFill();
        x.setFill(getFill());
    }
}
