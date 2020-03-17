package gspd.ispd.fxgui.dag.editor;

import gspd.ispd.fxgui.commons.Icon;
import gspd.ispd.fxgui.commons.IconEditor;
import gspd.ispd.fxgui.dag.icons.TimerIcon;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class TimerEditor extends IconEditor {

    private TextField timeInput;
    public TimerEditor() {
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
        add(timeLabel, 0, 0);
        add(timeInput, 1, 0);
    }

    @Override
    protected void setup(Icon icon) {
        TimerIcon timerIcon = (TimerIcon) icon;
        timeInput.setText(String.valueOf(timerIcon.getTime()));
    }
}
