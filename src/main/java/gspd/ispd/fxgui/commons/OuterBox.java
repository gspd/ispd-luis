package gspd.ispd.fxgui.commons;

import javafx.beans.Observable;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


public class OuterBox extends NodeShape {

    private Rectangle border;
    public OuterBox() {
        widthProperty().addListener(this::update);
        heightProperty().addListener(this::update);
        border = new Rectangle();
        border.fillProperty().bind(fillProperty());
        border.strokeProperty().bind(strokeProperty());
        setFill(Color.WHITE);
        setStroke(Color.BLACK);
        super.getChildren().add(0, border);
    }

    //////////////////////////////////////////////
    ////////////// EVENT HANDLERS ////////////////
    //////////////////////////////////////////////

    private void update(Observable observable) {
        update();
    }

    private void update() {
        border.setWidth(getWidth());
        border.setHeight(getHeight());
    }

    ///////////////////////////////////////////////
    ////////////// PROPERTIES /////////////////////
    ///////////////////////////////////////////////

    /**
     * The width property
     */
    private DoubleProperty width = new SimpleDoubleProperty(this, "width", 0.0);
    public double getWidth() {
        return width.get();
    }
    public DoubleProperty widthProperty() {
        return width;
    }
    public void setWidth(double width) {
        this.width.set(width);
    }

    /**
     * The heigth property
     */
    private DoubleProperty height = new SimpleDoubleProperty(this, "height", 0.0);
    public double getHeight() {
        return height.get();
    }
    public DoubleProperty heightProperty() {
        return height;
    }
    public void setHeight(double height) {
        this.height.set(height);
    }

}
