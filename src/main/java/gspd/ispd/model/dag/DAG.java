package gspd.ispd.model.dag;

import javafx.util.Pair;

import java.util.Map;

public class DAG<V,E> {

    private Map<DAGNode, V> nodes;
    private Map<Pair<DAGNode, DAGNode>, DAGEdge> edges;
    private Map<DAGEdge, E> edgesContent;

    public void addNode(DAGNode node, V content) {
        if (!nodes.containsKey(node)) {
            nodes.put(node, content);
        }
    }

    public void addNode(DAGNode node) {
        addNode(node, null);
    }

    public void setContent(DAGNode node, V content) {
        if (nodes.containsKey(node)) {
            nodes.put(node, content);
        }
    }

    public void setContent(DAGNode source, DAGNode destination, E content) {
        Pair<DAGNode, DAGNode> keyPair = new Pair<>(source, destination);
        if (edges.containsKey(keyPair)) {
            DAGEdge edge = edges.get(keyPair);

        }
    }

    private void connect(DAGNode source, DAGNode destination, E content) {

    }

    private void connect(DAGNode source, DAGNode destination) {
        connect(source, destination, null);
    }

}
