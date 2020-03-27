package gspd.ispd.fxgui.commons;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class IconConfigPane extends VBox {

    ///////////////////////////////////////
    ///////////// CONSTRUCTOR /////////////
    ///////////////////////////////////////

    public IconConfigPane() {
        iconProperty().addListener(this::iconChanged);
        contentProperty().addListener(this::contentChanged);
        createContent();
        setIcon(null);
    }

    private Label titleLabel;
    private void createContent() {
        titleLabel = new Label("");
        VBox.setVgrow(titleLabel, Priority.NEVER);
        VBox.setVgrow(emptyContent, Priority.ALWAYS);
        getChildren().addAll(titleLabel, emptyContent);
    }

    ///////////////////////////////////////
    ///////////// HANDLERS ////////////////
    ///////////////////////////////////////

    private void iconChanged(ObservableValue<? extends Icon> observable, Icon oldValue, Icon newValue) {
        if (newValue == null) {
            setContent(null);
        } else {
            setContent(newValue.editor());
        }
    }

    private Node emptyContent = new StackPane(new Label("Select an icon"));
    private void contentChanged(ObservableValue<? extends Node> observable, Node oldValue, Node newValue) {
        if (newValue == null) {
            VBox.setVgrow(emptyContent, Priority.ALWAYS);
            getChildren().set(1, emptyContent);
        } else {
            VBox.setVgrow(newValue, Priority.ALWAYS);
            getChildren().set(1, newValue);
        }
    }

    ///////////////////////////////////////
    ///////////// PROPERTIES //////////////
    ///////////////////////////////////////

    /**
     * The content of the configuration
     */
    private ObjectProperty<Node> content = new SimpleObjectProperty<>(this, "content", null);
    public Node getContent() {
        return content.get();
    }
    public ObjectProperty<Node> contentProperty() {
        return content;
    }
    public void setContent(Node content) {
        this.content.set(content);
    }

    /**
     * The icon the config pane is viewing/editing
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
