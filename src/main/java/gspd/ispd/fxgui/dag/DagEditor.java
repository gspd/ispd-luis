package gspd.ispd.fxgui.dag;

import gspd.ispd.fxgui.commons.*;
import gspd.ispd.fxgui.dag.icons.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

public class DagEditor extends HBox {

    private NodeIcon hoverNode;

    public DagEditor() {

        DagIconMenu menu = new DagIconMenu();
        HBox.setHgrow(menu, Priority.NEVER);
        DiagramPane pane = new DiagramPane();
        pane.setDiagram(cyclical());
        HBox.setHgrow(pane, Priority.ALWAYS);

        pane.addEventHandler(MouseEvent.MOUSE_RELEASED, e -> {
            if (e.getButton() == MouseButton.PRIMARY) {
                if (e.getTarget() == pane) {
                    if (menu.getSelectedItem() != null) {
                        Icon newIcon = menu.getSelectedItem().getIconBuilder().build();
                        if (newIcon != null && newIcon.getIconType().isTypeOf(NodeIcon.NODE_TYPE)) {
                            NodeIcon node = (NodeIcon) newIcon;
                            node.setCenterX(e.getX());
                            node.setCenterY(e.getY());
                            pane.getDiagram().add(newIcon);
                            pane.getSelectionModel().clearAndSelect(newIcon);
                        }
                        e.consume();
                    }
                }
            }
        });
        pane.setGridEnable(true);

        super.getChildren().setAll(menu, pane);
    }

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
