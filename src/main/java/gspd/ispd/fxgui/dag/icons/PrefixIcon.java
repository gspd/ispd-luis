package gspd.ispd.fxgui.dag.icons;

import gspd.ispd.fxgui.commons.EdgeIcon;
import gspd.ispd.fxgui.commons.Icon;
import gspd.ispd.fxgui.commons.IconType;
import gspd.ispd.fxgui.commons.NodeIcon;
import gspd.ispd.fxgui.dag.shapes.PrefixShape;
import javafx.util.Builder;

public class PrefixIcon extends EdgeIcon {

    public static final IconType PREFIX_TYPE = IconType.type(EDGE_TYPE, "PREFIX_TYPE");

    /////////////////////////////////////
    ////////// CONSTRUCTOR //////////////
    /////////////////////////////////////

    public PrefixIcon(boolean selected, double startX, double startY, double endX, double endY) {
        super(PrefixShape::new, selected, startX, startY, endX, endY);

        setType(PREFIX_TYPE);
    }

    public PrefixIcon(double startX, double startY, double endX, double endY) {
        this(false, startX, startY, endX, endY);
    }

    public PrefixIcon() {
        this(false, 0.0, 0.0, 0.0, 0.0);
    }

    public PrefixIcon(boolean selected, NodeIcon startIcon, NodeIcon endIcon) {
        super(PrefixShape::new, selected, startIcon, endIcon);
    }

    public PrefixIcon(NodeIcon startIcon, NodeIcon endIcon) {
        this(false, startIcon, endIcon);
    }

    ////////////////////////////////////////
    ////////////// OVERRIDES ///////////////
    ////////////////////////////////////////


    @Override
    public Builder<? extends Icon> iconBuilder() {
        return PrefixIcon::new;
    }
}
