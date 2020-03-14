package gspd.ispd.fxgui.dag.icons;

import gspd.ispd.fxgui.commons.Diagram;
import gspd.ispd.fxgui.dag.shapes.ParallelExpansionShape;

public class ParallelExpansionIcon extends ExpansionIcon {

    public ParallelExpansionIcon(Diagram diagram, boolean selected, double centerX, double centerY) {
        super(ParallelExpansionShape::new, diagram, selected, centerX, centerY);
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
}
