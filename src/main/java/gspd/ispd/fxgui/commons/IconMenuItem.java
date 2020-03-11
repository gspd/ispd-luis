package gspd.ispd.fxgui.commons;

import javafx.beans.property.*;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.util.Builder;

public class IconMenuItem extends Label implements Toggle {

    ///////////////////////////////////////////////
    ///////////////// CONSTRUCTOR /////////////////
    ///////////////////////////////////////////////

    public IconMenuItem(String text, Icon graphic, ToggleGroup toggleGroup) {
        super("", graphic);
        // LISTENERS
        textProperty().addListener(this::textChanged);
        selectedProperty().addListener(this::selectionChanged);
        // EVENTS
        setOnMouseClicked(this::onClick);
        // toggleGroupProperty().addListener(this::toggleGroupChanged);
        // BINDINGS
        nodeBuilder.bind(((Icon)getGraphic()).nodeBuilderProperty());
        // UI
        setPadding(new Insets(4, 4, 4, 4));
        setMaxWidth(Double.MAX_VALUE);
        // SETs
        setText(text);
        setToggleGroup(toggleGroup);
        setSelected(false);
    }

    public IconMenuItem(String text, Icon graphic) {
        this(text, graphic, null);
    }

    public IconMenuItem(String text) {
        this(text, null, null);
    }

    public IconMenuItem(Icon icon) {
        this("", icon, null);
    }

    public IconMenuItem(String text, ToggleGroup toggleGroup) {
        this(text, null, toggleGroup);
    }

    public IconMenuItem(Icon icon, ToggleGroup toggleGroup) {
        this("", icon, toggleGroup);
    }

    ////////////////////////////////////////////////
    /////////////// EVENT HANDLERS /////////////////
    ////////////////////////////////////////////////

    public void textChanged(ObservableValue<? extends String> observable, String oldValue, String newValue) {
        super.setTooltip(new Tooltip(newValue));
    }

    private void selectionChanged(ObservableValue<? extends Boolean> observable, boolean oldValue, boolean newValue) {
        if (getToggleGroup() != null) {
            getToggleGroup().selectToggle(null);
            if (newValue) {
                getToggleGroup().selectToggle(this);
            }
        }
        Background back = null;
        if (newValue) {
            back = new Background(new BackgroundFill(Color.DEEPSKYBLUE, CornerRadii.EMPTY, Insets.EMPTY));
        }
        super.setBackground(back);
    }

    private void onClick(MouseEvent event) {
        if (event.getButton() == MouseButton.PRIMARY) {
            if (getToggleGroup().getSelectedToggle() == this) {
                getToggleGroup().selectToggle(null);
            } else {
                getToggleGroup().selectToggle(this);
            }
            event.consume();
        }
    }

    ///////////////////////////////////////////////////
    //////////////// PROPERTIES ///////////////////////
    ///////////////////////////////////////////////////

    /**
     * The node builder of the inner icon
     */
    private ObjectProperty<Builder<? extends Node>> nodeBuilder = new SimpleObjectProperty<>(this, "nodeBuilder", null);
    public Builder<? extends Node> getNodeBuilder() {
        return nodeBuilder.get();
    }
    public ReadOnlyObjectProperty<Builder<? extends Node>> nodeBuilderProperty() {
        return nodeBuilder;
    }

    /**
     * The selected property
     */
    private BooleanProperty selected = new SimpleBooleanProperty(this, "selected", false);
    @Override
    public boolean isSelected() {
        return selected.get();
    }
    @Override
    public BooleanProperty selectedProperty() {
        return selected;
    }
    @Override
    public void setSelected(boolean selected) {
        this.selected.set(selected);
    }

    /**
     * The icon toggle group
     */
    private ObjectProperty<ToggleGroup> toggleGroup = new SimpleObjectProperty<>(null, "toggleGroup", null);
    @Override
    public ToggleGroup getToggleGroup() {
        return toggleGroup.get();
    }
    @Override
    public ObjectProperty<ToggleGroup> toggleGroupProperty() {
        return toggleGroup;
    }
    @Override
    public void setToggleGroup(ToggleGroup toggleGroup) {
        this.toggleGroup.set(toggleGroup);
    }
}
