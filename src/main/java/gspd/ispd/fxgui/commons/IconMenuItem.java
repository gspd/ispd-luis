package gspd.ispd.fxgui.commons;

import javafx.beans.property.*;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.util.Builder;

public class IconMenuItem extends ToggleButton implements Toggle {

    ///////////////////////////////////////////////
    ///////////////// CONSTRUCTOR /////////////////
    ///////////////////////////////////////////////

    public IconMenuItem(String text, Icon graphic, ToggleGroup toggleGroup) {
        super("", graphic);
        // LISTENERS
        textProperty().addListener(this::textChanged);
        // UI
        setPadding(new Insets(4, 4, 4, 4));
        setMaxWidth(Double.MAX_VALUE);
        setAlignment(Pos.CENTER_LEFT);
        // SETs
        iconBuilder.set(graphic.iconBuilder());
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

    ///////////////////////////////////////////////////
    //////////////// PROPERTIES ///////////////////////
    ///////////////////////////////////////////////////

    /**
     * The node builder of the inner icon
     */
    private ObjectProperty<Builder<? extends Icon>> iconBuilder = new SimpleObjectProperty<>(this, "iconBuilder", null);
    public Builder<? extends Icon> getIconBuilder() {
        return iconBuilder.get();
    }
    public ReadOnlyObjectProperty<Builder<? extends Icon>> iconBuilderProperty() {
        return iconBuilder;
    }

}
