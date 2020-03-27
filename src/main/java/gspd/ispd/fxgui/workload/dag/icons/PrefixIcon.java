package gspd.ispd.fxgui.workload.dag.icons;

import gspd.ispd.fxgui.commons.EdgeIcon;
import gspd.ispd.fxgui.commons.Icon;
import gspd.ispd.commons.ISPDType;
import gspd.ispd.fxgui.commons.IconEditor;
import gspd.ispd.fxgui.commons.NodeIcon;
import gspd.ispd.fxgui.workload.dag.editor.PrefixEditor;
import gspd.ispd.fxgui.workload.dag.shapes.PrefixShape;
import javafx.util.Builder;

public class PrefixIcon extends EdgeIcon {

    public static final ISPDType PREFIX_TYPE = ISPDType.type(EDGE_TYPE, "PREFIX_TYPE");

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
        setType(PREFIX_TYPE);
    }

    public PrefixIcon(NodeIcon startIcon, NodeIcon endIcon) {
        this(false, startIcon, endIcon);
    }

    ////////////////////////////////////////
    ////////////// OVERRIDES ///////////////
    ////////////////////////////////////////

    private static final Builder<PrefixIcon> PREFIX_BUILDER = PrefixIcon::new;
    @Override
    public Builder<? extends Icon> iconBuilder() {
        return PREFIX_BUILDER;
    }

    private static final PrefixEditor PREFIX_EDITOR = new PrefixEditor();
    @Override
    protected IconEditor editor() {
        PREFIX_EDITOR.setIcon(this);
        return PREFIX_EDITOR;
    }
}
