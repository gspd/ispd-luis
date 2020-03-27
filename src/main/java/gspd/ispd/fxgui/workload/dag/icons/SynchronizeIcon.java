package gspd.ispd.fxgui.workload.dag.icons;

import gspd.ispd.fxgui.commons.Icon;
import gspd.ispd.commons.ISPDType;
import gspd.ispd.fxgui.commons.IconEditor;
import gspd.ispd.fxgui.commons.NodeIcon;
import gspd.ispd.fxgui.workload.dag.editor.SynchronizeEditor;
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

    private static final Builder<SynchronizeIcon> SYNCHRONIZE_BUILDER = SynchronizeIcon::new;
    @Override
    public Builder<? extends Icon> iconBuilder() {
        return SYNCHRONIZE_BUILDER;
    }

    private static final SynchronizeEditor SYNCHRONIZE_EDITOR = new SynchronizeEditor();
    @Override
    protected IconEditor editor() {
        SYNCHRONIZE_EDITOR.setIcon(this);
        return SYNCHRONIZE_EDITOR;
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
