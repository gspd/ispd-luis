package gspd.ispd.fxgui.workload.dag.editor;

import gspd.ispd.commons.StringConstants;
import gspd.ispd.fxgui.commons.Icon;
import gspd.ispd.fxgui.commons.IconEditor;
import gspd.ispd.fxgui.workload.dag.icons.TimerIcon;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class TimerEditor extends NodeEditor {

    private TextField timeInput;
    public TimerEditor() {
        setTitle(StringConstants.TIMER_TITLE);
        Label timeLabel = new Label("Time");
        timeInput = new TextField();
        timeInput.textProperty().addListener((obs, o, n) -> {
            if (!n.matches("\\d*(\\.\\d*)?")) {
                timeInput.setText(n.replaceAll("[^.\\d]", ""));
            }
        });
        timeInput.textProperty().addListener((obs, o, n) -> {
            ((TimerIcon) getIcon()).setTime(Double.parseDouble(n));
        });
        add(timeLabel, 0, 1);
        add(timeInput, 1, 1);
    }

    @Override
    protected void setup(Icon icon) {
        super.setup(icon);
        TimerIcon timerIcon = (TimerIcon) icon;
        timeInput.setText(String.valueOf(timerIcon.getTime()));
    }
}
