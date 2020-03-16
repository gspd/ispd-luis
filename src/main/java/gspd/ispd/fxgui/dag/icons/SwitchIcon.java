package gspd.ispd.fxgui.dag.icons;

import gspd.ispd.fxgui.commons.Icon;
import gspd.ispd.fxgui.commons.IconType;
import gspd.ispd.fxgui.commons.NodeIcon;
import gspd.ispd.fxgui.dag.shapes.SwitchShape;
import javafx.scene.paint.Color;
import javafx.util.Builder;

public class SwitchIcon extends NodeIcon {

    public static final IconType SWITCH_TYPE = IconType.type(NODE_TYPE, "SWITCH_TYPE");

    ///////////////////////////////////////
    /////////// CONSTRUCTOR ///////////////
    ///////////////////////////////////////

    public SwitchIcon(boolean selected, double centerX, double centerY) {
        super(SwitchShape::new, selected, centerX, centerY);

        setType(SWITCH_TYPE);
    }

    public SwitchIcon(double centerX, double centerY) {
        this(false, centerX, centerY);
    }

    public SwitchIcon(boolean selected) {
        this(selected, 0.0, 0.0);
    }

    public SwitchIcon() {
        this(false, 0.0, 0.0);
    }

    /////////////////////////////////////////
    ///////////// OVERRIDE //////////////////
    /////////////////////////////////////////

    @Override
    public Builder<? extends Icon> iconBuilder() {
        return SwitchIcon::new;
    }

    @Override
    protected void updateIcon() {
        SwitchShape shape = (SwitchShape) getContent();
        if (isSelected()) {
            shape.setFill(Color.MEDIUMBLUE);
            shape.setStroke(Color.DARKBLUE);
        } else {
            shape.setFill(Color.BLACK);
            shape.setStroke(Color.BLACK);
        }
    }
}
