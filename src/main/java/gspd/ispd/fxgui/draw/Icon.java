package gspd.ispd.fxgui.draw;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Node;

public abstract class Icon extends Node {

    private DoubleProperty offsetX = new SimpleDoubleProperty();
    private DoubleProperty offsetY = new SimpleDoubleProperty();

    public Icon() {
        init();
    }

    private void init() {
        setOnMousePressed(event -> {
            setOffsetX(event.getX());
            setOffsetY(event.getY());
        });
        setOnMouseDragged(event -> {
            setTranslateX(event.getSceneX() - getOffsetX());
            setTranslateY(event.getSceneY() - getOffsetY());
        });
    }

    public double getOffsetX() {
        return offsetX.get();
    }

    public DoubleProperty offsetXProperty() {
        return offsetX;
    }

    public void setOffsetX(double offsetX) {
        this.offsetX.set(offsetX);
    }

    public double getOffsetY() {
        return offsetY.get();
    }

    public DoubleProperty offsetYProperty() {
        return offsetY;
    }

    public void setOffsetY(double offsetY) {
        this.offsetY.set(offsetY);
    }
}
