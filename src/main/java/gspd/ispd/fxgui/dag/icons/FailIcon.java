package gspd.ispd.fxgui.dag.icons;

import gspd.ispd.fxgui.commons.EdgeIcon;
import gspd.ispd.fxgui.commons.IconType;
import gspd.ispd.fxgui.commons.NodeIcon;
import gspd.ispd.fxgui.dag.shapes.FailShape;

public class FailIcon extends EdgeIcon {

    public static final IconType FAIL_TYPE = IconType.type(EDGE_TYPE, "FAIL_TYPE");

    /////////////////////////////////////
    ////////// CONSTRUCTOR //////////////
    /////////////////////////////////////

    public FailIcon(boolean selected, double startX, double startY, double endX, double endY) {
        super(FailShape::new, selected, startX, startY, endX, endY);
        setIconType(FAIL_TYPE);
    }

    public FailIcon(double startX, double startY, double endX, double endY) {
        this(false, startX, startY, endX, endY);
    }

    public FailIcon() {
        this(false, 0.0, 0.0, 0.0, 0.0);
    }

    public FailIcon(boolean selected, NodeIcon startIcon, NodeIcon endIcon) {
        super(FailShape::new, selected, startIcon, endIcon);
    }

    public FailIcon(NodeIcon startIcon, NodeIcon endIcon) {
        this(false, startIcon, endIcon);
    }
}
