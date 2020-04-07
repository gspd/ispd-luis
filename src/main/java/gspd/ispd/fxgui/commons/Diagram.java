package gspd.ispd.fxgui.commons;

import gspd.ispd.commons.ISPDType;
import gspd.ispd.fxgui.workload.dag.icons.ExpansionIcon;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.Group;
import javafx.scene.Node;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Represents a diagram with Nodes and Edges (Arrows)
 */
public class Diagram extends Group {

    public Diagram() { }
    ///////////////////////////////////////////////////////////
    // The children list should be maintained as follows, in
    // order to be correctly rendered:
    //
    // (begin) | decorations... | edges ... | expansions... | nodes... | (end)
    ///////////////////////////////////////////////////////////

    private int edges = 0;
    private boolean addEdge(EdgeIcon icon) {
        getChildren().add(edges, icon);
        edges++;
        return true;
    }

    private int expansions = 0;
    private boolean addExpansion(ExpansionIcon icon) {
        getChildren().add(edges + expansions, icon);
        expansions++;
        return true;
    }

    private int nodes = 0;
    private boolean addNode(NodeIcon icon) {
        getChildren().add(edges + expansions + nodes, icon);
        nodes++;
        return true;
    }

    private boolean removeEdge(EdgeIcon icon) {
        if (getChildren().remove(icon)) {
            edges--;
            return true;
        }
        return false;
    }

    private boolean removeExpansion(ExpansionIcon icon) {
        if (getChildren().remove(icon)) {
            expansions--;
            removeAdjacentEdges(icon);
            return true;
        }
        return false;
    }

    private boolean removeNode(NodeIcon icon) {
        if (getChildren().remove(icon)) {
            nodes--;
            removeAdjacentEdges(icon);
            return true;
        }
        return false;
    }

    private void removeAdjacentEdges(NodeIcon nodeIcon) {
        List<Node> adjEdges = getIconsByTypeStream(EdgeIcon.EDGE_TYPE)
                .filter(icon -> {
                    EdgeIcon ei = (EdgeIcon) icon;
                    return ei.getStartIcon() == nodeIcon ||
                            ei.getEndIcon() == nodeIcon;
                })
                .collect(Collectors.toList());
        for (Node edge : adjEdges) {
            removeEdge((EdgeIcon) edge);
        }
    }

    ///////////////////////////////////////
    ////////////// METHODS ////////////////
    ///////////////////////////////////////

    public boolean add(Icon icon) {
        boolean added = false;
        synchronized (this) {
            if (icon.getType().isTypeOf(ExpansionIcon.EXPANSION_TYPE)) {
                added = addExpansion((ExpansionIcon) icon);
            } else if (icon.getType().isTypeOf(NodeIcon.NODE_TYPE)) {
                added = addNode((NodeIcon) icon);
            } else if (icon.getType().isTypeOf(EdgeIcon.EDGE_TYPE)) {
                added = addEdge((EdgeIcon) icon);
            }
        }
        return added;
    }

    public void addAll(Collection<Icon> icons) {
        icons.forEach(this::add);
    }

    public void addAll(Icon... icons) {
        for (Icon icon : icons) {
            add(icon);
        }
    }

    synchronized public boolean remove(Icon icon) {
        if (icon == null) {
            return false;
        }
        boolean removed = false;
        if (icon.getType().isTypeOf(ExpansionIcon.EXPANSION_TYPE)) {
            removed = removeExpansion((ExpansionIcon) icon);
        } else if (icon.getType().isTypeOf(NodeIcon.NODE_TYPE)) {
            removed = removeNode((NodeIcon) icon);
        } else if (icon.getType().isTypeOf(EdgeIcon.EDGE_TYPE)) {
            removed = removeEdge((EdgeIcon) icon);
        }
        return removed;
    }

    public void removeAll(Collection<Icon> icons) {
        icons.forEach(this::remove);
    }

    public void removeAll(Icon... icons) {
        for (Icon icon : icons) {
            remove(icon);
        }
    }

    /**
     * Returns a list of icons in diagram of the given type
     * @param type the icon type
     * @return a list of icons (as Nodes)
     */
    public <T extends Node> List<T> getIconsByType(ISPDType type) {
        return (List<T>) getIconsByTypeStream(type).collect(Collectors.toList());
    }

    /**
     * Returns a stream of icons in diagram of the given type
     * @param type the icon type
     * @return a stream of icons (as Nodes)
     */
    public Stream<Node> getIconsByTypeStream(ISPDType type) {
        return getChildren()
                .stream()
                .filter(node -> ((Icon) node).getType().isTypeOf(type));
    }

    public Node getIconById(String id) {
        List<Node> unaryList = getChildren()
                .stream()
                .filter(node -> ((Icon) node).getIconID().equals(id))
                .collect(Collectors.toList());
        if (unaryList.size() == 1) return unaryList.get(0);
        return null;
    }

    /////////////////////////////////////
    ///////////// PROPERTIES ////////////
    /////////////////////////////////////

    private StringProperty name = new SimpleStringProperty(this, "name", "unnamed dag");
    public String getName() {
        return name.get();
    }
    public StringProperty nameProperty() {
        return name;
    }
    public void setName(String name) {
        this.name.set(name);
    }
}
