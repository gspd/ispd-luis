package gspd.ispd.fxgui.workload;

import javafx.geometry.HPos;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class TraceOrRandomPane extends GridPane {

    public TraceOrRandomPane() {
        createUI();
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

    public static final int TRACE = 1;
    public static final int GENERATE = 2;
    public int getChoice() {
        if (toggle.getSelectedToggle() == traceRadio) {
            return TRACE;
        } else if (toggle.getSelectedToggle() == generateRadio) {
            return GENERATE;
        }
        return -1;
    }
}
