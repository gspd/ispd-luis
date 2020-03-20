package gspd.ispd.fxgui.workload;

import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

public class TracePane extends GridPane {

    public TracePane() {
        createUI();
    }

    private RadioButton ispdRadio;
    private RadioButton externalRadio;
    private ToggleGroup toggle;
    private void createUI() {
        toggle = new ToggleGroup();
        Text text = new Text("Select the desired option");
        ispdRadio = new RadioButton("Open an existing iSPD trace file");
        ispdRadio.setToggleGroup(toggle);
        externalRadio = new RadioButton("Convert an external trace file to iSPD trace format");
        externalRadio.setToggleGroup(toggle);
        toggle.selectToggle(ispdRadio);
        add(text, 0, 0);
        add(ispdRadio, 0, 1);
        add(externalRadio, 0, 2);
    }

    public static final int ISPD = 1;
    public static final int EXTERNAL = 2;
    public int getChoice() {
        if (toggle.getSelectedToggle() == ispdRadio) {
            return ISPD;
        } else if (toggle.getSelectedToggle() == externalRadio) {
            return EXTERNAL;
        }
        return -1;
    }
}
