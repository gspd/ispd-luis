package gspd.ispd.fxgui;

import javafx.beans.binding.Bindings;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.effect.BlendMode;
import javafx.scene.input.*;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.*;

public class DrawPane extends Pane {

    enum DrawMode {
        NONE,
        SELECTING,
        MOVING
    }

    /**
     * Selection Model automatic takes care of the selecting system.
     * In this case, see {@link SelectionModel}, in order to
     * understand how the selection box works
     */
    private SelectionModel selectionModel;
    private DrawMode mode;

    public DrawPane() {
        selectionModel = new SelectionModel(this);
        mode = DrawMode.NONE;
        setCursor(Cursor.CROSSHAIR);
        setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
    }

    public void enableGrid() {
        getStyleClass().add("grid");
    }

    public void disableGrid() {
        getStyleClass().remove("grid");
    }

    /**
     * Adds a node to the pane.
     *
     * @param node the node
     * @param xCenter the center X position
     * @param yCenter the center Y position
     */
    public void add(Node node, double xCenter, double yCenter) {
        double halfWidth = node.getBoundsInLocal().getWidth() / 2;
        double halfHeight = node.getBoundsInLocal().getHeight() / 2;
        node.relocate(xCenter - halfWidth, yCenter - halfHeight);
        add(node);
    }

    public void add(Node node) {
        getChildren().add(node);
        GUI.makeDraggable(node);
    }

    /**
     * @return The selection model
     */
    public SelectionModel getSelectionModel() {
        return selectionModel;
    }

    /**
     * Specifies the selection model engine for iSPD {@link DrawPane}. The way the selected
     * items interacts with the selection shape (rectangle).
     */
    public static class SelectionModel {
        /**
         * Associates each selected node with a rectangle box that
         * strokes that node
         */
        private Set<Node> selected;
        private DrawPane pane;
        private Rectangle selectionBox;
        private SelectionBoxContext context;
        private Point2D startPoint;
        // Mouse event handlers. They are responsible for updating selections and
        // updating the selection rectangle
        private EventHandler<MouseEvent> mousePressedHandler = event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                context.setStartX(event.getX());
                context.setStartY(event.getY());
                context.setEndX(event.getX());
                context.setEndY(event.getY());
                updateSelectionRectangle();
                pane.getChildren().add(selectionBox);
                event.consume();
            }
        };
        private EventHandler<MouseEvent> mouseDraggedHandler = event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                context.setEndX(event.getX());
                context.setEndY(event.getY());
                updateSelectionRectangle();
                event.consume();
            }
        };
        private EventHandler<MouseEvent> mouseReleasedHandler = event -> {
            pane.getChildren().remove(selectionBox);
            if (event.getButton() == MouseButton.PRIMARY) {
                clearAndUpdateSelectedItems();
                event.consume();
            }
        };

        /**
         * Selection model constructor
         * @param pane the DrawPane the selector belongs to
         */
        public SelectionModel(DrawPane pane) {
            super();
            this.selected = new HashSet<>();
            this.pane = pane;
            selectionBox = new Rectangle(0, 0, 0, 0);
            selectionBox.getStyleClass().add("selection-box");
            context = new SelectionBoxContext();
            pane.addEventHandler(MouseEvent.MOUSE_PRESSED, mousePressedHandler);
            pane.addEventHandler(MouseEvent.MOUSE_DRAGGED, mouseDraggedHandler);
            pane.addEventHandler(MouseEvent.MOUSE_RELEASED, mouseReleasedHandler);
        }

        public Set<Node> getSelectedItems() {
            return selected;
        }

        public void selectAll() {
            pane.getChildren().forEach(this::select);
        }

        public void select(Node node) {
            selected.add(node);
            node.setBlendMode(BlendMode.DIFFERENCE);
        }

        public void clearSelection(Node node) {
            selected.remove(node);
            node.setBlendMode(BlendMode.SRC_OVER);
        }

        public void clearSelection() {
            // do not use for/foreach , they cause ConcurrentModificationException
            while (!isEmpty()) {
                clearSelection(selected.iterator().next());
            }
        }

        public boolean isSelected(Node node) {
            return selected.contains(node);
        }

        public boolean isEmpty() {
            return selected.isEmpty();
        }

        private List<Node> getNodesInPoint(double x, double y) {
            List<Node> nodesList = new ArrayList<>();
            for (Node node : pane.getChildren()) {
                if (node.getBoundsInLocal().contains(new Point2D(x, y))) {
                    nodesList.add(node);
                }
            }
            return nodesList;
        }

        private void clearAndUpdateSelectedItems() {
            clearSelection();
            updateSelectedItems();
        }

        private void updateSelectedItems() {
            if (context.getType() == SelectionType.TOTAL) {
                for (Node node : pane.getChildren()) {
                    if (selectionBox.getBoundsInParent().contains(node.getBoundsInParent())) {
                        select(node);
                    }
                }
            } else if (context.getType() == SelectionType.PARTIAL) {
                for (Node node : pane.getChildren()) {
                    if (node.getBoundsInParent().intersects(selectionBox.getBoundsInParent())) {
                        select(node);
                    }
                }
            } else if (context.getType() == SelectionType.CLICK) {
                for (Node node : pane.getChildren()) {
                    if (node.getBoundsInParent().contains(context.getStartX(), context.startY)) {
                        select(node);
                        break;
                    }
                }
            }
        }

        private void updateSelectionRectangle() {
            selectionBox.setX(context.getStartX());
            selectionBox.setY(context.getStartY());
            selectionBox.setWidth(context.getWidth());
            selectionBox.setHeight(context.getHeight());
            if (context.getType() == SelectionType.TOTAL) {
                selectionBox.setStroke(Color.BLUE);
                selectionBox.setFill(Color.LIGHTBLUE.deriveColor(0, 1, 10, 0.5));
                selectionBox.getStrokeDashArray().clear();
            } else {
                selectionBox.setStroke(Color.BLACK);
                selectionBox.setFill(Color.LIGHTGREEN.deriveColor(0, 1, 10, 0.5));
                selectionBox.getStrokeDashArray().addAll(3.0, 7.0);
            }
        }

        /**
         * Selection Context automatic make correct changes to start and end
         * positions of the selection rectangle in order to maintain its correctness
         */
        private class SelectionBoxContext {
            private double startX;
            private double startY;
            private double endX;
            private double endY;
            private SelectionType type;

            public double getStartX() {
                return Math.min(endX, startX);
            }

            public void setStartX(double startX) {
                this.startX = startX;
            }

            public double getStartY() {
                return Math.min(endY, startY);
            }

            public void setStartY(double startY) {
                this.startY = startY;
            }

            public double getWidth() {
                return Math.abs(endX - startX);
            }

            public void setEndX(double endX) {
                this.endX = endX;
            }

            public double getHeight() {
                return Math.abs(endY - startY);
            }

            public void setEndY(double endY) {
                this.endY = endY;
            }

            public SelectionType getType() {
                if (endX > startX)
                    return SelectionType.TOTAL;
                else if (endX < startX)
                    return SelectionType.PARTIAL;
                else
                    return SelectionType.CLICK;
            }
        }

        /**
         * The selection can be of two types:
         * - CLICK: the selection box has not been made (it was a click)
         * - PARTIAL: all the nodes selection box touches are selected
         * - TOTAL: all the nodes selection box contains are selected
         */
        private enum SelectionType {
            CLICK,
            PARTIAL,
            TOTAL
        }
    }
}