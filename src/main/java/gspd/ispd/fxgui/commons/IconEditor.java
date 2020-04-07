package gspd.ispd.fxgui.commons;

import javafx.beans.Observable;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public abstract class IconEditor extends GridPane {

    /////////////////////////////////
    /////////// CONSTANTS ///////////
    /////////////////////////////////

    protected final static double INPUT_WIDTH = 100.0;

    /////////////////////////////////
    /////////// CONSTRUCTOR /////////
    /////////////////////////////////

    private Text titleText;
    public IconEditor() {
        iconProperty().addListener(this::iconChanged);
        titleProperty().addListener(this::titleChanged);
        setPadding(new Insets(5, 5, 5, 5));
        setVgap(5.0);
        setHgap(2.0);
        ColumnConstraints col0 = new ColumnConstraints();
        col0.setHgrow(Priority.ALWAYS);
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setHgrow(Priority.NEVER);
        getColumnConstraints().setAll(col0, col1);
        titleText = new Text();
        titleText.setFont(Font.font(16));
        add(titleText, 0, 0, 2, 1);
    }

    private void iconChanged(ObservableValue<? extends Icon> observable, Icon oldValue, Icon newValue) {
        if (newValue != null) {
            setup(newValue);
        }
    }

    private void titleChanged(ObservableValue<? extends String> observable, String oldValue, String newValue) {
        titleText.setText(newValue);
    }

    protected abstract void setup(Icon icon);

    /////////////////////////////////////////
    ////////////// ACCESSORS ////////////////
    /////////////////////////////////////////

    public Text getTitleText() {
        return titleText;
    }

    /////////////////////////////////////////
    ///////////// PROPERTIES ////////////////
    /////////////////////////////////////////

    /**
     * The title of the editor
     */
    private StringProperty title = new SimpleStringProperty(this, "title", "");
    public String getTitle() {
        return title.get();
    }
    public StringProperty titleProperty() {
        return title;
    }
    public void setTitle(String title) {
        this.title.set(title);
    }

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
