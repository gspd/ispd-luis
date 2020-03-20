package gspd.ispd.fxgui.workload.dag.icons;

import gspd.ispd.fxgui.commons.EdgeIcon;
import gspd.ispd.fxgui.commons.Icon;
import gspd.ispd.fxgui.commons.IconType;
import gspd.ispd.fxgui.commons.NodeIcon;
import gspd.ispd.fxgui.workload.dag.shapes.PrecedenceShape;
import javafx.util.Builder;

public class PrecedenceIcon extends EdgeIcon {

    public static final IconType PRECEDENCE_TYPE = IconType.type(EDGE_TYPE, "PRECEDENCE_TYPE");

    /////////////////////////////////////
    ////////// CONSTRUCTOR //////////////
    /////////////////////////////////////

    public PrecedenceIcon(boolean selected, double startX, double startY, double endX, double endY) {
        super(PrecedenceShape::new, selected, startX, startY, endX, endY);
        setType(PRECEDENCE_TYPE);
    }

    public PrecedenceIcon(double startX, double startY, double endX, double endY) {
        this(false, startX, startY, endX, endY);
    }

    public PrecedenceIcon() {
        this(false, 0.0, 0.0, 0.0, 0.0);
    }

    public PrecedenceIcon(boolean selected, NodeIcon startIcon, NodeIcon endIcon) {
        super(PrecedenceShape::new, selected, startIcon, endIcon);
    }

    public PrecedenceIcon(NodeIcon startIcon, NodeIcon endIcon) {
        this(false, startIcon, endIcon);
    }

    /////////////////////////////////////////
    ////////////// OVERRIDES ////////////////
    /////////////////////////////////////////

    @Override
    public Builder<? extends Icon> iconBuilder() {
        return PrecedenceIcon::new;
    }
}
