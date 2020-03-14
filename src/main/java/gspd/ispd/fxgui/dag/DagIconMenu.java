package gspd.ispd.fxgui.dag;

import gspd.ispd.fxgui.commons.*;
import gspd.ispd.fxgui.dag.icons.*;
import gspd.ispd.fxgui.dag.shapes.*;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;

public class DagIconMenu extends ScrollPane {

    ///////////////////////////////////////////////
    ///////////////// CONSTRUCTOR /////////////////
    ///////////////////////////////////////////////

    public DagIconMenu() {
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

        VBox miscBox = new VBox();
        miscBox.getChildren().add(new Label("Text"));

        TitledPane miscPane = new TitledPane("Misc", miscBox);

        VBox nodesBox = new VBox();
        nodesBox.getChildren().add(new IconMenuItem("Task", new TaskIcon(), iconsGroup));
        nodesBox.getChildren().add(new IconMenuItem("Activation", new ActivationIcon(), iconsGroup));
        nodesBox.getChildren().add(new IconMenuItem("Synchronize", new SynchronizeIcon(), iconsGroup));
        nodesBox.getChildren().add(new IconMenuItem("Switch",  new SwitchIcon(), iconsGroup));
        nodesBox.getChildren().add(new IconMenuItem("Timer", new TimerIcon(), iconsGroup));

        TitledPane nodesPane = new TitledPane("Nodes", nodesBox);

        VBox edgesBox = new VBox();
        edgesBox.getChildren().add(new IconMenuItem("Precedence", new PrecedenceIcon(0.0, 0.0, 20.0, 20.0), iconsGroup));
        edgesBox.getChildren().add(new IconMenuItem("Prefix", new PrefixIcon(0.0, 0.0, 20.0, 20.0), iconsGroup));
        edgesBox.getChildren().add(new IconMenuItem("Fail", new FailIcon(0.0, 0.0, 20.0, 20.0), iconsGroup));

        TitledPane edgesPane = new TitledPane("Edges", edgesBox);

        VBox expansionBox = new VBox();
        expansionBox.getChildren().add(new IconMenuItem("Iterative", new IterativeExpansionIcon(), iconsGroup));
        expansionBox.getChildren().add(new IconMenuItem("Parallel", new ParallelExpansionIcon(), iconsGroup));
        expansionBox.getChildren().add(new IconMenuItem("Recursive", new RecursiveExpansionIcon(), iconsGroup));
        expansionBox.getChildren().add(new IconMenuItem("Recursion", new RecursionIcon(), iconsGroup));

        TitledPane expansionPane = new TitledPane("Expansions", expansionBox);

        VBox content = new VBox();
        content.getChildren().setAll(miscPane, nodesPane, edgesPane, expansionPane);
        content.minWidthProperty().bind(super.widthProperty());

        super.setContent(content);
        super.setHbarPolicy(ScrollBarPolicy.NEVER);
        super.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
        super.setFitToWidth(true);
        super.setFitToHeight(true);
        super.setMinWidth(120.0);
        super.setPrefWidth(150.0);
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
