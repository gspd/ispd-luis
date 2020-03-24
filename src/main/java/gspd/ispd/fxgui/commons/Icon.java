package gspd.ispd.fxgui.commons;

import gspd.ispd.commons.ISPDType;
import javafx.beans.Observable;
import javafx.beans.property.*;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.event.EventTarget;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.util.Builder;

public abstract class Icon extends Group {

    public final static ISPDType ICON_TYPE = ISPDType.type("ICON_TYPE");

    ///////////////////////////////////////
    // ID System //////////////////////////
    ///////////////////////////////////////

    private static int id = 0;
    private void giveID() {
        setIconID("icon" + id);
        id++;
    }

    ///////////////////////////////////////////////
    ///////////////// CONSTRUCTOR /////////////////
    ///////////////////////////////////////////////

    public Icon(Builder<? extends Node> nodeBuilder, boolean selected) {
        giveID();
        selectedProperty().addListener(this::updateIcon);
        hoveredProperty().addListener(this::updateIcon);
        nodeBuilderProperty().addListener(this::builderChanged);
        contentProperty().addListener(this::contentChanged);
        // EVENTS
        // redirects all events that targets its children as
        // an event that targets itself
        addEventHandler(Event.ANY, event -> {
            EventTarget target = event.getTarget();
            if (target != this && ! (target instanceof Icon)) {
                Event.fireEvent(this, event);
                event.consume();
            }
        });
        // SETs
        setNodeBuilder(nodeBuilder);
        setSelected(selected);
        setType(ICON_TYPE);
    }

    public Icon(Builder<? extends Node> nodeBuilder) {
        this(nodeBuilder, false);
    }

    ////////////////////////////////////////////////
    /////////////// EVENT HANDLERS /////////////////
    ////////////////////////////////////////////////

    /**
     * Changes the content when changes the builder
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

    private void updateIcon(Observable observable) {
        updateIcon();
    }

    ///////////////////////////////////////////////////
    //////////////// ABSTRACT /////////////////////////
    ///////////////////////////////////////////////////

    public abstract Builder<? extends Icon> iconBuilder();

    protected abstract void updateIcon();

    protected abstract IconEditor editor();

    ///////////////////////////////////////////////////
    //////////////// PROPERTIES ///////////////////////
    ///////////////////////////////////////////////////

    /**
     * The icon id
     */
    private String iconID;
    public String getIconID() {
        return iconID;
    }
    public void setIconID(String iconID) {
        this.iconID = iconID;
    }

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
     * Whether this icon is active or not
     */
    private BooleanProperty active = new SimpleBooleanProperty(this, "active", false);
    public boolean isActive() {
        return active.get();
    }
    public BooleanProperty activeProperty() {
        return active;
    }
    public void setActive(boolean active) {
        this.active.set(active);
    }

    /**
     * Whether this icon is hovered ot not
     */
    private BooleanProperty hovered;
    public boolean isHovered() {
        return hoveredProperty().get();
    }
    public BooleanProperty hoveredProperty() {
        if (hovered == null) {
            hovered = new SimpleBooleanProperty(this, "hovered", false);
        }
        return hovered;
    }
    public void setHovered(boolean hovered) {
        hoveredProperty().set(hovered);
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

    /**
     * The icon type
     */
    private ObjectProperty<ISPDType> type = new SimpleObjectProperty<>(null, "iconType", ICON_TYPE);
    public ISPDType getType() {
        return type.get();
    }
    public ObjectProperty<ISPDType> typeProperty() {
        return type;
    }
    public void setType(ISPDType type) {
        this.type.set(type);
    }
}
