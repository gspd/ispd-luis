package gspd.ispd.fxgui.dag.icons;

import gspd.ispd.fxgui.commons.Diagram;
import gspd.ispd.fxgui.dag.shapes.IterativeExpansionShape;

public class IterativeExpansionIcon extends ExpansionIcon {

    public IterativeExpansionIcon(Diagram diagram, boolean selected, double centerX, double centerY) {
        super(IterativeExpansionShape::new, diagram, selected, centerX, centerY);
    }

    public IterativeExpansionIcon(Diagram diagram, double centerX, double centerY) {
        this(diagram, false, centerX, centerY);
    }

    public IterativeExpansionIcon(Diagram diagram, boolean selected) {
        this(diagram, selected, 0.0, 0.0);
    }

    public IterativeExpansionIcon(Diagram diagram) {
        this(diagram, false, 0.0, 0.0);
    }

    public IterativeExpansionIcon(boolean selected, double centerX, double centerY) {
        this(null, selected, centerX, centerY);
    }

    public IterativeExpansionIcon(double centerX, double centerY) {
        this(null, false, centerX, centerY);
    }

    public IterativeExpansionIcon(boolean selected) {
        this(null, selected, 0.0, 0.0);
    }

    public IterativeExpansionIcon() {
        this(null, false, 0.0, 0.0);
    }
}
