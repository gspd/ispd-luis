package gspd.ispd.fxgui.workload.dag.icons;

import gspd.ispd.fxgui.commons.Icon;
import gspd.ispd.commons.ISPDType;
import gspd.ispd.fxgui.commons.IconEditor;
import gspd.ispd.fxgui.commons.NodeIcon;
import gspd.ispd.fxgui.workload.dag.editor.ActivationEditor;
import gspd.ispd.fxgui.workload.dag.shapes.ActivationShape;
import javafx.scene.paint.Color;
import javafx.util.Builder;

public class ActivationIcon extends NodeIcon {

    public static final ISPDType ACTIVATION_TYPE = ISPDType.type(NODE_TYPE, "ACTIVATION_TYPE");

    ///////////////////////////////////////////
    //////////////// CONSTRUCTOR //////////////
    ///////////////////////////////////////////

    public ActivationIcon(boolean selected, double centerX, double centerY) {
        super(ActivationShape::new, selected, centerX, centerY);

        setType(ACTIVATION_TYPE);
    }

    public ActivationIcon(double centerX, double centerY) {
        this(false, centerX, centerY);
    }

    public ActivationIcon(boolean selected) {
        this(selected, 0.0, 0.0);
    }

    public ActivationIcon() {
        this(false, 0.0, 0.0);
    }

    ///////////////////////////////////////////
    /////////////// OVERRIDE //////////////////
    ///////////////////////////////////////////

    private static final Builder<ActivationIcon> ACTIVATION_BUILDER = ActivationIcon::new;
    @Override
    public Builder<? extends Icon> iconBuilder() {
        return ACTIVATION_BUILDER;
    }

    private static final ActivationEditor ACTIVATION_EDITOR = new ActivationEditor();
    @Override
    protected IconEditor editor() {
        ACTIVATION_EDITOR.setIcon(this);
        return ACTIVATION_EDITOR;
    }

    @Override
    protected void updateIcon() {
        ActivationShape shape = (ActivationShape) getContent();
        if (isSelected()) {
            shape.setFill(Color.LIGHTSKYBLUE);
            shape.setStroke(Color.DODGERBLUE);
        } else {
            shape.setFill(Color.WHITE);
            shape.setStroke(Color.BLACK);
        }
    }
}
