package gspd.ispd.fxgui.dag;

import gspd.ispd.fxgui.commons.*;
import gspd.ispd.fxgui.dag.icons.*;
import javafx.beans.InvalidationListener;
import javafx.event.EventHandler;
import javafx.event.EventTarget;
import javafx.geometry.Orientation;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class DagEditor extends HBox {

    private DiagramPane diagramPane;
    private DagIconMenu dagMenu;
    private IconConfigPane iconConfig;

    public DagEditor() {

        createContent();

        diagramPane.addEventHandler(MouseEvent.MOUSE_CLICKED, generalMouseClickedHandler);
        diagramPane.addEventHandler(ContextMenuEvent.CONTEXT_MENU_REQUESTED, contextMenuHandler);
        diagramPane.getSelectionModel().getSelectedIcons().addListener((InvalidationListener) e -> {
            iconConfig.setIcon(diagramPane.getSelectionModel().getSelectedIcon());
        });

        addEventHandler(KeyEvent.KEY_RELEASED, e -> {
            if (e.getCode() == KeyCode.DELETE) {
                diagramPane.removeSelected();
                e.consume();
            }
        });

    }

    private void createContent() {
        dagMenu = new DagIconMenu();
        iconConfig = new IconConfigPane();

        SplitPane leftSplit = new SplitPane();
        leftSplit.setOrientation(Orientation.VERTICAL);
        leftSplit.getItems().setAll(dagMenu, iconConfig);
        HBox.setHgrow(leftSplit, Priority.NEVER);

        ToolBar bottomToolbar = new ToolBar();
        CheckBox gridCheckBox = new CheckBox("Grid");
        bottomToolbar.getItems().setAll(gridCheckBox);
        VBox.setVgrow(bottomToolbar, Priority.NEVER);

        diagramPane = new DiagramPane();
        VBox.setVgrow(diagramPane, Priority.ALWAYS);
        diagramPane.setDiagram(createDAG());
        diagramPane.gridEnableProperty().bind(gridCheckBox.selectedProperty());
        VBox rightPane = new VBox();
        rightPane.getChildren().setAll(diagramPane, bottomToolbar);
        HBox.setHgrow(rightPane, Priority.ALWAYS);
        super.getChildren().setAll(leftSplit, rightPane);

    }

    private EdgeIcon tempEdge;
    private void startAddEdge(MouseEvent event, EdgeIcon edge, NodeIcon startIcon) {
        tempEdge = edge;
        tempEdge.setStartIcon(startIcon);
        tempEdge.setEndX(event.getX());
        tempEdge.setEndY(event.getY());
        tempEdge.setMouseTransparent(true);
        diagramPane.getDiagram().add(tempEdge);
        diagramPane.removeEventHandler(MouseEvent.MOUSE_CLICKED, generalMouseClickedHandler);
        diagramPane.addEventHandler(MouseEvent.MOUSE_CLICKED, addEdgeMouseClickedEvent);
        diagramPane.addEventHandler(MouseEvent.MOUSE_MOVED, addEdgeMouseMovedHandler);
        diagramPane.removeEventHandler(ContextMenuEvent.CONTEXT_MENU_REQUESTED, contextMenuHandler);
    }

    private void stopAddEdge(boolean success) {
        diagramPane.getDiagram().remove(tempEdge);
        if (success) {
            diagramPane.getDiagram().add(tempEdge);
            tempEdge.setMouseTransparent(false);
        }
        tempEdge = null;
        diagramPane.removeEventHandler(MouseEvent.MOUSE_CLICKED, addEdgeMouseClickedEvent);
        diagramPane.removeEventHandler(MouseEvent.MOUSE_MOVED, addEdgeMouseMovedHandler);
        diagramPane.addEventHandler(MouseEvent.MOUSE_CLICKED, generalMouseClickedHandler);
        diagramPane.addEventHandler(ContextMenuEvent.CONTEXT_MENU_REQUESTED, contextMenuHandler);
    }

    //////////////////////////////////////////////////////////////
    ////////////////////////// FLAGS /////////////////////////////
    //////////////////////////////////////////////////////////////

    private static final int NONE = 0;
    private static final int ADD_EDGE = 1;
    private static final int ADD_NODE = 2;

    private int flags = NONE;
    private void setFlags(int flags) {
        this.flags = flags;
    }
    private void addFlags(int state) {
        this.flags |= state;
    }
    private void removeFlags(int state) {
        this.flags &= (~state);
    }
    private int getFlags() {
        return flags;
    }
    private boolean isFlag(int state) {
        return (this.flags & state) != 0;
    }

    //////////////////////////////////////////////////////////////
    ///////////////////// EVENT HANDLERS /////////////////////////
    //////////////////////////////////////////////////////////////

    private final EventHandler<MouseEvent> generalMouseClickedHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            if (event.getButton() == MouseButton.PRIMARY) {
                EventTarget target = event.getTarget();
                if (target == diagramPane) {
                    diagramPane.getSelectionModel().clear();
                    if (dagMenu.getSelectedItem() != null) {
                        Icon newIcon = dagMenu.getSelectedItem().getIconBuilder().build();
                        if (newIcon.getType().isTypeOf(NodeIcon.NODE_TYPE)) {
                            if (newIcon.getType().isTypeOf(NodeIcon.NODE_TYPE)) {
                                NodeIcon node = (NodeIcon) newIcon;
                                node.setCenterX(event.getX());
                                node.setCenterY(event.getY());
                                diagramPane.getDiagram().add(newIcon);
                                diagramPane.getSelectionModel().clearAndSelect(newIcon);
                            }
                        }
                        event.consume();
                    }
                } else if (target instanceof Icon) {
                    Icon targetIcon = (Icon) target;
                    if (dagMenu.getSelectedItem() != null) {
                        Icon newIcon = dagMenu.getSelectedItem().getIconBuilder().build();
                        if (newIcon.getType().isTypeOf(EdgeIcon.EDGE_TYPE)) {
                            EdgeIcon edge = (EdgeIcon) newIcon;
                            if (targetIcon.getType().isTypeOf(NodeIcon.NODE_TYPE)) {
                                System.out.println("You clicked a node do add an edge");
                                NodeIcon startIcon = (NodeIcon) target;
                                startAddEdge(event, edge, startIcon);
                                event.consume();
                            }
                        }
                    }
                    if (event.isControlDown()) {
                        diagramPane.getSelectionModel().toggle(targetIcon);
                    } else {
                        diagramPane.getSelectionModel().clearAndSelect(targetIcon);
                    }
                    event.consume();
                }
            }
        }
    };

    private EventHandler<MouseEvent> addEdgeMouseMovedHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            tempEdge.setEndX(event.getX());
            tempEdge.setEndY(event.getY());
        }
    };

    private final EventHandler<ContextMenuEvent> contextMenuHandler = new EventHandler<ContextMenuEvent>() {
        @Override
        public void handle(ContextMenuEvent event) {
            if (event.getTarget() == diagramPane) {
                System.out.println("Context requested in pane");
            } else if (event.getTarget() instanceof Icon) {
                Icon icon = (Icon) event.getTarget();
                if (diagramPane.getSelectionModel().isSelected(icon)) {
                    System.out.println("Context requested in selection");
                } else {
                    diagramPane.getSelectionModel().clearAndSelect(icon);
                    System.out.println("Context requested by an unselected icon");
                }
            }
        }
    };

    private EventHandler<MouseEvent> addEdgeMouseClickedEvent = new EventHandler<>() {
        @Override
        public void handle(MouseEvent event) {
            if (event.getButton() == MouseButton.PRIMARY) {
                EventTarget target = event.getTarget();
                if (target == diagramPane) {
                    System.out.println("You must click a node to stop");
                } else if (target instanceof Icon) {
                    Icon targetIcon = (Icon) target;
                    if (targetIcon.getType().isTypeOf(NodeIcon.NODE_TYPE)) {
                        NodeIcon node = (NodeIcon) targetIcon;
                        if (node != tempEdge.getStartIcon()) {
                            tempEdge.setEndIcon(node);
                            stopAddEdge(true);
                        } else {
                            System.out.println("You must select another icon");
                        }
                    } else {
                        System.out.println("You must select a node");
                        diagramPane.getDiagram().remove(tempEdge);
                        stopAddEdge(false);
                    }
                    event.consume();
                }
            } else if (event.getButton() == MouseButton.SECONDARY) {
                diagramPane.getDiagram().remove(tempEdge);
                stopAddEdge(false);
            }
        }
    };

    /////////////////////////////////////////////////////////////////////
    //////////////////////// TEMPORARY //////////////////////////////////
    /////////////////////////////////////////////////////////////////////

    private Diagram createSimpleDAG() {
        Diagram diagram = new DAG();

        NodeIcon node1 = new TaskIcon(20, 20);
        NodeIcon node2 = new TaskIcon(40, 20);
        NodeIcon node3 = new TaskIcon(60, 20);

        diagram.add(node1);
        diagram.add(node2);
        diagram.add(node3);

        return diagram;
    }

    private Diagram createLIGO() {
        Diagram d = new DAG();
        double y = 20.0, x = 0.0;
        int i;
        x = 100.0;
        TaskIcon t = new TaskIcon(x, y);
        d.add(t);
        for (x = 10.0; x <= 190.0; x += 20.0) {
            TaskIcon ta = new TaskIcon(x, y + 40.0);
            TaskIcon tb = new TaskIcon(x, y + 80.0);
            PrecedenceIcon p = new PrecedenceIcon(ta, tb);
            PrecedenceIcon g = new PrecedenceIcon(t, ta);
            d.addAll(ta, tb, p, g);
        }
        return d;
    }

    private Diagram createDAG() {

        Diagram diagram1 = new DAG();

        NodeIcon node1 = new TaskIcon(30, 30);
        diagram1.add(node1);
        NodeIcon node2 = new TaskIcon(30, 60);
        diagram1.add(node2);
        EdgeIcon dep1 = new PrecedenceIcon(node1, node2);
        diagram1.add(dep1);

        Diagram diagram2 = new DAG();

        NodeIcon exp1 = new IterativeExpansionIcon(diagram1, 80, 50);
        diagram2.add(exp1);
        NodeIcon node3 = new TaskIcon(95, 140);
        diagram2.add(node3);
        EdgeIcon dep2 = new PrecedenceIcon(exp1, node3);
        diagram2.add(dep2);

        Diagram diagram3 = new DAG();

        NodeIcon exp2 = new RecursiveExpansionIcon(diagram2, 40, 60);
        diagram3.add(exp2);

        NodeIcon node4 = new TimerIcon(60, 250);
        diagram3.add(node4);

        EdgeIcon dep3 = new FailIcon(exp2, node4);
        diagram3.add(dep3);

        node3.setCenterX(50);
        node1.setCenterX(150);

        return diagram3;
    }

    public Diagram cyclical() {
        DAG dag = new DAG();
        NodeIcon n1 = new TimerIcon(20, 20);
        NodeIcon n2 = new TaskIcon(50, 60);
        NodeIcon n3 = new SynchronizeIcon(20, 60);
        dag.addAll(n1, n2, n3);
        dag.addAll(
                new PrecedenceIcon(n1, n2),
                new PrecedenceIcon(n2, n3),
                new PrefixIcon(n3, n1),
                new FailIcon(n1, n3)
        );
        return dag;
    }
}
