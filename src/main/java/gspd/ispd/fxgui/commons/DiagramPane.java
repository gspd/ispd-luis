package gspd.ispd.fxgui.commons;

import gspd.ispd.fxgui.workload.dag.DAG;
import gspd.ispd.fxgui.workload.dag.icons.ExpansionIcon;
import javafx.beans.property.*;
import javafx.beans.value.ObservableValue;
import javafx.event.EventTarget;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DiagramPane extends Pane {

    ////////////////////////////////////
    //////////// CONSTRUCTOR ///////////
    ////////////////////////////////////

    public DiagramPane() {
        // LISTENERS
        gridEnableProperty().addListener(this::gridEnableChange);
        diagramProperty().addListener(this::diagramChanged);
        // EVENTS
        // MOUSE EVENTS
        addEventHandler(MouseEvent.DRAG_DETECTED, this::dragDetected);
        // SETs
        setGridEnable(false);
    }

    private double startX;
    private double startY;
    private void startSelectionDrag(MouseEvent event) {
        startFullDrag();
        startX = event.getX();
        startY = event.getY();
        setOnMouseDragOver(e -> {
            Point2D mousePos = new Point2D(e.getX(), e.getY());
            double dx = e.getX() - startX;
            double dy = e.getY() - startY;
            getSelectionModel().translateSelectedIcons(dx, dy);
            getDiagram().getIconsByTypeStream(ExpansionIcon.EXPANSION_TYPE)
                    .forEach(exp -> {
                        ExpansionIcon expIcon = (ExpansionIcon) exp;
                        if (exp.getBoundsInParent().contains(mousePos) && exp != e.getTarget()) {
                            expIcon.setHovered(true);
                        } else {
                            expIcon.setHovered(false);
                        }
                    });
            startX = e.getX();
            startY = e.getY();
            e.consume();
        });
        setOnMouseDragReleased(e -> stopSelectionDrag());
    }

    private void stopSelectionDrag() {
        List<Node> hovereds = getDiagram()
                .getIconsByTypeStream(ExpansionIcon.EXPANSION_TYPE)
                .filter(exp -> ((ExpansionIcon)exp).isHovered())
                .collect(Collectors.toList());
        if (hovereds.size() > 0) {
            hovereds.forEach(node -> ((ExpansionIcon) node).setHovered(false));
            getSelectionModel().selectConnectedEdges();
            getDiagram().removeAll(getSelectionModel().getSelectedIcons());
            ExpansionIcon theExpansion = (ExpansionIcon) hovereds.get(0);
            if (theExpansion.getDiagram() == null) {
                DAG dag = new DAG();
                dag.addAll(getSelectionModel().getSelectedIcons());
                theExpansion.setDiagram(dag);
            } else {
                theExpansion.getDiagram().addAll(getSelectionModel().getSelectedIcons());
            }
        }
        setOnMouseDragOver(null);
        setOnMouseDragReleased(null);
    }

    ///////////////////////////////////////////////
    //////////////// METHODS //////////////////////
    ///////////////////////////////////////////////

    /**
     * Verify if this DiagramPane has the icon
     * @param icon the icon to check if is inside
     * @return true if this DiagramPane has this icon and false otherwise
     */
    public boolean has(Icon icon) {
        return has(getDiagram(), icon);
    }

    /**
     * Helper recursive method to implement {@link #has(Icon)} method
     */
    private boolean has(Diagram diagram, Node node) {
        if (diagram == null) {
            return false;
        }
        if (diagram.getChildren().contains(node)) {
            return true;
        }
        List<Node> expansions = diagram.getIconsByType(ExpansionIcon.EXPANSION_TYPE);
        for (Node expansion : expansions) {
            ExpansionIcon exp = (ExpansionIcon) expansion;
            if (has(exp.getDiagram(), node)) {
                return true;
            }
        }
        return false;
    }

    public void removeSelected() {
        while (!getSelectionModel().isEmpty()) {
            Icon icon = getSelectionModel().getSelectedIcons().iterator().next();
            getDiagram().remove(icon);
            getSelectionModel().unselect(icon);
        }
    }

    ////////////////////////////////////
    ////////// EVENT HANDLERS //////////
    ////////////////////////////////////

    private void diagramChanged(ObservableValue<? extends Diagram> observable, Diagram oldValue, Diagram newValue) {
        super.getChildren().remove(oldValue);
        super.getChildren().add(newValue);
    }

    private void gridEnableChange(ObservableValue<? extends Boolean> observable, boolean oldValue, boolean newValue) {
        if (newValue) {
            setBackground(
                new Background(
                    new BackgroundImage(
                        new Rectangle(31.5, 31.5, Color.WHITE) {
                            {
                                setStroke(Color.LIGHTGRAY);
                                setStrokeType(StrokeType.CENTERED);
                                setStrokeWidth(0.9);
                            }
                        }.snapshot(new SnapshotParameters(), null),
                        BackgroundRepeat.REPEAT,
                        BackgroundRepeat.REPEAT,
                        BackgroundPosition.DEFAULT,
                        BackgroundSize.DEFAULT
                    )
                )
            );
        } else {
            setBackground(
                new Background(
                    new BackgroundFill(
                        Color.WHITE,
                        CornerRadii.EMPTY,
                        Insets.EMPTY
                    )
                )
            );
        }
    }

    private void dragDetected(MouseEvent event) {
        if (event.getButton() == MouseButton.PRIMARY) {
            EventTarget target = event.getTarget();
            if (target == this) {
                // Dragging in pane
                getSelectionModel().startSelecting(event);
            } else if (target instanceof Icon) {
                Icon icon = (Icon) target;
                if (!getSelectionModel().isSelected(icon)) {
                    getSelectionModel().clearAndSelect(icon);
                }
                if (event.isControlDown()) {
                    System.out.println("Copying/Moving the selection");
                } else {
                    // Drag an unselected icon
                    startSelectionDrag(event);
                }
            } else {
                System.out.println("Dragging something else");
            }
            event.consume();
        }
    }

    /////////////////////////////////
    ///////// PROPERTIES ////////////
    /////////////////////////////////

    /**
     * The grid enable property
     */
    private BooleanProperty gridEnable = new SimpleBooleanProperty(this, "gridEnable", true);
    public boolean isGridEnable() {
        return gridEnable.get();
    }
    public BooleanProperty gridEnableProperty() {
        return gridEnable;
    }
    public void setGridEnable(boolean gridEnable) {
        this.gridEnable.set(gridEnable);
    }

    /**
     * The diagram in the pane
     */
    private ObjectProperty<Diagram> diagram = new SimpleObjectProperty<>(this, "diagram", null);
    public Diagram getDiagram() {
        return diagram.get();
    }
    public ObjectProperty<Diagram> diagramProperty() {
        return diagram;
    }
    public void setDiagram(Diagram diagram) {
        this.diagram.set(diagram);
    }

    /**
     * The selection model
     */
    private ObjectProperty<DiagramSelectionModel> selectionModel = new SimpleObjectProperty<>(this, "selectionMode", new DiagramSelectionModel(this));
    public DiagramSelectionModel getSelectionModel() {
        return selectionModel.get();
    }
    public ReadOnlyObjectProperty<DiagramSelectionModel> selectionModelProperty() {
        return selectionModel;
    }
}
