package gspd.ispd.fxgui.workload.dag.editor;

import gspd.ispd.commons.StringConstants;
import gspd.ispd.fxgui.commons.EdgeIcon;
import gspd.ispd.fxgui.commons.Icon;
import gspd.ispd.fxgui.workload.dag.icons.SwitchIcon;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;

public class SwitchEditor extends NodeEditor {

    public SwitchEditor() {
        setTitle(StringConstants.SWITCH_TITLE);
    }

    @Override
    protected void setup(Icon icon) {
        super.setup(icon);
        SwitchIcon sw = (SwitchIcon) icon;
        clearInputs();
        sw.getDistributionMap().forEach(this::addInput);
    }

    private void clearInputs() {
        setTitle(StringConstants.SWITCH_TITLE);
        getChildren().removeAll(getChildrenUnmodifiable().filtered(n -> n != getTitleText()));
    }

    private void addInput(EdgeIcon edge, double prob) {
        int row = getRowCount();
        Spinner<Double> input = new Spinner<>();
        input.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0.0, Double.MAX_VALUE, prob));
        input.setEditable(true);
        input.setPrefWidth(INPUT_WIDTH);
        input.setEditable(true);
        input.valueProperty().addListener((obs, o, n) -> {
            ((SwitchIcon) getIcon()).putEdge(edge, n);
        });
        edge.hoveredProperty().bind(input.focusedProperty());
        add(new Label("prob" + row), 0, row);
        add(input, 1, row);
    }

    ////////////////////////////////////////
    ////////// OVERRIDES ///////////////////
    ////////////////////////////////////////


    @Override
    public void setIcon(Icon icon) {
        super.setIcon(icon);
        // Force re-setup, this is needed only for switch because
        // the number of inputs is updated dynamically
        setup(icon);
    }
}
