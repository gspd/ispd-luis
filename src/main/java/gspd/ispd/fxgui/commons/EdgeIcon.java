package gspd.ispd.fxgui.commons;

import gspd.ispd.fxgui.dag.editor.EdgeEditor;
import javafx.beans.Observable;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.util.Builder;

import static java.lang.Math.*;

public abstract class EdgeIcon extends Icon {

    public static final IconType EDGE_TYPE = IconType.type(ICON_TYPE, "ARROW_TYPE");

    ///////////////////////////////////////////////
    ///////////////// CONSTRUCTOR /////////////////
    ///////////////////////////////////////////////

    public EdgeIcon(Builder<? extends EdgeShape> arrowBuilder, boolean selected, double startX, double startY, double endX, double endY) {
        super(arrowBuilder, selected);
        // LISTENERS
        startXProperty().addListener(this::positionChanged);
        startYProperty().addListener(this::positionChanged);
        endXProperty().addListener(this::positionChanged);
        endYProperty().addListener(this::positionChanged);
        startIconProperty().addListener(this::startIconChanged);
        endIconProperty().addListener(this::endIconChanged);
        // SETs
        setStartX(startX);
        setStartY(startY);
        setEndX(endX);
        setEndY(endY);
        // TYPE
        setType(EDGE_TYPE);
    }

    public EdgeIcon(Builder<? extends EdgeShape> arrowBuilder, double startX, double startY, double endX, double endY) {
        this(arrowBuilder, false, startX, startY, endX, endY);
    }

    public EdgeIcon(Builder<? extends EdgeShape> arrowBuilder) {
        this(arrowBuilder, false, 0.0, 0.0, 0.0, 0.0);
    }

    public EdgeIcon(Builder<? extends EdgeShape> arrowBuilder, boolean selected, NodeIcon startIcon, NodeIcon endIcon) {
        this(arrowBuilder, selected, startIcon.getCenterX(), startIcon.getCenterY(), endIcon.getCenterX(), endIcon.getCenterY());
        setStartIcon(startIcon);
        setEndIcon(endIcon);
    }

    public EdgeIcon(Builder<? extends EdgeShape> arrowBuilder, NodeIcon startIcon, NodeIcon endIcon) {
        this(arrowBuilder, false, startIcon, endIcon);
    }

    ////////////////////////////////////////////////
    /////////////// EVENT HANDLERS /////////////////
    ////////////////////////////////////////////////

    private void positionChanged(Observable observable) {
        double length = sqrt(pow(getEndX() - getStartX(), 2) + pow(getEndY() - getStartY(), 2));
        EdgeShape edge = (EdgeShape)getContent();
        edge.setLength(length);
        double angleSin = asin((getEndY() - getStartY()) / length);
        double angleCos = acos((getEndX() - getStartX()) / length);
        double angle;
        if (angleSin == angleCos) {
            angle = angleCos;
        } else if (angleCos < PI / 2) {
            angle = angleSin;
        } else if (angleSin >= 0) {
            angle = angleCos;
        } else {
            angle = - angleSin - PI;
        }
        edge.getTransforms().setAll(
            new Translate(getStartX(), getStartY(), 0),
            new Rotate(toDegrees(angle), 0.0, 0.0)
        );
    }

    private void startIconChanged(ObservableValue<? extends NodeIcon> observable, NodeIcon oldValue, NodeIcon newValue) {
        if (newValue == null) {
            if (oldValue != null) {
                startXProperty().unbindBidirectional(oldValue.centerXProperty());
                startYProperty().unbindBidirectional(oldValue.centerYProperty());
            }
        } else {
            startXProperty().bindBidirectional(newValue.centerXProperty());
            startYProperty().bindBidirectional(newValue.centerYProperty());
        }
    }

    private void endIconChanged(ObservableValue<? extends NodeIcon> observable, NodeIcon oldValue, NodeIcon newValue) {
        if (newValue == null) {
            endXProperty().unbind();
            endYProperty().unbind();
        } else {
            endXProperty().bind(newValue.centerXProperty());
            endYProperty().bind(newValue.centerYProperty());
        }
    }

    ////////////////////////////////////////////////////////
    /////////////////// OVERRIDE ///////////////////////////
    ////////////////////////////////////////////////////////

    private static final EdgeEditor EDGE_EDITOR = new EdgeEditor();
    @Override
    protected IconEditor editor() {
        EDGE_EDITOR.setIcon(this);
        return EDGE_EDITOR;
    }

    @Override
    public Builder<? extends Icon> iconBuilder() {
        return null;
    }

    @Override
    protected void updateIcon() {
        EdgeShape edge = (EdgeShape) getContent();
        if (isSelected()) {
            edge.setFill(Color.BLUE);
        } else {
            edge.setFill(Color.BLACK);
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

    /**
     * The node icon that arrow comes from
     */
    private ObjectProperty<NodeIcon> startIcon;
    public NodeIcon getStartIcon() {
        return startIconProperty().get();
    }
    public ObjectProperty<NodeIcon> startIconProperty() {
        if (startIcon == null) {
            startIcon = new SimpleObjectProperty<>(this, "startIcon", null);
        }
        return startIcon;
    }
    public void setStartIcon(NodeIcon startIcon) {
        startIconProperty().set(startIcon);
    }

    /**
     * The end icon that the arrow points to
     */
    private ObjectProperty<NodeIcon> endIcon;
    public NodeIcon getEndIcon() {
        return endIconProperty().get();
    }
    public ObjectProperty<NodeIcon> endIconProperty() {
        if (endIcon == null) {
            endIcon = new SimpleObjectProperty<>(this, "endIcon", null);
        }
        return endIcon;
    }
    public void setEndIcon(NodeIcon endIcon) {
        endIconProperty().set(endIcon);
    }

    /**
     * The message size
     */
    private DoubleProperty messageSize = new SimpleDoubleProperty(this, "messageSize", 0.0);
    public double getMessageSize() {
        return messageSize.get();
    }
    public DoubleProperty messageSizeProperty() {
        return messageSize;
    }
    public void setMessageSize(double messageSize) {
        this.messageSize.set(messageSize);
    }
}
