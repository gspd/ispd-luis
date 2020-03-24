package gspd.ispd.fxgui.workload.dag.icons;

import gspd.ispd.commons.ISPDType;
import gspd.ispd.fxgui.commons.*;
import gspd.ispd.fxgui.workload.dag.editor.ExpansionEditor;
import javafx.beans.Observable;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.paint.Color;
import javafx.util.Builder;

public abstract class ExpansionIcon extends NodeIcon {

    public static final ISPDType EXPANSION_TYPE = ISPDType.type(NODE_TYPE, "EXPANSION_TYPE");

    //////////////////////////////////////////////////
    ////////////////// CONSTRUCTOR ///////////////////
    //////////////////////////////////////////////////

    public ExpansionIcon(Builder<? extends OuterBox> boxBuilder, Diagram diagram, boolean selected, double centerX, double centerY) {
        super(boxBuilder, selected, centerX, centerY);
        diagramProperty().addListener(this::contentDagChanged);
        setDiagram(diagram);

        setType(EXPANSION_TYPE);
    }

    //////////////////////////////////////////////////
    //////////////// EVENT HANDLERS //////////////////
    //////////////////////////////////////////////////

    private void contentDagChanged(ObservableValue<? extends Diagram> observable, Diagram oldValue, Diagram newValue) {
        OuterBox box = (OuterBox) getContent();
        box.getChildren().remove(oldValue);
        box.getChildren().add(newValue);
        if (newValue != null) {
            if (oldValue != newValue) {
                box.boundsInLocalProperty().addListener(this::boundsChanged);
            }
            box.setWidth(newValue.getLayoutBounds().getWidth() + 10.0);
            box.setHeight(newValue.getLayoutBounds().getHeight() + 20.0);
            newValue.relocate(5.0, 15.0);
            setCenterX(getLayoutX() + box.getBoundsInLocal().getCenterX());
            setCenterY(getLayoutY() + box.getBoundsInLocal().getCenterY());
        } else {
            box.setWidth(20.0);
            box.setHeight(20.0);
        }
    }

    private void boundsChanged(Observable observable) {
        contentDagChanged(diagramProperty(), getDiagram(), getDiagram());
    }

    ////////////////////////////////////////
    ///////////// OVERRIDES ////////////////
    ////////////////////////////////////////

    private static final ExpansionEditor EXPANSION_EDITOR = new ExpansionEditor();
    @Override
    protected IconEditor editor() {
        EXPANSION_EDITOR.setIcon(this);
        return EXPANSION_EDITOR;
    }

    @Override
    protected void updateIcon() {
        OuterBox box = (OuterBox) getContent();
        if (isSelected()) {
            box.setFill(Color.LIGHTBLUE);
            box.setStroke(Color.DEEPSKYBLUE);
        } else {
            box.setFill(Color.WHITE);
            box.setStroke(Color.BLACK);
        }
    }


    //////////////////////////////////////////
    ////////////// PROPERTIES ////////////////
    //////////////////////////////////////////

    /**
     * The inner diagram
     */
    private ObjectProperty<Diagram> diagram;
    public Diagram getDiagram() {
        return (diagram == null) ? null : diagramProperty().get();
    }
    public ObjectProperty<Diagram> diagramProperty() {
        if (diagram == null) {
            diagram = new SimpleObjectProperty<>(this, "contentDag", null);
        }
        return diagram;
    }
    public void setDiagram(Diagram diagram) {
        diagramProperty().set(diagram);
    }

    /**
     * The expansion operation number
     */
    private IntegerProperty number = new SimpleIntegerProperty(this, "number", 0);
    public int getNumber() {
        return number.get();
    }
    public IntegerProperty numberProperty() {
        return number;
    }
    public void setNumber(int number) {
        this.number.set(number);
    }
}
