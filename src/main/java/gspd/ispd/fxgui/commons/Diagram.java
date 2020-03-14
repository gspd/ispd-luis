package gspd.ispd.fxgui.commons;

import javafx.scene.Group;
import javafx.scene.Node;

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
    // (begin) | decorations... | arrows ... | nodes... | (end)
    ///////////////////////////////////////////////////////////

    private int firstArrow = 0;
    private int edgesLen = 0;
    private boolean addEdge(EdgeIcon icon) {
        getChildren().add(firstArrow + edgesLen, icon);
        edgesLen += 1;
        firstNode += 1;
        return true;
    }

    private int firstNode = 0;
    private int nodesLen = 0;
    private boolean addNode(NodeIcon icon) {
        try {
            getChildren().add(firstNode + nodesLen, icon);
            nodesLen += 1;
            return true;
        } catch (Exception e) {
            System.out.println("Fail add " + icon + " in Diagram " + this + ": " + e);
            return false;
        }
    }

    public boolean add(Icon icon) {
        boolean added = false;
        if (icon.getIconType().isTypeOf(NodeIcon.NODE_TYPE)) {
            added = addNode((NodeIcon) icon);
        } else if (icon.getIconType().isTypeOf(EdgeIcon.EDGE_TYPE)) {
            added = addEdge((EdgeIcon) icon);
        }
        return added;
    }

    public void addAll(Icon... icons) {
        for (Icon icon : icons) {
            add(icon);
        }
    }

    public boolean remove(Icon icon) {
        return getChildren().remove(icon);
    }

    public void removeAll(Icon... icons) {
        for (Icon icon : icons) {
            remove(icon);
        }
    }

    ///////////////////////////////////////
    ////////////// METHODS ////////////////
    ///////////////////////////////////////

    /**
     * Returns a list of icons in diagram of the given type
     * @param type the icon type
     * @return a list of icons (as Nodes)
     */
    public List<Node> getIconsByType(IconType type) {
        return getIconsByTypeStream(type).collect(Collectors.toList());
    }

    /**
     * Returns a stream of icons in diagram of the given type
     * @param type the icon type
     * @return a stream of icons (as Nodes)
     */
    public Stream<Node> getIconsByTypeStream(IconType type) {
        return getChildren()
                .stream()
                .filter(node -> ((Icon) node).getIconType().isTypeOf(type));
    }
}
