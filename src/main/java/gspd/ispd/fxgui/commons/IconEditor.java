package gspd.ispd.fxgui.commons;

import javafx.beans.Observable;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

public abstract class IconEditor extends GridPane {

    public IconEditor() {
        iconProperty().addListener(this::iconChanged);
        setPadding(new Insets(5, 5, 5, 5));
        setVgap(5.0);
        setHgap(2.0);
        ColumnConstraints col0 = new ColumnConstraints();
        col0.setHgrow(Priority.ALWAYS);
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setHgrow(Priority.NEVER);
        getColumnConstraints().setAll(col0, col1);
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
