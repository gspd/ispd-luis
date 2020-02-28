package gspd.ispd.fxgui;

import gspd.ispd.util.structures.FixedStack;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.Image;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.util.Builder;

import java.net.URL;
import java.util.*;
import java.util.concurrent.*;

/**
 * {@code DrawingPane} is a basic JavaFX pane that allows positioning, selecting,
 * adding, removing and undoing operations with simple nodes attached as its children
 *
 * @author luisbaldissera
 */
public class DrawPaneController implements Initializable {

    @FXML
    private Pane rootPane;

    /**
     * Selection Model automatic takes care of the selecting system.
     * In this case, see {@link SelectionModel}, in order to
     * understand how the selection box works
     */
    private SelectionModel selectionModel;
    /**
     * This stack stores drawing pane states, helping managing the
     * undo operations
     */
    private Stack<Snapshot> undoStack = new FixedStack<>(32);
    /**
     * True if grid enabled
     */
    private BooleanProperty gridEnable = new SimpleBooleanProperty();
    /**
     * The zoom scale of the draw pane
     */
    private DoubleProperty zoom = new SimpleDoubleProperty(1.0);
    /**
     * The blank background
     */
    private final static Background blankBackground = new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY));;
    /**
     * The grid background
     */
    private static Background gridBackground;

    private Set<Node> pinned = new HashSet<>();

    private EventHandler<MouseEvent> pinMouseMovedHandler = this::onPinMouseMoved;

    enum DrawState {
        NONE,
        SELECTING,
        MOVING,
    }

    private DrawState state;

    /**
     * Initializes the static fields of the class if they weren't yet
     */
    private static void initStatic() {
        if (gridBackground == null) {
            Rectangle rectangle = new Rectangle(32, 32);
            rectangle.setFill(Color.WHITE);
            rectangle.setStroke(Color.LIGHTGRAY);
            rectangle.setStrokeType(StrokeType.CENTERED);
            rectangle.setStrokeWidth(0.5);
            Image image = rectangle.snapshot(new SnapshotParameters(), null);
            gridBackground = new Background(new BackgroundImage(image, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT));
        }
    }

    public boolean isGridEnabled() {
        return gridEnable.get();
    }

    public BooleanProperty gridEnableProperty() {
        return gridEnable;
    }

    public void setGridEnable(boolean gridEnable) {
        this.gridEnable.set(gridEnable);
    }

    public double getZoom() {
        return zoom.get();
    }

    public DoubleProperty zoomProperty() {
        return zoom;
    }

    public void setZoom(double zoom) {
        this.zoom.set(zoom);
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
     *
     * @param node the node no add
     */
    public void add(Node node) {
        takeSnapshot();
        rootPane.getChildren().add(node);
        selectionModel.watch(node);
        rootPane.requestFocus();
    }

    private Callable<Node> pinToAddCallable;
    public Node pinToAdd(Node node) {
        try {
            node.setOpacity(0.5);
            pinned.add(node);
            rootPane.getChildren().add(node);
            pinToAddCallable = () -> node;
            rootPane.setOnMouseMoved(this::onPinMouseMoved);
            rootPane.setOnMousePressed(this::onPinMousePressed);
            Future<Node> futureResult = new FutureTask<>(pinToAddCallable);
            return futureResult.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return null;
        }
    }


    //////////////
    private void onNodeMousePressed(MouseEvent event) {
        Node target = (Node) event.getTarget();
        if (state == DrawState.NONE) {
            if (!selectionModel.isSelected(target)) {
                selectionModel.clearAndSelect(target);
            }
            state = DrawState.MOVING;
            event.consume();
        }
    }

    private void onPaneMousePressed(MouseEvent event) {
        // start box
        state = DrawState.SELECTING;
    }

    private void onPaneMouseDragged(MouseEvent event) {
        if (state == DrawState.SELECTING) {
            // resize the box
        } else if (state == DrawState.MOVING) {
            // moves the selection
        }
    }

    private void onPaneMouseRelease(MouseEvent event) {
        state = DrawState.NONE;
    }
    //////////////

    /**
     * Removes a given node from the drawing pane. If the node is not
     * in the drawing pane, nothing happens
     *
     * @param node the node to remove
     */
    public void remove(Node node) {
        takeSnapshot();
        selectionModel.clearSelection(node);
        selectionModel.unwatch(node);
        rootPane.getChildren().remove(node);
    }
    /**
     * Undo the last change
     */
    public void undo() {
        Snapshot snapshot = undoStack.pop();
        if (snapshot != null) {
            restoreSnapshot(snapshot);
        }
    }
    /**
     * Convenience method to remove all the nodes selected by the selection
     * model from the drawing pane
     */
    public void removeSelected() {
        // do not use for/foreach due ConcurrentModifyException
        while (!selectionModel.isEmpty()) {
            remove(selectionModel.getSelectedItems().iterator().next());
        }
    }

    private void onPinMouseMoved(MouseEvent event) {
        pinned.forEach(node -> followMouse(node, event));
        event.consume();
    }

    private void followMouse(Node node, MouseEvent event) {
        node.setLayoutX(event.getX() - node.getBoundsInLocal().getCenterX());
        node.setLayoutY(event.getY() - node.getBoundsInLocal().getCenterY());
    }

    private void onPinMousePressed(MouseEvent event) {
        try {
            if (event.getButton() == MouseButton.PRIMARY) {
                pinned.forEach(this::finallyAdd);
                rootPane.setOnMousePressed(selectionModel::onBoxMousePressed);
                pinToAddCallable.call();
                event.consume();
            } else if (event.getButton() == MouseButton.SECONDARY) {
                cancelPinning();
                rootPane.setOnMousePressed(selectionModel::onBoxMousePressed);
                event.consume();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void finallyAdd(Node node) {
        node.setOpacity(1);
        rootPane.getChildren().remove(node);
        add(node);
        pinned.remove(node);
    }

    private void cancelPinNode(Node node) {
        rootPane.getChildren().remove(node);
        pinned.remove(node);
    }

    public void cancelPinning() {
        while (!pinned.isEmpty()) {
            cancelPinNode(pinned.iterator().next());
        }
    }

    /**
     * @return The selection model
     */
    public SelectionModel getSelectionModel() {
        return selectionModel;
    }

    /**
     * Takes a snapshot of the drawing pane, and stores it in the redo stack
     *
     * @see Snapshot
     */
    private void takeSnapshot() {
        undoStack.push(new Snapshot(this));
    }
    /**
     * Restores state of a snapshot to the drawing pane
     *
     * @param snapshot the snapshot to restore
     */
    private void restoreSnapshot(Snapshot snapshot) {
        rootPane.getChildren().clear();
        snapshot.getChildren().forEach((node, point) -> {
            node.setLayoutX(point.getX());
            node.setLayoutY(point.getY());
            rootPane.getChildren().add(node);
        });
        selectionModel.clearSelection();
        selectionModel.selectAll(snapshot.getSelected());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // initializes the statics background
        initStatic();
        selectionModel = new SelectionModel(this);
        gridEnable.addListener(this::updateBackground);
        rootPane.scaleXProperty().bind(zoom);
        rootPane.scaleYProperty().bind(zoom);
        rootPane.setBackground(blankBackground);
        rootPane.setFocusTraversable(true);
        rootPane.setOnKeyPressed(this::handleKeys);
    }

    private void handleKeys(KeyEvent event) {
        if (event.getCode() == KeyCode.DELETE) {
            removeSelected();
            event.consume();
        }
    }

    private void updateBackground(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
        if (newValue)
            rootPane.setBackground(gridBackground);
        else
            rootPane.setBackground(blankBackground);
    }

    /**
     * Specifies the selection model engine for iSPD {@link DrawPaneController}. The way the selected
     * items interacts with the selection shape (rectangle). And also how the selected items
     * can be moved inside the drawing pane.
     */
    public static class SelectionModel {

        /**
         * Set that contains selected nodes
         */
        // private Set<Node> selected;
        private Map<Node, Rectangle> selected;
        /**
         * Set that contains all the node the selection
         * model is able to see to select
         */
        private Set<Node> watching;
        /**
         * The pane which this selection model refers.
         * Needed to access pane children
         */
        private DrawPaneController draw;
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

        // single* handlers: handle events to directly drag a node, indirectly selecting it
        private EventHandler<MouseEvent> singleMousePressedHandler = this::onSingleMousePressed;

        /**
         * Selection model constructor
         * @param draw the drawing pane the selection model belongs to
         */
        public SelectionModel(DrawPaneController draw) {
            super();
            this.draw = draw;
            // selected = new HashSet<>();
            selected = new HashMap<>();
            watching = new HashSet<>();
            selectionBox = new Rectangle(0, 0, 0, 0);
            boxContext = new SelectionBoxContext();
            dragContext = new DragContext();
            draw.rootPane.setOnMousePressed(this::onBoxMousePressed);
            draw.rootPane.setOnMouseDragged(this::onBoxMouseDragged);
            draw.rootPane.setOnMouseReleased(this::onBoxMouseReleased);
        }

        /**
         * Returns a set with all selected nodes
         * @return a set with all selected nodes
         */
        public Set<Node> getSelectedItems() {
            return selected.keySet();
        }

        public void selectAll() {
            draw.rootPane.getChildren().forEach(this::select);
        }

        public void selectAll(Collection<Node> collection) {
            collection.forEach(this::select);
        }

        public void selectAll(Node... nodes) {
            selectAll(Arrays.asList(nodes));
        }

        /**
         * Select a given node in the drawing pane. If the node is not being watched for selection,
         * and {@link IllegalArgumentException} is thrown.
         *
         * @param node the node to select
         *
         * @see SelectionModel#watch(Node)
         */
        public void select(Node node) {
            if (watching.contains(node) && !isSelected(node)) {
                // adds node to the selected map decorating it with a border rectangle
                Rectangle border = createDecoration(node);
                draw.rootPane.getChildren().add(border);
                selected.put(node, border);
                // exchange the single handler by the group handler in order to respect selection
                // events with other selected items
                node.setOnMousePressed(this::onGroupMousePressed);
                node.setOnMouseDragged(this::onGroupMouseDragged);
                node.setOnMouseReleased(this::onGroupMouseReleased);
            }
        }

        private Rectangle createDecoration(Node node) {
            Rectangle border = new Rectangle();
            border.setStroke(Color.GREEN);
            border.setWidth(1.0);
            border.setFill(null);
            border.setLayoutX(node.getBoundsInParent().getMinX());
            border.setLayoutY(node.getBoundsInParent().getMinY());
            border.setWidth(node.getBoundsInLocal().getWidth());
            border.setHeight(node.getBoundsInLocal().getHeight());
            return border;
        }

        /**
         * Unselect a given node. If the node is being watched for selections, an {@link IllegalArgumentException}
         * is thrown. If the node is in drawing pane but it is not selected, it remains unselected and nothing
         * happens.
         *
         * @param node the node to unselect
         *
         * @see SelectionModel#watch(Node)
         */
        public void clearSelection(Node node) {
            if (watching.contains(node) && isSelected(node)) {
                unselect(node);
            }
        }

        /**
         * Starts to watch node for selections, configuring it
         * to respond the selection model. If given node is not
         * in the drawing pane, an {@link IllegalArgumentException}
         * is throw.
         *
         * @param node the node to watch
         */
        public void watch(Node node) {
            if (draw.rootPane.getChildren().contains(node)) {
                if (watching.add(node)) {
                    // node.addEventHandler(MouseEvent.MOUSE_PRESSED, singleMousePressedHandler);
                    node.setOnMousePressed(this::onSingleMousePressed);
                }
            } else {
                throw new IllegalArgumentException("Node is not one of the " + DrawPaneController.class.getName() + " children");
            }
        }

        /**
         * Unselect a node without verifying if it is inside the drawing pane
         *
         * @param node the node to unselect
         */
        private void unselect(Node node) {
            // removes decoration rectangle of the node in the pane
            draw.rootPane.getChildren().remove(selected.get(node));
            // removes the node from selected map
            selected.remove(node);
            // exchange the group handler by the single handler in order to be able
            // to be indirectly selected alone
            node.setOnMousePressed(this::onSingleMousePressed);
            node.setOnMouseDragged(null);
            node.setOnMouseDragged(null);
        }

        /**
         * Unselect all selected nodes
         */
        public void clearSelection() {
            while (!isEmpty()) {
                unselect(selected.keySet().iterator().next());
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
            return selected.containsKey(node);
        }

        /**
         * Checks if selection is empty
         *
         * @return true if selection is empty
         */
        public boolean isEmpty() {
            return selected.isEmpty();
        }

        /**
         * Stops to watch the node for selections, and removes
         * configurations to node respond to watches. If the node
         * is not being watched, nothing happens
         *
         * @param node the node to unwatch
         */
        public void unwatch(Node node) {
            if (watching.remove(node)) {
                node.setOnMousePressed(null);
            }
        }

        /**
         * <p>
         * Blocks a node for selection, consuming any pressed event. To
         * return to normal, {@link #unblock(Node)} must be called
         * <p>
         * Note that if node is not being watched for selections, nothing
         * happens
         * 
         * @param node the node to block
         *             
         * @see #unblock(Node) 
         */
        public void block(Node node) {
            if (watching.contains(node)) {
                node.setOnMousePressed(Event::consume);
            }
        }

        /**
         * <p>
         * Unblocks a node previously blocked by {@link #block(Node)}
         * <p>
         * Note that if node is not being watched, nothing happens
         *
         * @param node the blocked node to unblock
         */
        public void unblock(Node node) {
            if (watching.contains(node)) {
                node.setOnMousePressed(this::onSingleMousePressed);
            }
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
                for (Node node : draw.rootPane.getChildren()) {
                    if (selectionBox.getBoundsInParent().contains(node.getBoundsInParent())) {
                        set.add(node);
                    }
                }
            } else if (boxContext.getPolicy() == SelectionPolicy.INTERSECTS) {
                for (Node node : draw.rootPane.getChildren()) {
                    if (node.getBoundsInParent().intersects(selectionBox.getBoundsInParent())) {
                        set.add(node);
                    }
                }
            } else if (boxContext.getPolicy() == SelectionPolicy.SINGLE) {
                // iterates the list in reverse order to get the 'up-layer' nodes first
                ListIterator<Node> iterator = draw.rootPane.getChildren().listIterator(draw.rootPane.getChildren().size());
                while (iterator.hasPrevious()) {
                    Node node = iterator.previous();
                    if (node.getBoundsInParent().contains(boxContext.getStartX(), boxContext.getStartY())) {
                        set.add(node);
                        break;
                    }
                }
            }
            if (!set.isEmpty()) {
                draw.takeSnapshot();
            }
            set.forEach(this::select);
        }
        /**
         * Change the selection box position accordingly with drag context
         */
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

        /**
         * Change all the selected items position accordingly with the drag context
         */
        private void updateSelectedItemsPosition() {
            selected.keySet().forEach(this::updateSingleItem);
        }

        private void updateSingleItem(Node node) {
            node.setLayoutX(node.getLayoutX() + dragContext.getTranslateX());
            node.setLayoutY(node.getLayoutY() + dragContext.getTranslateY());
            Rectangle border = selected.get(node);
            border.setLayoutX(node.getBoundsInParent().getMinX());
            border.setLayoutY(node.getBoundsInParent().getMinY());
            border.setWidth(node.getBoundsInLocal().getWidth());
            border.setHeight(node.getBoundsInLocal().getHeight());
        }
        // event handlers methods
        private void onBoxMousePressed(MouseEvent event) {
            if (event.getButton() == MouseButton.PRIMARY) {
                boxContext.setStartX(event.getX());
                boxContext.setStartY(event.getY());
                boxContext.setEndX(event.getX());
                boxContext.setEndY(event.getY());
                updateSelectionRectangle();
                draw.rootPane.getChildren().add(selectionBox);
                draw.rootPane.requestFocus();
                event.consume();
            }
        }

        private void onBoxMouseDragged(MouseEvent event) {
            if (event.getButton() == MouseButton.PRIMARY) {
                boxContext.setEndX(event.getX());
                boxContext.setEndY(event.getY());
                updateSelectionRectangle();
                event.consume();
            }
        }

        private void onBoxMouseReleased(MouseEvent event) {
            draw.rootPane.getChildren().remove(selectionBox);
            if (event.getButton() == MouseButton.PRIMARY) {
                clearAndUpdateSelectedItems();
                event.consume();
            }
        }

        private void onGroupMousePressed(MouseEvent event) {
            if (event.getButton() == MouseButton.PRIMARY) {
                draw.takeSnapshot();
                dragContext.setStartX(event.getX());
                dragContext.setStartY(event.getY());
                dragContext.setEndX(event.getX());
                dragContext.setEndY(event.getY());
                updateSelectedItemsPosition();
                draw.rootPane.requestFocus();
                event.consume();
            }
        }

        private void onGroupMouseDragged(MouseEvent event) {
            if (event.getButton() == MouseButton.PRIMARY) {
                dragContext.setEndX(event.getX());
                dragContext.setEndY(event.getY());
                updateSelectedItemsPosition();
                event.consume();
            }
        }

        private void onGroupMouseReleased(MouseEvent event) {
            if (event.getButton() == MouseButton.PRIMARY) {
                event.consume();
            }
        }

        private void onSingleMousePressed(MouseEvent event) {
            if (event.getButton() == MouseButton.PRIMARY) {
                draw.rootPane.requestFocus();
                // clear the selection and selects only the pressed node.
                // Note that in selection the event that node listen will be changed
                // then it can be dragged
                clearAndSelect((Node)event.getTarget());
                // fires the event to the same target to respond with the new event handler
                // set on clearAndSelect method
                Event.fireEvent(event.getTarget(), event);
                // consume the event to not propagate in event chain
                event.consume();
            }
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
    /**
     * Snapshot of important states about the drawing pane, in order to allow
     * undo and redo operations.
     * <p>
     * Note that it is immutable via constructor and getters
     */
    private static class Snapshot {
        /**
         * Stores the nodes in scene, and maps them to their respective layout
         * position
         */
        private Map<Node, Point2D> children;
        /**
         * Stores the set of selected node
         */
        private Set<Node> selected;

        public Snapshot(DrawPaneController draw) {
            // Linked hash map is used here to guarantee the order of keys is in the same order
            // they were added. It is important in the later snapshot restoring process
            this.children = new LinkedHashMap<>();
            draw.rootPane.getChildren().forEach(node -> {
                Point2D point = new Point2D(node.getLayoutX(), node.getLayoutY());
                children.put(node, point);
            });
            this.selected = new HashSet<>(draw.getSelectionModel().getSelectedItems());
        }

        public Set<Node> getSelected() {
            return new HashSet<>(selected);
        }

        public Map<Node, Point2D> getChildren() {
            return new LinkedHashMap<>(children);
        }
    }
}