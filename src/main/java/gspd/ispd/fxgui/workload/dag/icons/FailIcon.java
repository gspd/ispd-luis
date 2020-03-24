package gspd.ispd.fxgui.workload.dag.icons;

import gspd.ispd.fxgui.commons.EdgeIcon;
import gspd.ispd.fxgui.commons.Icon;
import gspd.ispd.commons.ISPDType;
import gspd.ispd.fxgui.commons.NodeIcon;
import gspd.ispd.fxgui.workload.dag.shapes.FailShape;
import javafx.util.Builder;

public class FailIcon extends EdgeIcon {

    public static final ISPDType FAIL_TYPE = ISPDType.type(EDGE_TYPE, "FAIL_TYPE");

    /////////////////////////////////////
    ////////// CONSTRUCTOR //////////////
    /////////////////////////////////////

    public FailIcon(boolean selected, double startX, double startY, double endX, double endY) {
        super(FailShape::new, selected, startX, startY, endX, endY);
        setType(FAIL_TYPE);
    }

    public FailIcon(double startX, double startY, double endX, double endY) {
        this(false, startX, startY, endX, endY);
    }

    public FailIcon() {
        this(false, 0.0, 0.0, 0.0, 0.0);
    }

    public FailIcon(boolean selected, NodeIcon startIcon, NodeIcon endIcon) {
        super(FailShape::new, selected, startIcon, endIcon);
        setType(FAIL_TYPE);
    }

    public FailIcon(NodeIcon startIcon, NodeIcon endIcon) {
        this(false, startIcon, endIcon);
    }

    /////////////////////////////////////////
    //////////// OVERRIDES //////////////////
    /////////////////////////////////////////

    @Override
    public Builder<? extends Icon> iconBuilder() {
        return FailIcon::new;
    }
}
