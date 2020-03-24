package gspd.ispd.fxgui.workload.dag.icons;

import gspd.ispd.fxgui.commons.Icon;
import gspd.ispd.fxgui.commons.IconEditor;
import gspd.ispd.commons.ISPDType;
import gspd.ispd.fxgui.commons.NodeIcon;
import gspd.ispd.fxgui.workload.dag.editor.TimerEditor;
import gspd.ispd.fxgui.workload.dag.shapes.TimerShape;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.paint.Color;
import javafx.util.Builder;

public class TimerIcon extends NodeIcon {

    public static final ISPDType TIMER_TYPE = ISPDType.type(NODE_TYPE, "TIMER_TYPE");

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

    private static final TimerEditor TIMER_EDITOR = new TimerEditor();
    @Override
    protected IconEditor editor() {
        TIMER_EDITOR.setIcon(this);
        return TIMER_EDITOR;
    }

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

    ////////////////////////////////////////////
    ////////////// PROPERTIES //////////////////
    ////////////////////////////////////////////

    /**
     * The time of this timer
     */
    private DoubleProperty time = new SimpleDoubleProperty(this, "time", 0.0);
    public double getTime() {
        return time.get();
    }
    public DoubleProperty timeProperty() {
        return time;
    }
    public void setTime(double time) {
        this.time.set(time);
    }
}
