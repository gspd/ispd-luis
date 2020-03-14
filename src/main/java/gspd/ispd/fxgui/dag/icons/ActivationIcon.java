package gspd.ispd.fxgui.dag.icons;

import gspd.ispd.fxgui.commons.Icon;
import gspd.ispd.fxgui.commons.IconType;
import gspd.ispd.fxgui.commons.NodeIcon;
import gspd.ispd.fxgui.dag.shapes.ActivationShape;
import javafx.scene.paint.Color;
import javafx.util.Builder;

public class ActivationIcon extends NodeIcon {

    public static final IconType ACTIVATION_TYPE = IconType.type(NODE_TYPE, "ACTIVATION_TYPE");

    ///////////////////////////////////////////
    //////////////// CONSTRUCTOR //////////////
    ///////////////////////////////////////////

    public ActivationIcon(boolean selected, double centerX, double centerY) {
        super(ActivationShape::new, selected, centerX, centerY);

        setIconType(ACTIVATION_TYPE);
    }

    public ActivationIcon(double centerX, double centerY) {
        this(false, centerX, centerY);
    }

    public ActivationIcon(boolean selected) {
        this(selected, 0.0, 0.0);
    }

    public ActivationIcon() {
        this(false, 0.0, 0.0);
    }

    ///////////////////////////////////////////
    /////////////// OVERRIDE //////////////////
    ///////////////////////////////////////////

    @Override
    public Builder<? extends Icon> iconBuilder() {
        return ActivationIcon::new;
    }

    @Override
    protected void updateIcon() {
        ActivationShape shape = (ActivationShape) getContent();
        if (isSelected()) {
            shape.setFill(Color.LIGHTSKYBLUE);
            shape.setStroke(Color.DODGERBLUE);
        } else {
            shape.setFill(Color.WHITE);
            shape.setStroke(Color.BLACK);
        }
    }
}
