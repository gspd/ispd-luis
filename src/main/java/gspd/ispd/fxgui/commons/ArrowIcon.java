package gspd.ispd.fxgui.commons;

import javafx.beans.Observable;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.util.Builder;

import static java.lang.Math.*;

public class ArrowIcon extends Icon {

    ///////////////////////////////////////////////
    ///////////////// CONSTRUCTOR /////////////////
    ///////////////////////////////////////////////

    public ArrowIcon(Builder<? extends Arrow> arrowBuilder, boolean selected, double startX, double startY, double endX, double endY) {
        super(arrowBuilder, selected);
        startXProperty().addListener(this::positionChanged);
        startYProperty().addListener(this::positionChanged);
        endXProperty().addListener(this::positionChanged);
        endYProperty().addListener(this::positionChanged);
        setStartX(startX);
        setStartY(startY);
        setEndX(endX);
        setEndY(endY);
    }

    public ArrowIcon(Builder<? extends Arrow> arrowBuilder, double startX, double startY, double endX, double endY) {
        this(arrowBuilder, false, startX, startY, endX, endY);
    }

    public ArrowIcon(Builder<? extends Arrow> arrowBuilder) {
        this(arrowBuilder, false, 0.0, 0.0, 0.0, 0.0);
    }

    ////////////////////////////////////////////////
    /////////////// EVENT HANDLERS /////////////////
    ////////////////////////////////////////////////

    private void positionChanged(Observable observable) {
        double length = sqrt(pow(getEndX() - getStartX(), 2) + pow(getEndY() - getStartY(), 2));
        Arrow arrow = (Arrow)getContent();
        arrow.setLength(length);
        double angleSin = asin((getEndY() - getStartY()) / length);
        double angleCos = acos((getEndX() - getStartX()) / length);
        double angle;
        if (angleSin - angleSin < 0.01) {
            angle = angleCos;
        } else if (angleCos < PI / 2) {
            angle = angleSin;
        } else if (angleSin >= 0) {
            angle = angleCos;
        } else {
            angle = - angleSin - PI;
        }
        arrow.getTransforms().setAll(
            new Translate(getStartX(), getStartY(), 0),
            new Rotate(toDegrees(angle), 0.0, 0.0)
        );
    }

    @Override
    protected void updateSelection(boolean selected) {
        Arrow arrow = (Arrow) getContent();
        if (selected) {
            arrow.setFill(Color.GREEN);
        } else {
            arrow.setFill(Color.BLACK);
        }
    }

    ///////////////////////////////////////////////////
    //////////////// PROPERTIES ///////////////////////
    ///////////////////////////////////////////////////

    /**
     * The arrow start X
     */
    private DoubleProperty startX = new SimpleDoubleProperty(this, "startX", 0.0);
    public double getStartX() {
        return startX.get();
    }
    public DoubleProperty startXProperty() {
        return startX;
    }
    public void setStartX(double startX) {
        this.startX.set(startX);
    }

    /**
     * The arrow start Y
     */
    private DoubleProperty startY = new SimpleDoubleProperty(this, "startY", 0.0);
    public double getStartY() {
        return startY.get();
    }
    public DoubleProperty startYProperty() {
        return startY;
    }
    public void setStartY(double startY) {
        this.startY.set(startY);
    }

    /**
     * The arrow end X
     */
    private DoubleProperty endX = new SimpleDoubleProperty(this, "endX", 0.0);
    public double getEndX() {
        return endX.get();
    }
    public DoubleProperty endXProperty() {
        return endX;
    }
    public void setEndX(double endX) {
        this.endX.set(endX);
    }

    /**
     * The arrow end Y
     */
    private DoubleProperty endY = new SimpleDoubleProperty(this, "endY", 0.0);
    public double getEndY() {
        return endY.get();
    }
    public DoubleProperty endYProperty() {
        return endY;
    }
    public void setEndY(double endY) {
        this.endY.set(endY);
    }

}
