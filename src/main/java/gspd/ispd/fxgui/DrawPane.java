package gspd.ispd.fxgui;

import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.effect.BlendMode;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.*;

public class DrawPane extends Pane {

    /**
     * Selection Model automatic takes care of the selecting system.
     * In this case, see {@link SelectionModel}, in order to
     * understand how the selection box works
     */
    private SelectionModel selectionModel;

    public DrawPane() {
        // initializes the selection model and passes 'this' in order to
        // grant access to the nodes children to selection model
        selectionModel = new SelectionModel(this);
        setCursor(Cursor.CROSSHAIR);
        setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
    }

    /**
     * Adds a node to the pane, with its center located at given positions
     *
     * @param node the node to add
     * @param xCenter the center X position
     * @param yCenter the center Y position
     */
    public void add(Node node, double xCenter, double yCenter) {
        double halfWidth = node.getBoundsInLocal().getWidth() / 2;
        double halfHeight = node.getBoundsInLocal().getHeight() / 2;
        node.relocate(xCenter - halfWidth, yCenter - halfHeight);
        add(node);
    }

    /**
     * Adds a node to the drawing pane itself
     * @param node the node no add
     */
    public void add(Node node) {
        getChildren().add(node);
    }

    /**
     * @return The selection model
     */
    public SelectionModel getSelectionModel() {
        return selectionModel;
    }

    /**
     * Specifies the selection model engine for iSPD {@link DrawPane}. The way the selected
     * items interacts with the selection shape (rectangle). And also how the selected items
     * can be moved inside the drawing pane.
     */
    public static class SelectionModel {
        /**
         * Group that contains selected nodes
         */
        private Set<Node> selected;
        /**
         * The pane which this selection model refers.
         * Needed to access pane children
         */
        private DrawPane pane;
        /**
         * The selection box rectangle
         */
        private Rectangle selectionBox;
        /**
         * Relevant information about selection box
         */
        private SelectionBoxContext boxContext;
        /**
         * Relevant information about dragging the selection
         */
        private DragContext dragContext;
        // Mouse event handlers. They are responsible for updating selections,
        // updating the selection rectangle and updating items position. The
        // 'box*' handlers refers to handling the selection box, while the 'group*'
        // handlers takes care of positioning the selected items.
        private EventHandler<MouseEvent> boxMousePressedHandler = event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                boxContext.setStartX(event.getX());
                boxContext.setStartY(event.getY());
                boxContext.setEndX(event.getX());
                boxContext.setEndY(event.getY());
                updateSelectionRectangle();
                pane.getChildren().add(selectionBox);
                event.consume();
            }
        };
        private EventHandler<MouseEvent> boxMouseDraggedHandler = event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                boxContext.setEndX(event.getX());
                boxContext.setEndY(event.getY());
                updateSelectionRectangle();
                event.consume();
            }
        };
        private EventHandler<MouseEvent> boxMouseReleasedHandler = event -> {
            pane.getChildren().remove(selectionBox);
            if (event.getButton() == MouseButton.PRIMARY) {
                clearAndUpdateSelectedItems();
                event.consume();
            }
        };
        private EventHandler<MouseEvent> groupMousePressedHandler = event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                dragContext.setStartX(event.getX());
                dragContext.setStartY(event.getY());
                dragContext.setEndX(event.getX());
                dragContext.setEndY(event.getY());
                updateSelectedItemsPosition();
                event.consume();
            }
        };
        private EventHandler<MouseEvent> groupMouseDraggedHandler = event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                dragContext.setEndX(event.getX());
                dragContext.setEndY(event.getY());
                updateSelectedItemsPosition();
                event.consume();
            }
        };
        private EventHandler<MouseEvent> groupMouseReleasedHandler = event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                event.consume();
            }
        };

        /**
         * Selection model constructor
         * @param pane the drawing pane the selection model belongs to
         */
        public SelectionModel(DrawPane pane) {
            super();
            this.pane = pane;
            selected = new HashSet<>();
            selectionBox = new Rectangle(0, 0, 0, 0);
            boxContext = new SelectionBoxContext();
            dragContext = new DragContext();
            pane.addEventHandler(MouseEvent.MOUSE_PRESSED, boxMousePressedHandler);
            pane.addEventHandler(MouseEvent.MOUSE_DRAGGED, boxMouseDraggedHandler);
            pane.addEventHandler(MouseEvent.MOUSE_RELEASED, boxMouseReleasedHandler);
        }


        /**
         * Returns a set with all selected nodes
         * @return a set with all selected nodes
         */
        public Set<Node> getSelectedItems() {
            return selected;
        }

        public void selectAll() {
            pane.getChildren().forEach(this::select);
        }

        /**
         * Select a given node in the drawing pane. If the node is not in drawing pane, an
         * {@link IllegalArgumentException} is thrown.
         *
         * @param node the node to select
         */
        public void select(Node node) {
            if (pane.getChildren().contains(node)) {
                selected.add(node);
                node.addEventHandler(MouseEvent.MOUSE_PRESSED, groupMousePressedHandler);
                node.addEventHandler(MouseEvent.MOUSE_DRAGGED, groupMouseDraggedHandler);
                node.addEventHandler(MouseEvent.MOUSE_RELEASED, groupMouseReleasedHandler);
                node.setBlendMode(BlendMode.DIFFERENCE);
            } else {
                throw new IllegalArgumentException("Node is not one of the " + DrawPane.class.getName() + " children");
            }
        }

        /**
         * Unselect a given node. If the node is not in drawing pane, an {@link IllegalArgumentException}
         * is thrown. If the node is in drawing pane but it is not selected, it remains unselected and nothing
         * happens.
         *
         * @param node the node to unselect
         */
        public void clearSelection(Node node) {
            if (pane.getChildren().contains(node)) {
                unselect(node);
            } else {
                throw new IllegalArgumentException("Node is not one od the " + DrawPane.class.getName() + " children");
            }
        }

        /**
         * Unselect a node without verifying if it is inside the drawing pane
         *
         * @param node the node to unselect
         */
        private void unselect(Node node) {
            selected.remove(node);
            node.removeEventHandler(MouseEvent.MOUSE_PRESSED, groupMousePressedHandler);
            node.removeEventHandler(MouseEvent.MOUSE_DRAGGED, groupMouseDraggedHandler);
            node.removeEventHandler(MouseEvent.MOUSE_RELEASED, groupMouseReleasedHandler);
            node.setBlendMode(BlendMode.SRC_OVER);
        }

        /**
         * Unselect all selected nodes
         */
        public void clearSelection() {
            while (!isEmpty()) {
                unselect(selected.iterator().next());
            }
        }

        /**
         * Clear the selection and selects a node. If the node is not in drawing pane, an
         * {@link IllegalArgumentException} is thrown.
         *
         * @param node the node to select
         */
        public void clearAndSelect(Node node) {
            clearSelection();
            select(node);
        }

        /**
         * Returns true if a node is selected, and false if the node is not selected or is not
         * in the drawing pane
         *
         * @param node the node to check
         * @return true if node is selected
         */
        public boolean isSelected(Node node) {
            return selected.contains(node);
        }

        /**
         * Checks if selection is empty
         *
         * @return true if selection is empty
         */
        public boolean isEmpty() {
            return selected.isEmpty();
        }

        private void clearAndUpdateSelectedItems() {
            clearSelection();
            updateSelectedItems();
        }

        /**
         * Selects all the items based on the selection box position and policy.
         *
         * @see SelectionPolicy
         */
        private void updateSelectedItems() {
            Set<Node> set = new HashSet<>();
            if (boxContext.getPolicy() == SelectionPolicy.CONTAINS) {
                for (Node node : pane.getChildren()) {
                    if (selectionBox.getBoundsInParent().contains(node.getBoundsInParent())) {
                        set.add(node);
                    }
                }
            } else if (boxContext.getPolicy() == SelectionPolicy.INTERSECTS) {
                for (Node node : pane.getChildren()) {
                    if (node.getBoundsInParent().intersects(selectionBox.getBoundsInParent())) {
                        set.add(node);
                    }
                }
            } else if (boxContext.getPolicy() == SelectionPolicy.SINGLE) {
                // The list is reversed in order to get the 'up-layers' first
                FXCollections.reverse(pane.getChildren());
                for (Node node : pane.getChildren()) {
                    if (node.getBoundsInParent().contains(boxContext.getStartX(), boxContext.getStartY())) {
                        set.add(node);
                        break;
                    }
                }
                // The list is then un-reversed
                FXCollections.reverse(pane.getChildren());
            }
            set.forEach(this::select);
        }

        private void updateSelectionRectangle() {
            selectionBox.setX(boxContext.getStartX());
            selectionBox.setY(boxContext.getStartY());
            selectionBox.setWidth(boxContext.getWidth());
            selectionBox.setHeight(boxContext.getHeight());
            if (boxContext.getPolicy() == SelectionPolicy.CONTAINS) {
                selectionBox.setStroke(Color.BLUE);
                selectionBox.setFill(Color.LIGHTBLUE.deriveColor(0, 1, 10, 0.5));
                selectionBox.getStrokeDashArray().clear();
            } else {
                selectionBox.setStroke(Color.BLACK);
                selectionBox.setFill(Color.LIGHTGREEN.deriveColor(0, 1, 10, 0.5));
                selectionBox.getStrokeDashArray().addAll(3.0, 7.0);
            }
        }

        private void updateSelectedItemsPosition() {
            selected.forEach(node -> {
                node.setLayoutX(Math.max(0, node.getLayoutX() + dragContext.getTranslateX()));
                node.setLayoutY(Math.max(0, node.getLayoutY() + dragContext.getTranslateY()));
            });
        }

        /**
         * Selection Context automatic make correct changes to start and end
         * positions of the selection rectangle in order to maintain its
         * correctness
         */
        private static class SelectionBoxContext {
            private double startX;
            private double startY;
            private double endX;
            private double endY;
            private double diffX;
            private double diffY;

            public double getStartX() {
                double sX = Math.min(endX, startX);
                if (sX < 0) {
                    diffX = sX;
                    sX = 0;
                } else {
                    diffX = 0;
                }
                return sX;
            }

            public void setStartX(double startX) {
                this.startX = startX;
            }

            public double getStartY() {
                double sY = Math.min(endY, startY);
                if (sY < 0) {
                    diffY = sY;
                    sY = 0;
                } else {
                    diffY = 0;
                }
                return sY;
            }

            public void setStartY(double startY) {
                this.startY = startY;
            }

            public double getWidth() {
                return Math.abs(endX - startX) + diffX;
            }

            public void setEndX(double endX) {
                this.endX = endX;
            }

            public double getHeight() {
                return Math.abs(endY - startY) +  diffY;
            }

            public void setEndY(double endY) {
                this.endY = endY;
            }

            public SelectionPolicy getPolicy() {
                if (endX > startX)
                    return SelectionPolicy.CONTAINS;
                else if (endX < startX)
                    return SelectionPolicy.INTERSECTS;
                else
                    return SelectionPolicy.SINGLE;
            }
        }

        /**
         * The selection policy can be one of the following:
         * <ul>
         *   <li>
         *     <b>SINGLE</b>: it will select only the most 'up layer' element in the
         *       pane. This is used when single clicked happens and no selection box
         *       was really formed
         *   </li>
         *   <li>
         *     <b>INTERSECTS</b>: it will selects all the elements that intersects
         *       the selection box
         *   </li>
         *   <li>
         *     <b>CONTAINS</b>: it will selects all the elements that is totally
         *       contained in the selection box
         *   </li>
         * </ul>
         */
        private enum SelectionPolicy {
            SINGLE,
            INTERSECTS,
            CONTAINS
        }

        /**
         * Drag context stores information about the position of the group
         * that contains the selected elements, and auto calculates other
         * useful information about the events itself
         */
        private static class DragContext {

            private double startX;
            private double startY;
            private double endX;
            private double endY;

            public void setStartY(double startY) {
                this.startY = startY;
            }

            public void setEndX(double endX) {
                this.endX = endX;
            }

            public void setEndY(double endY) {
                this.endY = endY;
            }

            public void setStartX(double startX) {
                this.startX = startX;
            }

            public double getTranslateX() {
                return endX - startX;
            }

            public double getTranslateY() {
                return endY - startY;
            }
        }
    }
}