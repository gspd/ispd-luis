package gspd.ispd.fxgui.workload;

import gspd.ispd.fxgui.workload.dag.DAG;
import gspd.ispd.fxgui.workload.dag.DagEditor;
import gspd.ispd.fxgui.workload.dag.GenerateWorkloadEntry;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.StringConverter;

public class GenerateWorkloadPane extends VBox {

    public GenerateWorkloadPane() {
        createUI();
    }

    private ComboBox<String> userInput;
    private ComboBox<String> schedulerInput;
    private Spinner<Integer> quantityInput;
    private RadioButton randomRadio;
    private RadioButton dagRadio;
    private ToggleGroup typeToggle;
    private Button addDagButton;
    private Button editDagButton;
    private Button addButton;
    private Button removeButton;
    private ComboBox<DAG> dagChooser;
    private TableView<GenerateWorkloadEntry> table;
    private Spinner<Double> compMinInput;
    private Spinner<Double> compMaxInput;
    private Spinner<Double> commuMinInput;
    private Spinner<Double> commuMaxInput;
    private void createUI() {
        userInput = new ComboBox<>();
        userInput.setEditable(true);
        schedulerInput = new ComboBox<>();
        schedulerInput.setEditable(true);
        quantityInput = new Spinner<>();
        quantityInput.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE, 1));
        quantityInput.getStyleClass().add(Spinner.STYLE_CLASS_ARROWS_ON_RIGHT_HORIZONTAL);
        addDagButton = new Button("+");
        addDagButton.setOnAction(e -> {
            openEditor();
        });
        editDagButton = new Button("!");
        dagChooser = new ComboBox<>();
        dagChooser.setConverter(new StringConverter<DAG>() {
            @Override
            public String toString(DAG object) {
                if (object == null) {
                    return "";
                }
                return object.getName();
            }
            @Override
            public DAG fromString(String string) {
                return DAG.getDAG(string);
            }
        });
        typeToggle = new ToggleGroup();
        randomRadio = new RadioButton("Random");
        randomRadio.setToggleGroup(typeToggle);
        dagRadio = new RadioButton("DAG");
        dagRadio.setToggleGroup(typeToggle);
        GenerateWorkloadEntry tempEntry = new GenerateWorkloadEntry();
        TableColumn<GenerateWorkloadEntry, String> userColumn = new TableColumn<>("User");
        userColumn.setCellValueFactory(new PropertyValueFactory<>(tempEntry.userProperty().getName()));
        TableColumn<GenerateWorkloadEntry, String> schedulerColumn = new TableColumn<>("Scheduler");
        schedulerColumn.setCellValueFactory(new PropertyValueFactory<>(tempEntry.schedulerProperty().getName()));
        TableColumn<GenerateWorkloadEntry, Integer> quantityColumn = new TableColumn<>("Quantity");
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>(tempEntry.quantityProperty().getName()));
        TableColumn<GenerateWorkloadEntry, String> dataColumn = new TableColumn<>("Data");
        dataColumn.setCellValueFactory(new PropertyValueFactory<>("data"));
        table = new TableView<>();
        table.getColumns().clear();
        table.getColumns().add(userColumn);
        table.getColumns().add(schedulerColumn);
        table.getColumns().add(quantityColumn);
        table.getColumns().add(dataColumn);
        HBox buttons = new HBox();
        buttons.setSpacing(5.0);
        buttons.setAlignment(Pos.CENTER_RIGHT);
        addButton = new Button("Add");
        addButton.setOnAction(e -> {
            table.getItems().add(new GenerateWorkloadEntry());
        });
        removeButton = new Button("Remove");
        removeButton.setOnAction(e -> {
            table.getItems().removeAll(table.getSelectionModel().getSelectedItems());
        });
        buttons.getChildren().addAll(addButton, removeButton);
        GridPane configGrid = new GridPane();
        configGrid.setHgap(5.0);
        configGrid.setVgap(5.0);
        configGrid.add(new Label("User"), 0, 0);
        configGrid.add(userInput, 1, 0);
        configGrid.add(new Label("Scheduler"), 0, 1);
        configGrid.add(schedulerInput, 1, 1);
        configGrid.add(new Label("Quantity"), 0, 2);
        configGrid.add(quantityInput, 1, 2);
        configGrid.add(randomRadio, 2, 0);
        configGrid.add(dagRadio, 3, 0);
        configGrid.disableProperty().bind(table.getSelectionModel().selectedItemProperty().isNull());
        StackPane configStack = new StackPane();
        configGrid.add(configStack, 2, 1, 2, 3);
        GridPane dagGrid = new GridPane();
        dagGrid.setHgap(5.0);
        dagGrid.add(dagChooser, 0, 0);
        dagGrid.add(addDagButton, 1, 0);
        dagGrid.add(editDagButton, 2, 0);
        GridPane randomGrid = new GridPane();
        randomGrid.setVgap(5.0);
        randomGrid.setHgap(5.0);
        compMinInput = new Spinner<>(new SpinnerValueFactory.DoubleSpinnerValueFactory(0.0, Double.MAX_VALUE));
        compMinInput.setEditable(true);
        compMaxInput = new Spinner<>(new SpinnerValueFactory.DoubleSpinnerValueFactory(0.0, Double.MAX_VALUE));
        compMaxInput.setEditable(true);
        commuMinInput = new Spinner<>(new SpinnerValueFactory.DoubleSpinnerValueFactory(0.0, Double.MAX_VALUE));
        commuMinInput.setEditable(true);
        commuMaxInput = new Spinner<>(new SpinnerValueFactory.DoubleSpinnerValueFactory(0.0, Double.MAX_VALUE));
        compMaxInput.setEditable(true);
        randomGrid.add(new Label("Computing"), 0, 1);
        randomGrid.add(new Label("Communication"), 0, 2);
        randomGrid.add(new Label("Min"), 1, 0);
        randomGrid.add(compMinInput, 1, 1);
        randomGrid.add(compMaxInput, 1, 2);
        randomGrid.add(new Label("Max"), 2, 0);
        randomGrid.add(commuMinInput, 2, 1);
        randomGrid.add(commuMaxInput, 2, 2);
        typeToggle.selectedToggleProperty().addListener(e -> {
            if (typeToggle.getSelectedToggle() == randomRadio) {
                configStack.getChildren().setAll(randomGrid);
            } else if (typeToggle.getSelectedToggle() == dagRadio) {
                configStack.getChildren().setAll(dagGrid);
            }
        });
        typeToggle.selectToggle(randomRadio);
        /////////
        setPadding(new Insets(10.0));
        setSpacing(10.0);
        getChildren().add(table);
        getChildren().add(buttons);
        getChildren().add(configGrid);
    }

    private Stage dagStage;
    private void openEditor() {
        if (dagStage == null) {
            dagStage = new Stage();
            dagStage.initModality(Modality.WINDOW_MODAL);
        }
        DagEditor dagEditor = new DagEditor();
        dagStage.setScene(new Scene(dagEditor));
        dagStage.showAndWait();
    }

}
