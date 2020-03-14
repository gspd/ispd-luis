package gspd.ispd.fxgui.dag.icons;

import gspd.ispd.fxgui.commons.Diagram;
import gspd.ispd.fxgui.dag.shapes.RecursiveExpansionShape;

public class RecursiveExpansionIcon extends ExpansionIcon {

    public RecursiveExpansionIcon(Diagram diagram, boolean selected, double centerX, double centerY) {
        super(RecursiveExpansionShape::new, diagram, selected, centerX, centerY);
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

}
