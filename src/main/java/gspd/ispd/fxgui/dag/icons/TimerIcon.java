package gspd.ispd.fxgui.dag.icons;

import gspd.ispd.fxgui.commons.Icon;
import gspd.ispd.fxgui.commons.IconType;
import gspd.ispd.fxgui.commons.NodeIcon;
import gspd.ispd.fxgui.dag.shapes.TimerShape;
import javafx.scene.paint.Color;
import javafx.util.Builder;

public class TimerIcon extends NodeIcon {

    public static final IconType TIMER_TYPE = IconType.type(NODE_TYPE, "TIMER_TYPE");

    //////////////////////////////////////
    //////////// CONSTRUCTOR /////////////
    //////////////////////////////////////

    public TimerIcon(boolean selected, double centerX, double centerY) {
        super(TimerShape::new, selected, centerX, centerY);

        setType(TIMER_TYPE);
    }

    public TimerIcon(double centerX, double centerY) {
        this(false, centerX, centerY);
    }

    public TimerIcon(boolean selected) {
        this(selected, 0.0, 0.0);
    }

    public TimerIcon() {
        this(false, 0.0, 0.0);
    }

    //////////////////////////////////////////
    ///////////// OVERRIDES //////////////////
    //////////////////////////////////////////

    @Override
    public Builder<? extends Icon> iconBuilder() {
        return TimerIcon::new;
    }

    @Override
    protected void updateIcon() {
        TimerShape shape = (TimerShape) getContent();
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
