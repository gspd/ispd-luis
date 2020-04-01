package gspd.ispd.fxgui.workload;

import gspd.ispd.ISPD;
import gspd.ispd.commons.StringConstants;
import gspd.ispd.fxgui.workload.dag.DAG;
import gspd.ispd.fxgui.workload.dag.DagEditor;
import gspd.ispd.gui.iconico.grade.DesenhoGrade;
import javafx.beans.Observable;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GenerateWorkloadPane extends VBox {

    public GenerateWorkloadPane() {
        createContent();
        initEvents();
    }

    private void workloadDataChanged(Observable observable) {
        GenerateWorkloadEntry entry = table.getSelectionModel().getSelectedItem();
        Toggle selected = typeToggle.getSelectedToggle();
        if (selected == randomRadio) {
            entry.setType(GenerateWorkloadEntry.RANDOM_TYPE);
            String dataString =
                    compMinInput.getValue() + " " +
                    compMaxInput.getValue() + " " +
                    commuMinInput.getValue() + " " +
                    commuMaxInput.getValue();
            entry.setData(dataString);
        } else if (selected == dagRadio) {
            entry.setType(GenerateWorkloadEntry.DAG_TYPE);
            String dataString = dagChooser.getValue() == null ? "<null>" : dagChooser.getValue();
            entry.setData(dataString);
        }
        table.refresh();
    }

    private void tableSelectionChanged(Observable observable) {
        GenerateWorkloadEntry entry = table.getSelectionModel().getSelectedItem();
        if (entry == null) {
            userInput.getSelectionModel().select(null);
            schedulerInput.getSelectionModel().select(null);
            quantityInput.getValueFactory().setValue(0);
            arrivalTimeInput.getValueFactory().setValue(0.0);
            compMinInput.getValueFactory().setValue(0.0);
            compMaxInput.getValueFactory().setValue(0.0);
            commuMinInput.getValueFactory().setValue(0.0);
            commuMaxInput.getValueFactory().setValue(0.0);
            dagChooser.setValue(null);
            typeToggle.selectToggle(randomRadio);
        } else {
            userInput.getSelectionModel().select(entry.getUser());
            schedulerInput.getSelectionModel().select(entry.getScheduler());
            quantityInput.getValueFactory().setValue(entry.getQuantity());
            arrivalTimeInput.getValueFactory().setValue(entry.getArrivalTime());
            if (entry.getType().equals(GenerateWorkloadEntry.RANDOM_TYPE)) {
                typeToggle.selectToggle(randomRadio);
                Scanner scanner = new Scanner(entry.getData());
                compMinInput.getValueFactory().setValue(scanner.nextDouble());
                compMaxInput.getValueFactory().setValue(scanner.nextDouble());
                commuMinInput.getValueFactory().setValue(scanner.nextDouble());
                commuMaxInput.getValueFactory().setValue(scanner.nextDouble());
            } else if (entry.getType().equals(GenerateWorkloadEntry.DAG_TYPE)) {
                typeToggle.selectToggle(dagRadio);
                if (entry.getData().equals("<null>")) {
                    dagChooser.setValue(null);
                } else {
                    List<DAG> elegibleDags = getDesenhoGrade()
                            .getDags()
                            .stream()
                            .filter(d -> d.getName().equals(entry.getData()))
                            .collect(Collectors.toList());
                    DAG dag = null;
                    if (elegibleDags.size() > 0) {
                        dag = elegibleDags.get(0);
                    }
                    dagChooser.setValue(dag.getName());
                }
            }
        }
    }

    private ComboBox<String> userInput;
    private ComboBox<String> schedulerInput;
    private Spinner<Integer> quantityInput;
    private Spinner<Double> arrivalTimeInput;
    private RadioButton randomRadio;
    private RadioButton dagRadio;
    private ToggleGroup typeToggle;
    private Button addDagButton;
    private Button editDagButton;
    private Button addButton;
    private Button duplicateButton;
    private Button removeButton;
    private ComboBox<String> dagChooser;
    private TableView<GenerateWorkloadEntry> table;
    private Spinner<Double> compMinInput;
    private Spinner<Double> compMaxInput;
    private Spinner<Double> commuMinInput;
    private Spinner<Double> commuMaxInput;
    private static final double INPUT_WIDTH = 100.0;
    private void createContent() {
        userInput = new ComboBox<>();
        userInput.setPrefWidth(INPUT_WIDTH);
        schedulerInput = new ComboBox<>();
        schedulerInput.setPrefWidth(INPUT_WIDTH);
        quantityInput = new Spinner<>();
        quantityInput.setEditable(true);
        quantityInput.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE, 1));
        quantityInput.getStyleClass().add(Spinner.STYLE_CLASS_ARROWS_ON_RIGHT_HORIZONTAL);
        quantityInput.setPrefWidth(INPUT_WIDTH);
        arrivalTimeInput = new Spinner<>();
        arrivalTimeInput.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0, Double.MAX_VALUE, 0.5));
        arrivalTimeInput.setEditable(true);
        arrivalTimeInput.getStyleClass().add(Spinner.STYLE_CLASS_ARROWS_ON_RIGHT_HORIZONTAL);
        arrivalTimeInput.setPrefWidth(INPUT_WIDTH);
        addDagButton = new Button();
        addDagButton.setGraphic(new ImageView(new Image(ISPD.class.getResource(StringConstants.PLUS_ICON_PATH).toExternalForm())));
        editDagButton = new Button();
        editDagButton.setGraphic(new ImageView(new Image(ISPD.class.getResource(StringConstants.EDIT_ICON_PATH).toExternalForm())));
        dagChooser = new ComboBox<>();
        dagChooser.setPrefWidth(INPUT_WIDTH);
        typeToggle = new ToggleGroup();
        randomRadio = new RadioButton("Random");
        randomRadio.setToggleGroup(typeToggle);
        dagRadio = new RadioButton("DAG");
        dagRadio.setToggleGroup(typeToggle);
        TableColumn<GenerateWorkloadEntry, String> userColumn = new TableColumn<>("User");
        userColumn.setCellValueFactory(new PropertyValueFactory<>(GenerateWorkloadEntry.USER_PROPERTY));
        TableColumn<GenerateWorkloadEntry, String> schedulerColumn = new TableColumn<>("Scheduler");
        schedulerColumn.setCellValueFactory(new PropertyValueFactory<>(GenerateWorkloadEntry.SCHEDULER_PROPERTY));
        TableColumn<GenerateWorkloadEntry, Integer> quantityColumn = new TableColumn<>("Quantity");
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>(GenerateWorkloadEntry.QUANTITY_PROPERTY));
        TableColumn<GenerateWorkloadEntry, Double> arrivalColumn = new TableColumn<>("Arrival Time");
        arrivalColumn.setCellValueFactory(new PropertyValueFactory<>(GenerateWorkloadEntry.ARRIVAL_TIME_PROPERTY));
        TableColumn<GenerateWorkloadEntry, String> typeColumn = new TableColumn<>("Type");
        typeColumn.setCellValueFactory(new PropertyValueFactory<>(GenerateWorkloadEntry.TYPE_PROPERTY));
        TableColumn<GenerateWorkloadEntry, String> dataColumn = new TableColumn<>("Data");
        dataColumn.setCellValueFactory(new PropertyValueFactory<>(GenerateWorkloadEntry.DATA_PROPERTY));
        table = new TableView<>();
        table.getColumns().clear();
        table.getColumns().add(userColumn);
        table.getColumns().add(schedulerColumn);
        table.getColumns().add(quantityColumn);
        table.getColumns().add(arrivalColumn);
        table.getColumns().add(typeColumn);
        table.getColumns().add(dataColumn);
        HBox buttons = new HBox();
        buttons.setSpacing(5.0);
        buttons.setAlignment(Pos.CENTER_RIGHT);
        addButton = new Button("Add");
        removeButton = new Button("Remove");
        duplicateButton = new Button("Duplicate");
        buttons.getChildren().add(duplicateButton);
        buttons.getChildren().add(removeButton);
        buttons.getChildren().add(addButton);
        GridPane configGrid = new GridPane();
        configGrid.setHgap(5.0);
        configGrid.setVgap(5.0);
        configGrid.add(new Label("User"), 0, 0);
        configGrid.add(userInput, 1, 0);
        configGrid.add(new Label("Scheduler"), 0, 1);
        configGrid.add(schedulerInput, 1, 1);
        configGrid.add(new Label("Quantity"), 0, 2);
        configGrid.add(quantityInput, 1, 2);
        configGrid.add(new Label("Arrival Time"), 0, 3);
        configGrid.add(arrivalTimeInput, 1, 3);
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
        compMinInput.setPrefWidth(INPUT_WIDTH);
        compMaxInput = new Spinner<>(new SpinnerValueFactory.DoubleSpinnerValueFactory(0.0, Double.MAX_VALUE));
        compMaxInput.setEditable(true);
        compMaxInput.setPrefWidth(INPUT_WIDTH);
        commuMinInput = new Spinner<>(new SpinnerValueFactory.DoubleSpinnerValueFactory(0.0, Double.MAX_VALUE));
        commuMinInput.setEditable(true);
        commuMinInput.setPrefWidth(INPUT_WIDTH);
        commuMaxInput = new Spinner<>(new SpinnerValueFactory.DoubleSpinnerValueFactory(0.0, Double.MAX_VALUE));
        commuMaxInput.setEditable(true);
        commuMaxInput.setPrefWidth(INPUT_WIDTH);
        randomGrid.add(new Label("Computing"), 0, 1);
        randomGrid.add(new Label("Communication"), 0, 2);
        randomGrid.add(new Label("Min"), 1, 0);
        randomGrid.add(compMinInput, 1, 1);
        randomGrid.add(compMaxInput, 2, 1);
        randomGrid.add(new Label("Max"), 2, 0);
        randomGrid.add(commuMinInput, 1, 2);
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
        setSpacing(10.0);
        getChildren().add(table);
        getChildren().add(buttons);
        getChildren().add(configGrid);
    }

    private void initEvents() {
        addDagButton.setOnAction(e -> {
            openEditor(new DAG());
        });
        editDagButton.disableProperty().bind(dagChooser.valueProperty().isNull());
        editDagButton.setOnAction(e -> {
            List<DAG> elegibleDags = getDesenhoGrade()
                    .getDags()
                    .stream()
                    .filter(d -> d.getName().equals(dagChooser.getValue()))
                    .collect(Collectors.toList());
            if (elegibleDags.size() > 0) {
                openEditor(elegibleDags.get(0));
            }
        });
        table.getSelectionModel().selectedItemProperty().addListener(this::tableSelectionChanged);
        userInput.valueProperty().addListener((obs, o, n)-> {
            table.getSelectionModel().getSelectedItem().setUser(n);
            table.refresh();
        });
        schedulerInput.valueProperty().addListener((obs, o, n) -> {
            table.getSelectionModel().getSelectedItem().setScheduler(n);
            table.refresh();
        });
        quantityInput.valueProperty().addListener((obs, o, n) -> {
            table.getSelectionModel().getSelectedItem().setQuantity(n);
            table.refresh();
        });
        arrivalTimeInput.valueProperty().addListener((obs, o, n) -> {
            table.getSelectionModel().getSelectedItem().setArrivalTime(n);
            table.refresh();
        });
        addButton.setOnAction(e -> {
            table.getItems().add(new GenerateWorkloadEntry());
        });
        removeButton.disableProperty().bind(table.getSelectionModel().selectedItemProperty().isNull());
        removeButton.setOnAction(e -> {
            table.getItems().remove(table.getSelectionModel().getSelectedIndex());
        });
        duplicateButton.disableProperty().bind(table.getSelectionModel().selectedItemProperty().isNull());
        duplicateButton.setOnAction(e -> {
            GenerateWorkloadEntry entry = table.getSelectionModel().getSelectedItem();
            GenerateWorkloadEntry newEntry = entry.clone();
            table.getItems().add(newEntry);
        });
        typeToggle.selectedToggleProperty().addListener(this::workloadDataChanged);
        compMinInput.valueProperty().addListener(this::workloadDataChanged);
        compMaxInput.valueProperty().addListener(this::workloadDataChanged);
        commuMinInput.valueProperty().addListener(this::workloadDataChanged);
        commuMaxInput.valueProperty().addListener(this::workloadDataChanged);
        dagChooser.valueProperty().addListener(this::workloadDataChanged);
        // PROPERTIES LISTENERS
        desenhoGradeProperty().addListener((obs, o, n) -> {
            if (n != null) {
                userInput.getItems().setAll(n.getUsuarios());
                schedulerInput.getItems().setAll(n.getNosEscalonadores());
                List<String> names = n.getDags()
                        .stream()
                        .flatMap(dag -> Stream.of(dag.getName()))
                        .collect(Collectors.toList());
                dagChooser.getItems().setAll(names);
            }
        });
    }

    public List<GenerateWorkloadEntry> getEntries() {
        return Collections.unmodifiableList(table.getItems());
    }

    private Stage dagStage;
    private DagEditor dagEditor;
    private void openEditor(DAG dag) {
        if (dagStage == null) {
            dagStage = new Stage();
            dagStage.initModality(Modality.WINDOW_MODAL);
        }
        if (dagEditor == null) {
            dagEditor = new DagEditor();
            dagStage.setScene(new Scene(dagEditor, 800, 600));
        }
        dagEditor.getDiagramPane().setDiagram(dag);
        dagEditor.getDiagramPane().getSelectionModel().clear();
        dagStage.showAndWait();
        DAG result = dagEditor.getDAG();
        if (result != null && !dagChooser.getItems().contains(result.getName())) {
            getDesenhoGrade().getDags().add(result);
            dagChooser.getItems().add(result.getName());
            dagChooser.getSelectionModel().select(result.getName());
        }
    }

    /////////////////////////////////
    ////////// PROPERTIES ///////////
    /////////////////////////////////

    private ObjectProperty<DesenhoGrade> desenhoGrade = new SimpleObjectProperty<>(this, "desenhoGrade", null);
    public DesenhoGrade getDesenhoGrade() {
        return desenhoGrade.get();
    }
    public ObjectProperty<DesenhoGrade> desenhoGradeProperty() {
        return desenhoGrade;
    }
    public void setDesenhoGrade(DesenhoGrade areaDesenho) {
        this.desenhoGrade.set(areaDesenho);
    }
}
