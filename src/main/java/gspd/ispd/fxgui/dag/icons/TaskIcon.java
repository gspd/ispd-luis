package gspd.ispd.fxgui.dag.icons;

import gspd.ispd.fxgui.commons.Icon;
import gspd.ispd.fxgui.commons.IconType;
import gspd.ispd.fxgui.commons.NodeIcon;
import gspd.ispd.fxgui.dag.shapes.TaskShape;
import javafx.scene.paint.Color;
import javafx.util.Builder;

public class TaskIcon extends NodeIcon {

    public static final IconType TASK_TYPE = IconType.type(NODE_TYPE, "TASK_TYPE");

    /////////////////////////////////////
    ////////// CONSTRUCTOR //////////////
    /////////////////////////////////////

    public TaskIcon(boolean selected, double centerX, double centerY) {
        super(TaskShape::new, selected, centerX, centerY);
        setIconType(TASK_TYPE);
    }

    public TaskIcon(double centerX, double centerY) {
        this(false, centerX, centerY);
    }

    public TaskIcon(boolean selected) {
        this(selected, 0.0, 0.0);
    }

    public TaskIcon() {
        this(false, 0.0, 0.0);
    }

    ///////////////////////////////////////////////////
    ///////////////// OVERRIDES ///////////////////////
    ///////////////////////////////////////////////////


    @Override
    public Builder<? extends Icon> iconBuilder() {
        return TaskIcon::new;
    }

    @Override
    protected void updateIcon() {
        TaskShape shape = (TaskShape) getContent();
        if (isSelected()) {
            shape.setStroke(Color.BLUE);
            shape.setFill(Color.DEEPSKYBLUE);
        } else if (isHovered()) {
            shape.setFill(Color.LIGHTBLUE);
            shape.setStroke(Color.CYAN);
        } else {
            shape.setStroke(Color.BLACK);
            shape.setFill(Color.LIGHTYELLOW);
        }
    }
}
