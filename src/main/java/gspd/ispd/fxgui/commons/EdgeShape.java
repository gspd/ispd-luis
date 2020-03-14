package gspd.ispd.fxgui.commons;

import javafx.beans.Observable;
import javafx.beans.property.*;
import javafx.beans.value.ObservableValue;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.*;

public abstract class EdgeShape extends IconShape {

    ///////////////////////////////////////////////
    ///////////////// CONSTRUCTOR /////////////////
    ///////////////////////////////////////////////

    protected EdgeShape(Line baseLine, EdgeShape wrappedEdge) {
        baseLineProperty().addListener(this::baseLineChanged);
        wrappedEdgeProperty().addListener(this::wrappedEdgeChanged);
        // SETS
        setBaseLine(baseLine);
        setWrappedEdge(wrappedEdge);
    }

    /**
     * Constructs an simple lined arrow from a base
     * line
     */
    public EdgeShape(Line baseLine) {
        this(baseLine, null);
    }

    /**
     * Constructs a decorated arrow from another arrow
     */
    public EdgeShape(EdgeShape wrappedEdge) {
        this(null, wrappedEdge);
    }

    ////////////////////////////////////////////////
    /////////////// EVENT HANDLERS /////////////////
    ////////////////////////////////////////////////

    private void wrappedEdgeChanged(ObservableValue<? extends EdgeShape> observable, EdgeShape oldValue, EdgeShape newValue) {
        if (oldValue != null) {
            oldValue.lengthProperty().unbind();
            oldValue.fillProperty().unbind();
            getChildren().remove(oldValue);
        }
        if (newValue != null) {
            newValue.lengthProperty().bind(lengthProperty());
            newValue.fillProperty().bind(fillProperty());
            getChildren().add(newValue);
        }
    }


    private void baseLineChanged(ObservableValue<? extends Line> observable, Line oldValue, Line newValue) {
        if (oldValue != null) {
            oldValue.endXProperty().unbind();
            oldValue.strokeProperty().unbind();
            getChildren().remove(oldValue);
        }
        if (newValue != null) {
            getChildren().add(newValue);
            newValue.endXProperty().bind(lengthProperty());
            newValue.strokeProperty().bind(fillProperty());
        }
    }

    ///////////////////////////////////////////////////
    //////////////// PROPERTIES ///////////////////////
    ///////////////////////////////////////////////////

    /**
     * The length of the arrow
     */
    private DoubleProperty length;
    public Double getLength() {
        return lengthProperty().get();
    }
    public DoubleProperty lengthProperty() {
        if (length == null) {
            length = new SimpleDoubleProperty(this, "length", 0.0);
        }
        return length;
    }
    public void setLength(double length) {
        lengthProperty().set(length);
    }

    /**
     * The arrow fill
     */
    private ObjectProperty<Paint> fill;
    public Paint getFill() {
        return fillProperty().get();
    }
    public ObjectProperty<Paint> fillProperty() {
        if (fill == null) {
            fill = new SimpleObjectProperty<>(this, "fill", Color.BLACK);
        }
        return fill;
    }
    public void setFill(Paint fill) {
        fillProperty().set(fill);
    }

    /**
     * The base line
     */
    private ObjectProperty<Line> baseLine = new SimpleObjectProperty<>(this, "baseLine", null);
    public Line getBaseLine() {
        return baseLine.get();
    }
    public ObjectProperty<Line> baseLineProperty() {
        return baseLine;
    }
    public void setBaseLine(Line baseLine) {
        this.baseLine.set(baseLine);
    }

    /**
     * The wrapped edge
     */
    private ObjectProperty<EdgeShape> wrappedEdge = new SimpleObjectProperty<>(this, "wrappedEdge", null);
    public EdgeShape getWrappedEdge() {
        return wrappedEdge.get();
    }
    public ObjectProperty<EdgeShape> wrappedEdgeProperty() {
        return wrappedEdge;
    }
    public void setWrappedEdge(EdgeShape wrappedEdge) {
        this.wrappedEdge.set(wrappedEdge);
    }
}
