package gspd.ispd.fxgui.commons;

public abstract class BaseArrow extends Arrow {

    private Arrow arrow;
    public BaseArrow(Arrow arrow) {
        super();
        this.arrow = arrow;
        arrow.lengthProperty().bind(lengthProperty());
        arrow.fillProperty().bind(fillProperty());
        super.getChildren().setAll(arrow);
    }

    ////////////////////////////////////////////////
    /////////////// EVENT HANDLERS /////////////////
    ////////////////////////////////////////////////

    @Override
    protected void updateLength() {
        arrow.updateLength();
    }

    @Override
    protected void updateFill() {
        arrow.updateFill();
    }
}
