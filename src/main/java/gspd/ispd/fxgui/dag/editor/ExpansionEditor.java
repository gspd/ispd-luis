package gspd.ispd.fxgui.dag.editor;

import gspd.ispd.fxgui.commons.Icon;
import gspd.ispd.fxgui.commons.IconEditor;
import gspd.ispd.fxgui.dag.icons.ExpansionIcon;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;

public class ExpansionEditor extends IconEditor {

    private Spinner<Integer> numberSpinner;
    public ExpansionEditor() {
        Label numberLabel = new Label("Number");
        numberSpinner = new Spinner<>();
        numberSpinner.setEditable(true);
        numberSpinner.setValueFactory(new SpinnerValueFactory<Integer>() {
            @Override
            public void decrement(int steps) {
                int nval = getValue() - steps;
                if (nval < 1) nval = 1;
                setValue(nval);
            }
            @Override
            public void increment(int steps) {
                int nval = getValue() + steps;
                setValue(nval);
            }
        });
        numberSpinner.valueProperty().addListener((obs, o, n) -> {
            ((ExpansionIcon) getIcon()).setNumber(n);
        });
        add(numberLabel, 0, 0);
        add(numberSpinner, 1, 0);
    }

    @Override
    protected void setup(Icon icon) {
        ExpansionIcon expansionIcon = (ExpansionIcon) icon;
        numberSpinner.decrement(Integer.MAX_VALUE);
        numberSpinner.increment(expansionIcon.getNumber());
    }
}
