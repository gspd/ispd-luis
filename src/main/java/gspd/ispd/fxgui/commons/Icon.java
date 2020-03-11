package gspd.ispd.fxgui.commons;

import javafx.beans.property.*;
import javafx.beans.value.ObservableValue;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.util.Builder;

public abstract class Icon extends Group {

    ///////////////////////////////////////////////
    ///////////////// CONSTRUCTOR /////////////////
    ///////////////////////////////////////////////

    public Icon(Builder<? extends Node> nodeBuilder, boolean selected) {
        nodeBuilderProperty().addListener(this::builderChanged);
        selectedProperty().addListener(this::selectionChanged);
        contentProperty().addListener(this::contentChanged);
        setNodeBuilder(nodeBuilder);
        setSelected(selected);
    }

    public Icon(Builder<? extends Node> nodeBuilder) {
        this(nodeBuilder, false);
    }

    ////////////////////////////////////////////////
    /////////////// EVENT HANDLERS /////////////////
    ////////////////////////////////////////////////

    /**
     * Chages the content when changes the builder
     */
    protected void builderChanged(ObservableValue<? extends Builder<? extends Node>> observable, Builder<? extends Node> oldValue, Builder<? extends Node> newValue) {
        setContent(newValue.build());
    }

    /**
     * Exchange the old content to the new one
     */
    private void contentChanged(ObservableValue<? extends Node> observable, Node oldValue, Node newValue) {
        super.getChildren().remove(oldValue);
        super.getChildren().add(newValue);
    }

    /**
     * Decorate the node whether it is marked as selected or not
     */
    private void selectionChanged(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
        updateSelection(newValue);
    }

    protected abstract void updateSelection(boolean selected);

    ///////////////////////////////////////////////////
    //////////////// PROPERTIES ///////////////////////
    ///////////////////////////////////////////////////

    /**
     * Whether this icon is selected or not
     */
    private BooleanProperty selected;
    public boolean isSelected() {
        return selectedProperty().get();
    }
    public BooleanProperty selectedProperty() {
        if (selected == null) {
            selected = new SimpleBooleanProperty(this, "selected", false);
        }
        return selected;
    }
    public void setSelected(boolean selected) {
        selectedProperty().set(selected);
    }

    /**
     * The shape builder of the icon
     */
    private ObjectProperty<Builder<? extends Node>> nodeBuilder;
    public Builder<? extends Node> getNodeBuilder() {
        return nodeBuilderProperty().get();
    }
    public ObjectProperty<Builder<? extends Node>> nodeBuilderProperty() {
        if (nodeBuilder == null) {
            nodeBuilder = new SimpleObjectProperty<>(this, "shapeBuilder", null);
        }
        return nodeBuilder;
    }
    public void setNodeBuilder(Builder<? extends Node> nodeBuilder) {
        nodeBuilderProperty().set(nodeBuilder);
    }


    /**
     * The content node of the icon
     */
    private ObjectProperty<Node> content;
    public Node getContent() {
        return contentProperty().get();
    }
    public ObjectProperty<Node> contentProperty() {
        if (content == null) {
            content = new SimpleObjectProperty<>(this, "content", null);
        }
        return content;
    }
    public void setContent(Node content) {
        contentProperty().set(content);
    }
}
