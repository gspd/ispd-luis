package gspd.ispd.fxgui.commons;

import javafx.beans.Observable;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.layout.GridPane;

public abstract class IconEditor extends GridPane {

    public IconEditor() {
        iconProperty().addListener(this::iconChanged);
    }

    private void iconChanged(ObservableValue<? extends Icon> observable, Icon oldValue, Icon newValue) {
        if (newValue != null) {
            setup(newValue);
        }
    }

    protected abstract void setup(Icon icon);

    /////////////////////////////////////////
    ///////////// PROPERTIES ////////////////
    /////////////////////////////////////////

    /**
     * The icon it is editing
     */
    private ObjectProperty<Icon> icon = new SimpleObjectProperty<>(this, "icon", null);
    public Icon getIcon() {
        return icon.get();
    }
    public ObjectProperty<Icon> iconProperty() {
        return icon;
    }
    public void setIcon(Icon icon) {
        this.icon.set(icon);
    }
}
