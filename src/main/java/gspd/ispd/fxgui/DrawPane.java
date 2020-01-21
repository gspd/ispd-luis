package gspd.ispd.fxgui;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Control;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SelectionModel;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public abstract class DrawPane extends Control {

    private ObjectProperty<SelectionModel<Node>> selectionModel;
    private ObjectProperty<ObservableList<Node>> items;
    private Pane drawPane;
    private ScrollPane scrollPane;

    public DrawPane() {
        this.scrollPane = new ScrollPane();
        this.drawPane = new Pane();
        this.scrollPane.setContent(drawPane);
        this.selectionModel = new SimpleObjectProperty<>(this, "selectionModel", new NodeSelectionModel(drawPane));
        this.items.set(drawPane.getChildren());
        drawPane.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

            }
        });
    }

    public SelectionModel<Node> getSelectionModel() {
        return selectionModel.get();
    }

    public ObjectProperty<SelectionModel<Node>> selectionModelProperty() {
        return selectionModel;
    }

    public void setSelectionModel(SelectionModel<Node> selectionModel) {
        this.selectionModel.set(selectionModel);
    }

    public ObservableList<Node> getItems() {
        return items.get();
    }

    public ObjectProperty<ObservableList<Node>> itemsProperty() {
        return items;
    }

    public void setItems(ObservableList<Node> items) {
        this.items.set(items);
    }

    public void add(Node node) {
        this.addNode(node);
    }

    public void remove(Node node) {
        this.removeNode(node);
    }

    public ObservableList<Node> getNodes() {
        return drawPane.getChildren();
    }

    private void addNode(Node node) {
        drawPane.getChildren().add(node);
    }

    private void removeNode(Node node) {
        drawPane.getChildren().remove(node);
    }
}