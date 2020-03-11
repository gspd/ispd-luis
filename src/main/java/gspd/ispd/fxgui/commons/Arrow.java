package gspd.ispd.fxgui.commons;

import javafx.beans.property.*;
import javafx.beans.value.ObservableValue;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.*;
import javafx.util.Builder;

public class Arrow extends Group {

    ///////////////////////////////////////////////
    ///////////////// CONSTRUCTOR /////////////////
    ///////////////////////////////////////////////

    public Arrow(Line baseLine) {
        lengthProperty().addListener(this::lengthChanged);
        baseLineProperty().addListener(this::baseLineChanged);
        fillProperty().addListener(this::fillChanged);
        setBaseLine(baseLine);
    }

    public Arrow() {
        this(null);
    }

    ////////////////////////////////////////////////
    /////////////// EVENT HANDLERS /////////////////
    ////////////////////////////////////////////////

    private void lengthChanged(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
        updateLength();
    }

    protected void updateLength() {
        getBaseLine().setEndX(getLength());
    }

    private void baseLineChanged(ObservableValue<? extends Line> observable, Line oldValue, Line newValue) {
        super.getChildren().remove(oldValue);
        super.getChildren().add(newValue);
    }

    private void fillChanged(ObservableValue<? extends Paint> observable, Paint oldValue, Paint newValue) {
        updateFill();
    }

    protected void updateFill() {
        getBaseLine().setStroke(Color.GREEN);
    }


    ////////////////////////////////////////////////
    ////////////// STATIC BUILDERS /////////////////
    ////////////////////////////////////////////////

    public static final Builder<Arrow> PRECEDENCE_BUILDER = new Builder<Arrow>() {
        @Override
        public Arrow build() {
            Line baseLine = new Line();
            Arrow arrow = new Arrow(baseLine);
            return new PointedArrow(arrow);
        }
    };
    public static final Builder<Arrow> PREFIX_BUILDER = new Builder<Arrow>() {
        @Override
        public Arrow build() {
            Line baseLine = new Line();
            baseLine.getStrokeDashArray().setAll(8.0, 5.0);
            Arrow arrow = new Arrow(baseLine);
            arrow = new PointedArrow(arrow);
            return arrow;
        }
    };
    public static final Builder<Arrow> FAIL_BUILDER = new Builder<Arrow>() {
        @Override
        public Arrow build() {
            Line baseLine = new Line();
            Arrow arrow = new Arrow(baseLine);
            arrow = new PointedArrow(arrow);
            arrow = new XedArrow(arrow);
            return arrow;
        }
    };

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
     * The base line
     */
    private ObjectProperty<Line> baseLine;
    public Line getBaseLine() {
        return baseLineProperty().get();
    }
    public ObjectProperty<Line> baseLineProperty() {
        if (baseLine == null) {
            baseLine = new SimpleObjectProperty<>(this, "baseLine", null);
        }
        return baseLine;
    }
    public void setBaseLine(Line baseLine) {
        baseLineProperty().set(baseLine);
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
}
