package gspd.ispd.fxgui.commons;

import gspd.ispd.util.Handler;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


public class DiagramSelectionModel {

    //////////////////////////////////////
    //////////// CONSTRUCTOR /////////////
    //////////////////////////////////////

    private DiagramPane diagramPane;
    public DiagramSelectionModel(DiagramPane diagramPane) {
        this.diagramPane = diagramPane;
    }

    ///////////////////////////////////////
    ////////////// METHODS ////////////////
    ///////////////////////////////////////

    public boolean select(Icon icon) {
        if (diagramPane.has(icon)) {
            if (!isSelected(icon)) {
                selectedIcons.add(icon);
                icon.setSelected(true);
                return true;
            }
        }
        return false;
    }

    public boolean isSelected(Icon icon) {
        return selectedIcons.contains(icon);
    }

    public boolean isEmpty() {
        return selectedIcons.isEmpty();
    }

    public boolean unselect(Icon icon) {
        if (isSelected(icon)) {
            selectedIcons.remove(icon);
            icon.setSelected(false);
            return true;
        }
        return false;
    }

    public boolean toggle(Icon icon) {
        if (isSelected(icon)) {
            return unselect(icon);
        } else {
            return select(icon);
        }
    }

    public boolean clear() {
        try {
            while (!selectedIcons.isEmpty()) {
                unselect(selectedIcons.iterator().next());
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean clearAndSelect(Icon icon) {
        if (clear()) {
            return select(icon);
        }
        return false;
    }

    public void translateSelectedIcons(double x, double y) {
        getSelectedIcons()
            .stream()
            .filter(icon -> icon.getType().isTypeOf(NodeIcon.NODE_TYPE))
            .forEach(icon -> {
                NodeIcon ni = (NodeIcon) icon;
                ni.setCenterX(ni.getCenterX() + x);
                ni.setCenterY(ni.getCenterY() + y);
            });
    }

    /**
     * The box used to select
     */
    private SelectionRectangle box = new SelectionRectangle();
    private EventHandler<MouseEvent> selectingMouseReleased = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            stopSelecting();
        }
    };
    public void startSelecting(MouseEvent event) {
        box.startX.set(event.getX());
        box.startY.set(event.getY());
        box.endX.set(event.getX());
        box.endY.set(event.getY());
        diagramPane.getChildren().add(box);
        diagramPane.startFullDrag();
        diagramPane.setOnMouseDragOver(e -> {
            box.endX.set(e.getX());
            box.endY.set(e.getY());
        });
        diagramPane.setOnMouseDragReleased(e -> stopSelecting());
        diagramPane.addEventHandler(MouseEvent.MOUSE_RELEASED, selectingMouseReleased);
    }

    private void stopSelecting() {
        diagramPane.getChildren().remove(box);
        diagramPane.removeEventHandler(MouseEvent.MOUSE_RELEASED, selectingMouseReleased);
        // selects the nodes
        diagramPane.setOnMouseDragOver(null);
        diagramPane.setOnMouseDragReleased(null);
    }

    private static class SelectionRectangle extends Rectangle {

        SelectionRectangle() {
            layoutXProperty().bind(Bindings.min(startX, endX));
            layoutYProperty().bind(Bindings.min(startY, endY));
            widthProperty().bind(Bindings.createDoubleBinding(() -> Math.abs(endX.get() - startX.get()), startX, endX));
            heightProperty().bind(Bindings.createDoubleBinding(() -> Math.abs(endY.get() - startY.get()), startY, endY));
            mode.bind(Bindings.when(endX.greaterThan(startX)).then(CONTAINS).otherwise(INTERSECTS));
            setOpacity(0.6);
            mode.addListener((obs, o, n) -> {
                if (n.intValue() == CONTAINS) {
                    setFill(Color.LIGHTBLUE);
                    setStroke(Color.BLUE);
                    getStrokeDashArray().clear();
                } else if (n.intValue() == INTERSECTS) {
                    setFill(Color.LIGHTGREEN);
                    setStroke(Color.BLACK);
                    getStrokeDashArray().setAll(8.0, 5.0, 8.0, 5.0);
                }
            });
        }

        DoubleProperty startX = new SimpleDoubleProperty(0.0);
        DoubleProperty startY = new SimpleDoubleProperty(0.0);
        DoubleProperty endX = new SimpleDoubleProperty(0.0);
        DoubleProperty endY = new SimpleDoubleProperty(0.0);

        static final int CONTAINS = 0;
        static final int INTERSECTS = 1;
        IntegerProperty mode = new SimpleIntegerProperty(0);

    }

    //////////////////////////////////////////////////
    ///////////////// PROPERTIES /////////////////////
    //////////////////////////////////////////////////

    /**
     * The selected icons so far
     */
    private ObservableList<Icon> selectedIcons = FXCollections.observableArrayList();
    public ObservableList<Icon> getSelectedIcons() {
        return selectedIcons;
    }

    public Icon getSelectedIcon() {
        Icon active = null;
        if (getSelectedIcons().size() > 0) {
            active = getSelectedIcons().get(selectedIcons.size() - 1);
        }
        return active;
    }

}
