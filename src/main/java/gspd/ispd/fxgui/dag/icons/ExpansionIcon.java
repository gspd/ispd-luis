package gspd.ispd.fxgui.dag.icons;

import gspd.ispd.fxgui.commons.IconType;
import gspd.ispd.fxgui.commons.NodeIcon;
import gspd.ispd.fxgui.commons.Diagram;
import gspd.ispd.fxgui.commons.OuterBox;
import javafx.beans.Observable;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.paint.Color;
import javafx.util.Builder;

public abstract class ExpansionIcon extends NodeIcon {

    public static final IconType EXPANSION_TYPE = IconType.type(NODE_TYPE, "EXPANSION_TYPE");

    //////////////////////////////////////////////////
    ////////////////// CONSTRUCTOR ///////////////////
    //////////////////////////////////////////////////

    public ExpansionIcon(Builder<? extends OuterBox> boxBuilder, Diagram diagram, boolean selected, double centerX, double centerY) {
        super(boxBuilder, selected, centerX, centerY);
        diagramProperty().addListener(this::contentDagChanged);
        setDiagram(diagram);

        setIconType(EXPANSION_TYPE);
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

}
