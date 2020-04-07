package gspd.ispd.fxgui.workload.dag;

import gspd.ispd.fxgui.commons.*;
import gspd.ispd.fxgui.workload.dag.icons.SwitchIcon;
import javafx.scene.Node;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DAG extends Diagram {

    @Override
    public boolean add(Icon icon) {
        boolean added = false;
        synchronized (this) {
            if (icon.getType().isTypeOf(NodeIcon.NODE_TYPE)) {
                added = super.add(icon);
            } else if (icon.getType().isTypeOf(EdgeIcon.EDGE_TYPE)) {
                EdgeIcon edge = (EdgeIcon) icon;
                if (allowed(edge)) {
                    added = super.add(icon);
                    if (edge.getStartIcon().getType().isTypeOf(SwitchIcon.SWITCH_TYPE)) {
                        SwitchIcon sw = (SwitchIcon) edge.getStartIcon();
                        if (!sw.getDistributionMap().containsKey(edge)) {
                            sw.putEdge(edge, 0.0);
                        }
                    }
                }
            }
        }
        return added;
    }

    protected boolean allowed(EdgeIcon edge) {
        boolean allow = true;
        List<Node> edges = getIconsByType(EdgeIcon.EDGE_TYPE);
        for (Node node : edges) {
            EdgeIcon ei = (EdgeIcon) node;
            if (ei.getStartIcon() == edge.getStartIcon() && ei.getEndIcon() == edge.getEndIcon() ||
                closeCycle(edge)) {
                allow = false;
                break;
            }
        }
        return allow;
    }

    @Override
    synchronized public boolean remove(Icon icon) {
        boolean removed = super.remove(icon);
        if (removed && icon.getType().isTypeOf(EdgeIcon.EDGE_TYPE)) {
            EdgeIcon edge = (EdgeIcon) icon;
            NodeIcon node = edge.getStartIcon();
            if (node != null && node.getType().isTypeOf(SwitchIcon.SWITCH_TYPE)) {
                SwitchIcon sw = (SwitchIcon) node;
                sw.removeEdge(edge);
            }
        }
        return removed;
    }

    private static final int WHITE = 0;
    private static final int GRAY = 1;
    private static final int BLACK = 2;
    private HashMap<Node, Integer> colorMap;
    private boolean closeCycle(EdgeIcon edge) {
        List<Node> nodes = getIconsByType(NodeIcon.NODE_TYPE);
        colorMap = new HashMap<>();
        for (Node node : nodes) {
            colorMap.put(node, WHITE);
        }
        colorMap.put(edge.getStartIcon(), GRAY);
        return wgb(edge.getEndIcon());
    }
    // https://www.ime.usp.br/~pf/algoritmos_em_grafos/aulas/dag.html
    private boolean wgb(NodeIcon vertex) {
        List<Node> adjEdges = getIconsByTypeStream(EdgeIcon.EDGE_TYPE)
                .filter(node -> ((EdgeIcon)node).getStartIcon() == vertex)
                .collect(Collectors.toList());
        for (Node node : adjEdges) {
            EdgeIcon edge = (EdgeIcon) node;
            NodeIcon dest = edge.getEndIcon();
            if (colorMap.get(dest) == BLACK) {
                continue;
            }
            if (colorMap.get(dest) == GRAY) {
                return true;
            }
            colorMap.put(dest, GRAY);
            if (wgb(dest)) {
                return true;
            }
        }
        colorMap.put(vertex, BLACK);
        return false;
    }

}
