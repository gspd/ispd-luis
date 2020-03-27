package gspd.ispd.fxgui.workload.dag.editor;

import gspd.ispd.fxgui.commons.Icon;
import gspd.ispd.fxgui.commons.IconEditor;
import gspd.ispd.fxgui.workload.dag.icons.ExpansionIcon;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;

public abstract class ExpansionEditor extends NodeEditor {

    private Spinner<Integer> numberSpinner;
    public ExpansionEditor() {
        Label numberLabel = new Label("Number");
        numberSpinner = new Spinner<>();
        numberSpinner.setEditable(true);
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, Integer.MAX_VALUE, 1);
        numberSpinner.setValueFactory(valueFactory);
        numberSpinner.valueProperty().addListener((obs, o, n) -> {
            ((ExpansionIcon) getIcon()).setNumber(n);
        });
        add(numberLabel, 0, 1);
        add(numberSpinner, 1, 1);
    }

    @Override
    protected void setup(Icon icon) {
        ExpansionIcon expansionIcon = (ExpansionIcon) icon;
        numberSpinner.getValueFactory().setValue(expansionIcon.getNumber());
    }
}
