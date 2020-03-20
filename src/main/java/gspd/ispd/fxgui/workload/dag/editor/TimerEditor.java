package gspd.ispd.fxgui.workload.dag.editor;

import gspd.ispd.fxgui.commons.Icon;
import gspd.ispd.fxgui.commons.IconEditor;
import gspd.ispd.fxgui.workload.dag.icons.TimerIcon;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class TimerEditor extends IconEditor {

    private TextField timeInput;
    public TimerEditor() {
        Text title = new Text("Timer");
        title.setFont(Font.font(16));
        add(title, 0, 0, 2, 1);
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
        TimerIcon timerIcon = (TimerIcon) icon;
        timeInput.setText(String.valueOf(timerIcon.getTime()));
    }
}
