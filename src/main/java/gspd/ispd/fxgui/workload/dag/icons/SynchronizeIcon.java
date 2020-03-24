package gspd.ispd.fxgui.workload.dag.icons;

import gspd.ispd.fxgui.commons.Icon;
import gspd.ispd.commons.ISPDType;
import gspd.ispd.fxgui.commons.NodeIcon;
import gspd.ispd.fxgui.workload.dag.shapes.SynchronizeShape;
import javafx.scene.paint.Color;
import javafx.util.Builder;

public class SynchronizeIcon extends NodeIcon {

    public static final ISPDType SYNCHRONIZE_TYPE = ISPDType.type(NODE_TYPE, "SYNCHRONIZE_TYPE");

    ////////////////////////////////////////////
    ///////////// CONSTRUCTOR //////////////////
    ////////////////////////////////////////////

    public SynchronizeIcon(boolean selected, double centerX, double centerY) {
        super(SynchronizeShape::new, selected, centerX, centerY);

        setType(SYNCHRONIZE_TYPE);
    }

    public SynchronizeIcon(double centerX, double centerY) {
        this(false, centerX, centerY);
    }

    public SynchronizeIcon(boolean selected) {
        this(selected, 0.0, 0.0);
    }

    public SynchronizeIcon() {
        this(false, 0.0, 0.0);
    }

    //////////////////////////////////////////////
    /////////////// OVERRIDE /////////////////////
    //////////////////////////////////////////////

    @Override
    public Builder<? extends Icon> iconBuilder() {
        return SynchronizeIcon::new;
    }

    @Override
    protected void updateIcon() {
        SynchronizeShape shape = (SynchronizeShape) getContent();
        if (isSelected()) {
            shape.setFill(Color.MEDIUMBLUE);
            shape.setStroke(Color.DARKBLUE);
        } else {
            shape.setFill(Color.BLACK);
            shape.setStroke(Color.BLACK);
        }
    }
}
