package gspd.ispd.fxgui.workload.dag.icons;

import gspd.ispd.fxgui.commons.Icon;
import gspd.ispd.fxgui.commons.IconType;
import gspd.ispd.fxgui.commons.NodeIcon;
import gspd.ispd.fxgui.workload.dag.shapes.RecursionShape;
import javafx.scene.paint.Color;
import javafx.util.Builder;

public class RecursionIcon extends NodeIcon {

    public static final IconType RECURSION_TYPE = IconType.type(NODE_TYPE, "RECURSION_TYPE");

    //////////////////////////////////////////
    //////////// CONSTRUCTOR /////////////////
    //////////////////////////////////////////

    public RecursionIcon(boolean selected, double centerX, double centerY) {
        super(RecursionShape::new, selected, centerX, centerY);

        setType(RECURSION_TYPE);
    }

    public RecursionIcon(double centerX, double centerY) {
        this(false, centerX, centerY);
    }

    public RecursionIcon(boolean selected) {
        this(selected, 0.0, 0.0);
    }

    public RecursionIcon() {
        this(false, 0.0, 0.0);
    }

    //////////////////////////////////////////
    ////////////// OVERRIDE //////////////////
    //////////////////////////////////////////

    @Override
    public Builder<? extends Icon> iconBuilder() {
        return RecursionIcon::new;
    }

    @Override
    protected void updateIcon() {
        RecursionShape shape = (RecursionShape) getContent();
        if (isSelected()) {
            shape.setFill(Color.LIGHTSKYBLUE);
            shape.setStroke(Color.DODGERBLUE);
        } else {
            shape.setFill(Color.WHITE);
            shape.setStroke(Color.BLACK);
        }
    }
}
