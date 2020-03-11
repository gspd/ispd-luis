package gspd.ispd.fxgui.dag;

import gspd.ispd.fxgui.commons.*;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;

public class IconMenu extends VBox {


    ///////////////////////////////////////////////
    ///////////////// CONSTRUCTOR /////////////////
    ///////////////////////////////////////////////

    public IconMenu() {
        iconsGroup = new ToggleGroup();
        iconsGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            selectedItem.set((IconMenuItem) newValue);
        });
        // GENERATE THE UI
        createContent();
    }

    /**
     * The toggle group of all the clickable icons
     */
    private ToggleGroup iconsGroup;
    private void createContent() {
        VBox nodesBox = new VBox();

        nodesBox.getChildren().add(new IconMenuItem("Task", new NodeIcon(NodeIcon.TASK_BUILDER), iconsGroup));
        nodesBox.getChildren().add(new IconMenuItem("Activation", new NodeIcon(NodeIcon.ACTIVATION_BUILDER), iconsGroup));
        nodesBox.getChildren().add(new IconMenuItem("Synchronize", new NodeIcon(NodeIcon.SYNCHRONIZE_BUILDER), iconsGroup));
        nodesBox.getChildren().add(new IconMenuItem("Switch",  new NodeIcon(NodeIcon.SWITCH_BUILDER), iconsGroup));
        nodesBox.getChildren().add(new IconMenuItem("Timer", new NodeIcon(NodeIcon.TIMER_BUILDER), iconsGroup));

        TitledPane nodesPane = new TitledPane("Nodes", nodesBox);

        VBox edgesBox = new VBox();
        edgesBox.getChildren().add(new IconMenuItem("Precedence", new ArrowIcon(Arrow.PRECEDENCE_BUILDER, 0.0, 0.0, 20.0, 20.0), iconsGroup));
        edgesBox.getChildren().add(new IconMenuItem("Prefix", new ArrowIcon(Arrow.PREFIX_BUILDER, 0.0, 0.0, 20.0, 20.0), iconsGroup));
        edgesBox.getChildren().add(new IconMenuItem("Fail", new ArrowIcon(Arrow.FAIL_BUILDER, 0.0, 0.0, 20.0, 20.0), iconsGroup));

        TitledPane edgesPane = new TitledPane("Edges", edgesBox);

        VBox expansionBox = new VBox();
        expansionBox.getChildren().add(new Label("Iterative"));
        expansionBox.getChildren().add(new Label("Parallel"));
        expansionBox.getChildren().add(new Label("Recursive"));

        TitledPane expansionPane = new TitledPane("Expansions", expansionBox);

        super.getChildren().setAll(nodesPane, edgesPane, expansionPane);
        super.setMinWidth(100.0);
        super.setMaxHeight(Double.MAX_VALUE);
    }

    ///////////////////////////////////////////////////
    //////////////// PROPERTIES ///////////////////////
    ///////////////////////////////////////////////////

    /**
     * The selected menu item
     */
    private ObjectProperty<IconMenuItem> selectedItem = new SimpleObjectProperty<>(this, "selectedIcon", null);
    public IconMenuItem getSelectedItem() {
        if (selectedItem == null) {
            return null;
        }
        return selectedItemProperty().get();
    }
    public ReadOnlyObjectProperty<IconMenuItem> selectedItemProperty() {
        return selectedItem;
    }
}
