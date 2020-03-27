package gspd.ispd.fxgui.workload.dag.icons;

import gspd.ispd.fxgui.commons.Diagram;
import gspd.ispd.commons.ISPDType;
import gspd.ispd.fxgui.commons.Icon;
import gspd.ispd.fxgui.commons.IconEditor;
import gspd.ispd.fxgui.workload.dag.editor.ParallelEditor;
import gspd.ispd.fxgui.workload.dag.shapes.ParallelExpansionShape;
import javafx.util.Builder;

public class ParallelExpansionIcon extends ExpansionIcon {

    public static final ISPDType PARALLEL_TYPE = ISPDType.type(ExpansionIcon.EXPANSION_TYPE, "PARALLEL_TYPE");

    public ParallelExpansionIcon(Diagram diagram, boolean selected, double centerX, double centerY) {
        super(ParallelExpansionShape::new, diagram, selected, centerX, centerY);
        setType(PARALLEL_TYPE);
    }

    public ParallelExpansionIcon(Diagram diagram, double centerX, double centerY) {
        this(diagram, false, centerX, centerY);
    }

    public ParallelExpansionIcon(Diagram diagram, boolean selected) {
        this(diagram, selected, 0.0, 0.0);
    }

    public ParallelExpansionIcon(Diagram diagram) {
        this(diagram, false, 0.0, 0.0);
    }

    public ParallelExpansionIcon(boolean selected, double centerX, double centerY) {
        this(null, selected, centerX, centerY);
    }

    public ParallelExpansionIcon(double centerX, double centerY) {
        this(null, false, centerX, centerY);
    }

    public ParallelExpansionIcon(boolean selected) {
        this(null, selected, 0.0, 0.0);
    }

    public ParallelExpansionIcon() {
        this(null, false, 0.0, 0.0);
    }

    /////////////////////////////////////////
    ///////////// OVERRIDE //////////////////
    /////////////////////////////////////////

    private static final Builder<ParallelExpansionIcon> PARALLEL_BUILDER = ParallelExpansionIcon::new;
    @Override
    public Builder<? extends Icon> iconBuilder() {
        return PARALLEL_BUILDER;
    }

    private static final ParallelEditor PARALLEL_EDITOR = new ParallelEditor();
    @Override
    protected IconEditor editor() {
        PARALLEL_EDITOR.setIcon(this);
        return PARALLEL_EDITOR;
    }
}
