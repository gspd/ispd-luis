package gspd.ispd.fxgui.workload;

import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.HPos;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class TraceOrRandomPane extends GridPane {

    public TraceOrRandomPane() {
        createUI();
        initEvents();
    }

    private ToggleGroup toggle;
    private RadioButton traceRadio;
    private RadioButton generateRadio;
    private void createUI() {
        toggle = new ToggleGroup();
        traceRadio = new RadioButton("Get from traces");
        traceRadio.setToggleGroup(toggle);
        toggle.selectToggle(traceRadio);
        generateRadio = new RadioButton("Generate workload");
        generateRadio.setToggleGroup(toggle);
        Text text = new Text("Choose how will be the workload");
        setHalignment(text, HPos.CENTER);
        add(text, 0, 0, 3, 1);
        add(traceRadio, 1, 1);
        add(generateRadio, 1, 2);
        setVgap(5);
    }

    private void initEvents() {
        toggle.selectedToggleProperty().addListener((obs, o, n) -> {
            if (n == traceRadio) {
                setChoice(TRACE);
            } else {
                setChoice(GENERATE);
            }
        });
        choiceProperty().addListener((obs, o, n) -> {
            int iN = (Integer) n;
            if (iN == TRACE) {
                toggle.selectToggle(traceRadio);
            } else if (iN == GENERATE) {
                toggle.selectToggle(generateRadio);
            }
        });
    }

    public static final int TRACE = 1;
    public static final int GENERATE = 2;
    private IntegerProperty choice = new SimpleIntegerProperty(this, "choice", 0);
    public IntegerProperty choiceProperty() {
        return choice;
    }
    public int getChoice() {
        return choice.get();
    }
    public void setChoice(int choice) {
        this.choice.set(choice);
    }
}
