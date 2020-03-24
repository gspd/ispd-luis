package gspd.ispd.fxgui;

import javafx.beans.value.ObservableValue;
import javafx.event.EventType;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.DataFormat;
import javafx.scene.layout.*;

import java.util.HashMap;
import java.util.Map;

public class WorkloadWindow extends AnchorPane {

    private Pane contentPane;
    private Map<Toggle, Node> toggleContentMap;

    public WorkloadWindow() {

        toggleContentMap = new HashMap<>();

        ToggleGroup workloadTypeToggle = new ToggleGroup();
        workloadTypeToggle.selectedToggleProperty().addListener(this::toggleChanged);

        RadioButton randomButton = new RadioButton("Random");
        randomButton.setToggleGroup(workloadTypeToggle);
        toggleContentMap.put(randomButton, new Label("Random Label"));

        RadioButton forNodeButton = new RadioButton("For Node");
        forNodeButton.setToggleGroup(workloadTypeToggle);
        toggleContentMap.put(forNodeButton, new Label("For Node Label"));

        RadioButton dagButton = new RadioButton("DAG");
        dagButton.setToggleGroup(workloadTypeToggle);
        toggleContentMap.put(dagButton, new Label("DAG Label"));

        RadioButton traceButton = new RadioButton("Traces");
        traceButton.setToggleGroup(workloadTypeToggle);
        toggleContentMap.put(traceButton, new Label("Trace Label"));

        HBox topButtons = new HBox(randomButton, forNodeButton, dagButton, traceButton);
        topButtons.setAlignment(Pos.CENTER);
        topButtons.setSpacing(15);
        VBox.setVgrow(topButtons, Priority.NEVER);

        Button okButton = new Button("OK");
        okButton.setDefaultButton(true);

        Button cancelButton = new Button("Cancel");

        HBox bottomButtons = new HBox(okButton, cancelButton);
        bottomButtons.setAlignment(Pos.CENTER);
        bottomButtons.setSpacing(10);
        VBox.setVgrow(bottomButtons, Priority.NEVER);

        contentPane = new Pane();
        VBox.setMargin(contentPane, new Insets(5, 0, 5, 0));
        VBox.setVgrow(contentPane, Priority.ALWAYS);

        VBox layout = new VBox(topButtons, contentPane, bottomButtons);
        layout.setPadding(new Insets(5, 5, 5, 5));
        AnchorPane.setRightAnchor(layout, 0.0);
        AnchorPane.setTopAnchor(layout, 0.0);
        AnchorPane.setLeftAnchor(layout, 0.0);
        AnchorPane.setBottomAnchor(layout, 0.0);

        getChildren().setAll(layout);
        // initialization
        randomButton.setSelected(true);
    }

    private void toggleChanged(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
        try {
            Node newContent = toggleContentMap.get(newValue);
            contentPane.getChildren().setAll(newContent);
        } catch (NullPointerException e) {
            System.out.println("'content' is not set: " + e);
        }
    }

}
