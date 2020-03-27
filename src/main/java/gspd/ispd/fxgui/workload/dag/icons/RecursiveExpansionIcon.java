package gspd.ispd.fxgui.workload.dag.icons;

import gspd.ispd.fxgui.commons.Diagram;
import gspd.ispd.commons.ISPDType;
import gspd.ispd.fxgui.commons.Icon;
import gspd.ispd.fxgui.commons.IconEditor;
import gspd.ispd.fxgui.workload.dag.editor.RecursiveEditor;
import gspd.ispd.fxgui.workload.dag.editor.SwitchEditor;
import gspd.ispd.fxgui.workload.dag.shapes.RecursiveExpansionShape;
import javafx.util.Builder;

public class RecursiveExpansionIcon extends ExpansionIcon {

    public static final ISPDType RECURSIVE_TYPE = ISPDType.type(ExpansionIcon.EXPANSION_TYPE, "RECURSIVE_TYPE");

    public RecursiveExpansionIcon(Diagram diagram, boolean selected, double centerX, double centerY) {
        super(RecursiveExpansionShape::new, diagram, selected, centerX, centerY);
        setType(RECURSIVE_TYPE);
    }

    public RecursiveExpansionIcon(Diagram diagram, double centerX, double centerY) {
        this(diagram, false, centerX, centerY);
    }

    public RecursiveExpansionIcon(Diagram diagram, boolean selected) {
        this(diagram, selected, 0.0, 0.0);
    }

    public RecursiveExpansionIcon(Diagram diagram) {
        this(diagram, false, 0.0, 0.0);
    }

    public RecursiveExpansionIcon(boolean selected, double centerX, double centerY) {
        this(null, selected, centerX, centerY);
    }

    public RecursiveExpansionIcon(double centerX, double centerY) {
        this(null, false, centerX, centerY);
    }

    public RecursiveExpansionIcon(boolean selected) {
        this(null, selected, 0.0, 0.0);
    }

    public RecursiveExpansionIcon() {
        this(null, false, 0.0, 0.0);
    }

    /////////////////////////////////////////////
    ///////////////// OVERRIDES /////////////////
    /////////////////////////////////////////////

    private static final Builder<RecursiveExpansionIcon> RECURSIVE_BUILDER = RecursiveExpansionIcon::new;
    @Override
    public Builder<? extends Icon> iconBuilder() {
        return RECURSIVE_BUILDER;
    }

    private static final RecursiveEditor RECURSIVE_EDITOR = new RecursiveEditor();
    @Override
    protected IconEditor editor() {
        RECURSIVE_EDITOR.setIcon(this);
        return RECURSIVE_EDITOR;
    }
}
