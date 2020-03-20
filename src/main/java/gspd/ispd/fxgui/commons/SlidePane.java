package gspd.ispd.fxgui.commons;

import javafx.beans.Observable;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.layout.*;

/**
 *
 * ,--------------------.
 * |                    |
 * |     Content        | <- (StackPane)
 * |                    |
 * |--------------------|
 * | LB              RB | <- (HBox)
 * `--------------------'
 */
public class SlidePane extends VBox {

    public SlidePane(Button leftButton, Button rightButton) {
        createUI();
        contentProperty().addListener((obs, o, n) -> {
            if (n != null) {
                contentPane.getChildren().setAll(n);
            }
        });
        leftButtonProperty().addListener(this::buttonChanged);
        rightButtonProperty().addListener(this::buttonChanged);
        setLeftButton(leftButton);
        setRightButton(rightButton);
    }

    public SlidePane() {
        this(null, null);
    }

    private void buttonChanged(Observable observable) {
        bottomBox.getChildren().clear();
        if (getLeftButton() != null) {
            HBox.setHgrow(getLeftButton(), Priority.NEVER);
            bottomBox.getChildren().add(getLeftButton());
        }
        bottomBox.getChildren().add(spacing);
        if (getRightButton() != null) {
            HBox.setHgrow(getRightButton(), Priority.NEVER);
            bottomBox.getChildren().add(getRightButton());
        }
    }

    private StackPane contentPane;
    private HBox bottomBox;
    private Pane spacing;
    private void createUI() {
        contentPane = new StackPane();
        contentPane.setAlignment(Pos.CENTER);
        VBox.setVgrow(contentPane, Priority.ALWAYS);
        bottomBox = new HBox();
        bottomBox.setPadding(new Insets(5));
        VBox.setVgrow(bottomBox, Priority.NEVER);
        spacing = new Pane();
        HBox.setHgrow(spacing, Priority.ALWAYS);
        getChildren().add(contentPane);
        getChildren().add(new Separator());
        getChildren().add(bottomBox);
    }

    //////////////////////////////////////////
    ////////////// PROPERTIES ////////////////
    //////////////////////////////////////////

    /**
     * The content node
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
     * The right button
     */
    private ObjectProperty<Button> rightButton = new SimpleObjectProperty<>(this, "rightButton", null);
    public Button getRightButton() {
        return rightButton.get();
    }
    public ObjectProperty<Button> rightButtonProperty() {
        return rightButton;
    }
    public void setRightButton(Button rightButton) {
        this.rightButton.set(rightButton);
    }

    /**
     * The left button
     */
    private ObjectProperty<Button> leftButton = new SimpleObjectProperty<>(this, "leftButton", null);
    public Button getLeftButton() {
        return leftButton.get();
    }
    public ObjectProperty<Button> leftButtonProperty() {
        return leftButton;
    }
    public void setLeftButton(Button leftButton) {
        this.leftButton.set(leftButton);
    }
}
