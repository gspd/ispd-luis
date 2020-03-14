package gspd.ispd.fxgui.commons;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.util.Builder;

public abstract class NodeIcon extends Icon {

    public final static IconType NODE_TYPE = IconType.type(ICON_TYPE, "NODE_TYPE");

    //////////////////////////////////////////////
    ///////////// CONSTRUCTOR ////////////////////
    //////////////////////////////////////////////

    public NodeIcon(Builder<? extends Node> nodeBuilder, boolean selected, double centerX, double centerY) {
        super(nodeBuilder, selected);
        // LISTENERS
        centerXProperty().addListener(this::positionChanged);
        centerYProperty().addListener(this::positionChanged);
        // SETs
        setCenterX(centerX);
        setCenterY(centerY);

        setIconType(NODE_TYPE);
    }

    public NodeIcon(Builder<? extends Node> nodeBuilder, double centerX, double centerY) {
        this(nodeBuilder, false, centerX, centerY);
    }

    public NodeIcon(Builder<? extends Node> nodeBuilder, boolean selected) {
        this(nodeBuilder, selected, 0.0, 0.0);
    }

    public NodeIcon(Builder<? extends Node> nodeBuilder) {
        this(nodeBuilder, false, 0.0, 0.0);
    }

    ////////////////////////////////////////////////
    /////////////// EVENT HANDLERS /////////////////
    ////////////////////////////////////////////////

    private void positionChanged(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
        if (observable == centerXProperty()) {
            setLayoutX(newValue.doubleValue() - getContent().getBoundsInLocal().getCenterX());
        } else if (observable == centerYProperty()) {
            setLayoutY(newValue.doubleValue() - getContent().getBoundsInLocal().getCenterY());
        }
    }

    private double dragX;
    private double dragY;
    private void dragDetected(MouseEvent event) {
        dragX = event.getX();
        dragY = event.getY();
        event.consume();
    }

    protected void mouseDragged(MouseEvent event) {
        double dx = event.getX() - dragX;
        double dy = event.getY() - dragY;
        setCenterX(getCenterX() + dx);
        setCenterY(getCenterY() + dy);
        event.consume();
    }

    ///////////////////////////////////////////////////
    //////////////// OVERRIDES ////////////////////////
    ///////////////////////////////////////////////////


    public Builder<? extends Icon> iconBuilder() {
        return null;
    }

    private Rectangle selection;
    private static final Lighting lighting;
    static {
        Light.Distant light = new Light.Distant();
        light.setAzimuth(-135);
        lighting = new Lighting(light);
        lighting.setSurfaceScale(5);
    }

    @Override
    protected void updateIcon() {
        if (isSelected()) {
            selection = new Rectangle();
            selection.setWidth(getContent().getLayoutBounds().getWidth() + 2.0);
            selection.setHeight(getContent().getLayoutBounds().getHeight() + 2.0);
            selection.setX(getContent().getLayoutBounds().getMinX() - 1.0);
            selection.setY(getContent().getLayoutBounds().getMinY() - 1.0);
            selection.setFill(null);
            selection.setStroke(Color.LIGHTGREEN);
            selection.setStrokeWidth(2.0);
            getContent().setEffect(lighting);
            super.getChildren().add(selection);
        } else {
            getContent().setEffect(null);
            super.getChildren().remove(selection);
            selection = null;
        }
    }

    ///////////////////////////////////////////////////
    //////////////// PROPERTIES ///////////////////////
    ///////////////////////////////////////////////////

    /**
     * The X center of the node
     */
    private DoubleProperty centerX = new SimpleDoubleProperty(this, "centerX", 0.0);
    public double getCenterX() {
        return centerX.get();
    }
    public DoubleProperty centerXProperty() {
        return centerX;
    }
    public void setCenterX(double centerX) {
        this.centerX.set(centerX);
    }

    /**
     * The Y center of the node
     */
    private DoubleProperty centerY = new SimpleDoubleProperty(this, "centerY", 0.0);
    public double getCenterY() {
        return centerY.get();
    }
    public DoubleProperty centerYProperty() {
        return centerY;
    }
    public void setCenterY(double centerY) {
        this.centerY.set(centerY);
    }
}
